(function() {

var define = (function() {
    var modules = {};
    return function define(id, dependencies, factory) {
        if (id in modules) {
            throw "重复定义模块：" + id;
        }
        modules[id] = factory.apply(null, dependencies.map(function(dep_id) {
            if (dep_id in modules) {
                return modules[dep_id];
            }
            throw "缺少依赖模块：" + dep_id;
        }));
    };
})();

// ********************************************************************************************************************
// W/conf
define('W/conf', [], function() {
    return {
        author: "Minhao Jin (minhao.jin@gmail.com)",
        version: "0.1.0",
        base: ""
    };
});

// ********************************************************************************************************************
// W/util

define('W/util', [], function() {
    var RE_REFERENCE = /^[A-Za-z_][A-Za-z0-9_]*(\.[A-Za-z_][A-Za-z0-9_]*|\[[^\]]+\])*$/;

    return {
        ajax_get: function(url) {
            return new Promise(function(resolve, reject) {
                if (url.startsWith("var:")) {
                    resolve(window[url.substring(4)]);
                    return;
                }

                var xhr = new XMLHttpRequest();
                xhr.open("GET", url, true);
                xhr.onload = function() {
                    resolve(this.responseText);
                };
                xhr.onerror = function(e) {
                    reject(e);
                }
                xhr.send();
            });
        },

        extend: function(target) {
            for (var i = 1; i < arguments.length; i++) {
                var source = arguments[i];
                if (source != null) {
                    for (var name in source) {
                        var value = source[name];
                        if (value !== undefined) {
                            target[name] = value;
                        }
                    }
                }
            }
            return target;
        },

        is_reference: function(expr) {
           return RE_REFERENCE.test(expr);
        }
    };
});

// ********************************************************************************************************************
// W/proto

define('W/proto', [], function() {
    return {};
});

// ********************************************************************************************************************
// W/class

define('W/class', ['W/proto', 'W/util'], function(P, util) {
    var id_seq = 1;
    return function W(parent, mixin) {
        var W = function() {
            if (W.main) {
                W.main.apply(W, arguments);
            }
        };
        W.__proto__ = P; // FIXME: 非标准化方法
        W.id = id_seq++;

        if (parent) {
            parent.children = parent.children || [];
            parent.children.push(W);
            W.parent = parent;
        }

        util.extend(W, mixin);
        return W;
    };
});

// ********************************************************************************************************************
// W/filter

define('W/filter', ['W/proto', 'W/util'], function(P, util) {
    var FILTERS = {
        JSON: function(s) {
            return JSON.stringify(s);
        },

        HTML: function(s) {
            return s;
        },

        LOWER: function(s) {
            return s == null ? '' : s.toLowerCase();
        },

        UPPER: function(s) {
            return s == null ? '' : s.toUpperCase();
        }
    };

    util.extend(P, {
        filter: function(name, value) {
            if (value) {
                FILTERS[name] = value;
                return this;
            }
            return FILTERS[name];
        }
    });

    return {
        apply: function(text, filters) {
            var html = false;
            var len = filters.length;

            if (filters[len - 1] === FILTERS.HTML) {
                html = true;
                len--;
            }

            for (var i = 0; i < len; i++) {
                text = filters[i](text);
            }

            return { text: text, html: html };
        },

        filters: function() {
            return util.extend({}, FILTERS);
        }
    };
});

// ********************************************************************************************************************
// W/page

define('W/page', ['W/conf', 'W/util', 'W/filter'], function(conf, util, filter) {
    var PAGE_CACHE = {};
    var PAGE_W = {};

    function Page() {}

    util.extend(Page.prototype, {
        create: function(W) {
            var page = W.page = this;

            PAGE_W[W.id] = W;

            if (page.factory === undefined) {
                try {
                    if (typeof babel !== "undefined") {
                        page.factory = window.eval(babel.transform(page.script).code);
                    } else {
                        page.factory = window.eval(page.script);
                    }
                } catch (e) {
                    // FIXME
                    console.error(e);
                    if (e.lineNumber) {
                        var line = page.script.split("\n")[e.lineNumber - 1];
                        var hint = Array(e.columnNumber).join("-") + "^";
                        console.error(line + "\n" + hint);
                    }
                }
            }

            var fn = page.factory();
            W.call(fn, W).then(function() {
                W.digest();
                if (W._initialize) {
                    W._initialize();
                }
            });
        },

        remove: function(W) {
            delete PAGE_W[W.id];
        }
    });

    function parse_page(href, body) {
        return new Promise(function(resolve, reject) {
            var page = new Page();
            page.href = href;

            if (typeof body === "string") {
                // 将<w>替换成<template w>以避免HTML解析时上下文不允许<w>而导致<w>位置变化的问题
                body = body.replace(/<(\/?)w(>|\s)/g, function(m, $1, $2) {
                    return "<" + $1 + "template w" + $2;
                });

                page.html = body;
                body = document.createElement("BODY");
                body.innerHTML = page.html;
            }

            var links = body.querySelectorAll("link[rel=\"import\"]");
            var promises = [];
            for (var i = 0; i < links.length; i++) {
                var link = links[i];
                link.remove();
                promises.push(load_page(link.getAttribute("href")));
            }

            var widget = body.querySelector("template[w-widget]");
            var widget_tnode;
            if (widget) {
                widget.remove();
                if ('children' in widget.content) {
                    widget_tnode = widget.content.children[0];
                } else {
                    var child_nodes = widget.content.childNodes;
                    for (var i = 0; i < child_nodes.length; i++) {
                        widget_tnode = child_nodes[i];
                        if (widget_tnode.nodeType === 1) {
                            break;
                        }
                    }
                }

                page.name = widget_tnode.nodeName;
            }

            page.script = compile(href, body, widget_tnode);

            if (promises.length) {
                Promise.all(promises).then(function() {
                    var imports = page.imports = {};
                    for (var i = 0; i < arguments[0].length; i++) {
                        var widget = arguments[0][i];
                        imports[widget.name] = widget;
                    }
                    resolve(page);
                });
            } else {
                resolve(page);
            }
        });
    }

    function compile(href, body, widget_tnode) {
        var out = [];
        out.push("(function() {");
        out.push("return function*(W) {");
        out.push("W._render = function(pvnode) {");

        // FIXME: 仅导入用到的过滤器
        for (var filter_name in filter.filters()) {
            out.push("var " + filter_name + " = W.filter(\"" + filter_name + "\");");
        }

        out.push("var vnode;");

        var ctx = { out: out };

        for (var i = 0; i < body.childNodes.length; i++) {
            var child_tnode = body.childNodes[i];
            compile_recursive(child_tnode, ctx);
        }

        out.push("}; //W._render");

        out.push("W._eval = function(script) { return eval(script); };");

        if (widget_tnode) {
            var attributes = {}
            for (var i = 0; i < widget_tnode.attributes.length; i++) {
                var attr = widget_tnode.attributes[i];
                var attr_name = attr.name, attr_value = attr.value;

                switch (attr_name) {
                    case "for:": {
                        continue;
                    }

                    default: {
                        if (attr_name[attr_name.length - 1] !== ":") {
                            // FIXME: error?
                            continue;
                        }

                        attributes[attr_name.substring(0, attr_name.length - 1)] = attr_value;
                    }
                }
            }

            out.push("W._widget_digest = function() {");
            out.push("var $detail = {};");
            for (var attr_name in attributes) {
                if (attr_name.startsWith("on")) {
                    out.push(attributes[attr_name] + " = W.node.listeners[\"" + attr_name.substring(2) + "\"];");
                } else {
                    out.push("$detail[\"" + attr_name + "\"] = " + attributes[attr_name] + ";");
                    out.push(attributes[attr_name] + " = W.node.attributes[\"" + attr_name + "\"];");
                }
            }
            out.push("W.fire(\"widget-digest\", $detail);");
            out.push("};");

            out.push("W._widget_emit = function() {");
            for (var attr_name in attributes) {
                if (attr_name.startsWith("on")) {
                    continue;
                }
                out.push("W.node.attributes[\"" + attr_name + "\"] = " + attributes[attr_name] + ";");
            }
            out.push("};");

            out.push("W._widget_digest();");
        }

        if (ctx.script) {
            out.push("//--------------------------------------------------------------------------------");
            out.push(ctx.script);
            out.push("//--------------------------------------------------------------------------------");
        }

        out.push("};");
        out.push("})");
        out.push("//@ sourceURL=" + href);

        return out.join("\n");
    }

    function compile_recursive(tnode, ctx) {
        var out = ctx.out;
        var name = tnode.nodeName, type = tnode.nodeType;

        switch (type) {
            case 1:
            case 9: {
                if (name === "SCRIPT") {
                    if (ctx.script === undefined) {
                        ctx.script = tnode.textContent;
                    }
                    break;
                }

                if (name === "TEMPLATE" && tnode.hasAttribute("w")) {
                    var wnode = document.createElement("w");
                    for (var i = 0; i < tnode.attributes.length; i++) {
                        var attr = tnode.attributes[i];
                        var attr_name = attr.name, attr_value = attr.value;
                        if (attr_name === "w") {
                            continue;
                        }
                        wnode.setAttribute(attr_name, attr_value);
                    }
                    wnode.appendChild(document.importNode(tnode.content, true));
                    tnode = wnode;
                    name = "W";
                }

                var attributes = {}, const_attributes = {}, class_attributes = null;
                var listeners = {}, listeners_pre = {}, listeners_pst = {};
                var if_expr = null, for_expr = null, var_expr = null;

                for (var i = 0; i < tnode.attributes.length; i++) {
                    var attr = tnode.attributes[i];
                    var attr_name = attr.name, attr_value = attr.value;

                    if (attr_name[attr_name.length - 1] !== ":") {
                        const_attributes[attr_name] = attr_value;
                        continue;
                    }

                    switch (attr_name) {
                        case "for:": {
                            for_expr = attr_value;
                            break;
                        }
                        case "if:": {
                            if_expr = attr_value;
                            break;
                        }
                        case "var:": {
                            var_expr = attr_value;
                            break;
                        }
                        case "href:":
                        case "value:": {
                            if (name === "A" && attr_name === "href:" && attr_value.startsWith("javascript:")) {
                                const_attributes["href"] = "javascript:void(0)";
                                listeners["click"] = listeners["click"] || undefined;
                                listeners_pst["click"] = attr_value.substring("javascript:".length);
                                break;
                            }

                            if (attr_name === "value:") {
                                attributes["value"] = attr_value;
                                if (util.is_reference(attr_value)) {
                                    if (name === "INPUT" || name === "TEXTAREA") {
                                        listeners["input"] = listeners["input"] || undefined;
                                        listeners_pre["input"] = attr_value + " = e.target.value";
                                    } else if (name === "SELECT") {
                                        listeners["change"] = listeners["change"] || undefined;
                                        listeners_pre["change"] = attr_value + " = e.target.value";
                                    }
                                }
                                break;
                            }
                        }
                        default: {
                            if (attr_name[0] === "o" && attr_name[1] === "n") {
                                listeners[attr_name.substring(2, attr_name.length - 1)] = attr_value;
                                break;
                            }

                            if (attr_name.length > 6 && attr_name.substring(0, 6) === "class:") {
                                class_attributes = class_attributes || {};
                                class_attributes[attr_name.substring(6, attr_name.length - 1)] = attr_value;
                                break;
                            }

                            attributes[attr_name.substring(0, attr_name.length - 1)] = attr_value;
                        }
                    }
                }

                if (if_expr) {
                    out.push("if (" + if_expr + ") {");
                }

                if (var_expr) {
                    out.push("var " + var_expr + ";");
                }

                out.push("vnode = W._new_ve(pvnode, \"" + name + "\", " + type + ", " + JSON.stringify(const_attributes) + ");");

                if (class_attributes) {
                    var class_expr = attributes["class"] ? "(" + attributes["class"] + ")" : "''";
                    for (var class_name in class_attributes) {
                        class_expr += " + ((" + class_attributes[class_name] + ")?" + JSON.stringify(class_name + " ") + ":'')";
                    }
                    attributes["class"] = class_expr;
                }

                for (var attr_name in attributes) {
                    if (attr_name === "class" && "class" in const_attributes) {
                        out.push("vnode.attributes[\"class\"] = (" + JSON.stringify(const_attributes["class"] + " ") + ") + (" + attributes[attr_name] + ");");
                        continue;
                    }

                    out.push("vnode.attributes[\"" + attr_name + "\"] = (" + attributes[attr_name] + ");");
                }

                // FIXME: 处理<input type="file">的问题
                if (name === "INPUT" && const_attributes["type"] === "file") {
                    listeners['change'] = listeners['input'];
                    listeners['input'] = undefined;
                    listeners_pre["change"] = listeners_pre['input'];
                    listeners_pre['input'] = undefined;
                }

                // FIXME: 处理<select>设置value属性时，对应的<option>未选中的问题
                if (name === "OPTION") {
                    if (const_attributes["value"]) {
                        out.push("if (pvnode.name === 'SELECT' && pvnode.attributes['value'] == " + JSON.stringify(const_attributes["value"]) + ") {");
                        out.push("vnode.attributes['selected'] = 'selected';");
                        out.push("}");
                    }
                    if (attributes["value"]) {
                        out.push("if (pvnode.name === 'SELECT' && pvnode.attributes['value'] == vnode.attributes['value']) {");
                        out.push("vnode.attributes['selected'] = 'selected';");
                        out.push("}");
                    }
                }

                for (var event_name in listeners) {
                    out.push("vnode.listeners[\"" + event_name + "\"] = function*(e) {");
                    if (event_name in listeners_pre) {
                        out.push(listeners_pre[event_name] + ";");
                    }
                    if (listeners[event_name]) {
                        out.push(listeners[event_name] + ";");
                    }
                    if (event_name in listeners_pst) {
                        out.push(listeners_pst[event_name] + ";");
                    }
                    out.push("};");
                }

                // FIXME: 更严谨的判断是否为控件
                if (name.indexOf("-") !== -1) {
                    out.push("vnode.listeners[\"emit\"] = (function(vnode) {");
                    out.push("return function(e) {");
                    for (var attr_name in attributes) {
                        out.push("vnode.attributes[\"" + attr_name + "\"] = e.detail[\"" + attr_name + "\"];");
                        if (util.is_reference(attributes[attr_name])) {
                            out.push(attributes[attr_name] + " = e.detail[\"" + attr_name + "\"];");
                        }
                        out.push("W.digest();");
                    }
                    out.push("};");
                    out.push("})(vnode);");
                }

                if (tnode.childNodes.length) {
                    out.push("pvnode = vnode;");

                    var for_var;
                    if (for_expr) {
                        for_var = for_expr.trim().split(" ")[0];
                        out.push("var " + for_var + "$index = 0;");
                        out.push("for (var " + for_expr + ") {");
                        out.push("(function(" + for_var + ", " + for_var + "$index) {");
                    }

                    for (var i = 0; i < tnode.childNodes.length; i++) {
                        var child_tnode = tnode.childNodes[i];
                        compile_recursive(child_tnode, ctx);
                    }

                    if (for_expr) {
                        out.push("})(" + for_var + ", " + for_var + "$index++);");
                        out.push("}");
                    }

                    out.push("pvnode = pvnode.parent;");
                }

                if (if_expr) {
                    out.push("}");
                }

                break;
            }

            case 3: {
                var text_content = tnode.textContent;
                var from, to, expr;
                while (text_content && ((from = text_content.indexOf("${")) !== -1)) {
                    to = from + 2;

                    var count = 1;
                    loop:
                    for (;;) {
                        switch (text_content.charAt(to)) {
                            case "{": {
                                count++;
                                break;
                            }
                            case "}": {
                                count--;
                                if (count === 0) {
                                    break loop;
                                }
                                break;
                            }
                            case "": {
                                throw "找不到匹配的\"}\"";
                            }
                        }
                        to++;
                    }

                    if (from > 0) {
                        out.push("vnode = W._new_vt(pvnode, " + JSON.stringify(text_content.substring(0, from)) + ");");
                    }

                    expr = text_content.substring(from + 2, to);
                    out.push("vnode = W._new_vt(pvnode, " + expr + ");");

                    text_content = text_content.substring(to + 1);
                }

                out.push("vnode = W._new_vt(pvnode, " + JSON.stringify(text_content) + ");");

                break;
            }

            default: {
                // FIXME
            }
        }
    }

    function list_page() {
        var ret = [];
        for (var id in PAGE_W) {
            ret.push(PAGE_W[id]);
        }
        return ret;
    }

    function load_page(href) {
        href = conf.base + href;
        return new Promise(function(resolve, reject) {
            var page = PAGE_CACHE[href];
            if (page) {
                resolve(page);
            } else {
                util.ajax_get(href).then(function(html) {
                    page = PAGE_CACHE[href] = parse_page(href, html);
                    resolve(page);
                });
            }
        });
    }

    function reload_page(href) {
        href = conf.base + href;
        util.ajax_get(href).then(function(html) {
            var page_prev = PAGE_CACHE[href];
            try {
                page = PAGE_CACHE[href] = parse_page(href, html);
            } catch (e) {
                return;
            }
            page.then(function(page) {
                var W;
                for (var id in PAGE_W) {
                    if (PAGE_W[id].page.href === page.href) {
                        W = PAGE_W[id];
                        break;
                    }
                }

                if (!W) return;

                var sections = page.script.split("//--------------------------------------------------------------------------------");
                if (sections.length === 3) {
                    var script_user = sections[1];
                    if (page_prev === undefined || script_user !== page_prev.script_user) {
                        try {
                            new Function(script_user);
                        } catch (e) {
                            return;
                        }

                        page.factory = window.eval(page.script);
                        page.script_user = script_user;

                        var fn = page.factory();
                        W.call(fn, W).then(function() {
                            W.digest();
                            if (W._initialize) {
                                W._initialize();
                            }
                        });
                    }
                }

                var from = page.script.indexOf("W._render");
                var to = page.script.indexOf("//W._render", from);
                var script = page.script.substring(from, to);

                W._eval(script);
                W.digest();
            });
        });
    }

    return {
        list: list_page,
        load: load_page,
        parse: parse_page,
        reload: reload_page
    };
});

// ********************************************************************************************************************
// W/dom

define('W/dom', ['W/util'], function(util) {
    function NodeMark(mark_beg, mark_end) {
        this.mark_beg = mark_beg;
        this.mark_end = mark_end;
    }

    util.extend(NodeMark.prototype, {
        appendChild: function(node) {
            var parentNode = this.mark_beg.parentNode;
            node.mark_beg && parentNode.insertBefore(node.mark_beg, this.mark_end);
            parentNode.insertBefore(node.target, this.mark_end);
            node.mark_end && parentNode.insertBefore(node.mark_end, this.mark_end);
        },

        replaceChild: function(new_child, old_child) {
            if (new_child.mark_beg || old_child.mark_beg) {
                throw "Not Implemented";
            }
            this.mark_beg.parentNode.replaceChild(new_child.target, old_child.target);
        }
    });

    function NodeProxy(W, target) {
        this.W         = W;
        this.target    = target;
        this.listeners = {};
    }

    util.extend(NodeProxy.prototype, {
        addEventListener: function(event, listener) {
            var W = this.W, listeners = this.listeners;
            if (!(event in listeners)) {
                this.target.addEventListener(event, function(e) {
                    var listener = listeners[event];
                    if (listener) {
                        W.call(listener, e).then(function(ret) {
                            if (ret === undefined || ret) {
                                W.digest();
                            }
                        });
                    }
                });
            }
            listeners[event] = listener;
        },

        appendChild: function(node) {
            var parentNode = this.target;
            node.mark_beg && parentNode.appendChild(node.mark_beg);
            parentNode.appendChild(node.target);
            node.mark_end && parentNode.appendChild(node.mark_end);
        },

        remove: function(clean) {
            if (!clean) {
                this.target.remove();
            }
        },

        removeAttribute: function(attr_name, attr_value) {
            this.target.removeAttribute(attr_name);
        },

        removeEventListener: function(event, listener) {
            this.listeners[event] = null;
        },

        replaceChild: function(new_child, old_child) {
            var parentNode = this.target;
            if (old_child.mark_beg || new_child.mark_beg) {
                var mark = old_child.mark_beg || old_child.target;
                if (new_child.mark_beg) {
                    parentNode.insertBefore(new_child.mark_beg, mark);
                    parentNode.insertBefore(new_child.target, mark);
                    parentNode.insertBefore(new_child.mark_end, mark);
                } else {
                    parentNode.insertBefore(new_child.target, mark);
                }
                old_child.remove();
                return;
            }
            this.target.replaceChild(new_child.target, old_child.target);
        },

        setAttribute: function(attr_name, attr_value) {
            var is_prop = (attr_name === "value" && attr_name in this.target); // FIXME: 支持其他属性
            if (attr_value == null || attr_value === false) {
                this.target.removeAttribute(attr_name);
                if (is_prop) {
                    this.target[attr_name] = null;
                }
            } else {
                this.target.setAttribute(attr_name, attr_value);
                if (is_prop) {
                    this.target[attr_name] = attr_value;
                }
            }
        }
    });

    return {
        NodeMark: NodeMark,
        NodeProxy: NodeProxy
    };
});

// ********************************************************************************************************************
// W/WElement

define('W/WElement', ['W/class', 'W/util', 'W/page'], function(WClass, util, page) {
    var CACHE = {};

    function WElement(W) {
        this.W          = W;
        this.attributes = {};
        this.children   = [];
        this.listeners  = {};
        this.mark_beg   = document.createComment('');
        this.mark_end   = document.createComment('');

        var _target = document.createDocumentFragment();
        var self = this;
        Object.defineProperty(this, 'target', {
            get: function() {
                if (this.target_mark_beg === undefined) {
                    for (var i = 0; i < self.children.length; i++) {
                        var child = self.children[i];
                        child.mark_beg && _target.appendChild(child.mark_beg);
                        _target.appendChild(child.target);
                        child.mark_end && _target.appendChild(child.mark_end);
                    }
                }
                return _target;
            }
        });

        W._new_w_elements.push(this);
    }

    util.extend(WElement.prototype, {
        addEventListener: function(event, listener) {
            var W = this.W, listeners = this.listeners;
            listeners[event] = listener;

            var node = this;
            if (node.Wc) {
                node.Wc.on(event, function(e) {
                    var listener = listeners[event];
                    if (listener) {
                        W.call(listener, e).then(function(ret) {
                            if (ret === undefined || ret) {
                                W.digest();
                            }
                        });
                    }
                });
            }
        },

        appendChild: function(node) {
            this.children.push(node);
            var mark_end = this.target_mark_end || this.mark_end;
            if (mark_end.parentNode) {
                if (node.mark_beg || node.mark_end) {
                    throw "Not Implemented";
                }
                mark_end.parentNode.insertBefore(node.target, mark_end);
            }
        },

        initialize: function() {
            // EMPTY
        },

        remove: function(clean) {
            if (this.target_mark_beg) {
                clean = false;
                entry = CACHE[this.attributes['name']];
                if (entry.source === this) {
                    delete entry.source;
                }
            }

            for (var i = 0; i < this.children.length; i++) {
                this.children[i].remove(clean);
            }

            var node = this;
            if (node.Wc) {
                node.Wc.destroy();
                node.Wc = undefined;
            }
        },

        removeAttribute: function(attr_name) {
            delete this.attributes[attr_name];
        },

        removeEventListener: function(event, listener) {
            delete this.listeners[event];
        },

        setAttribute: function(attr_name, attr_value) {
            this.attributes[attr_name] = attr_value;

            if (attr_name === "href") {
                var node = this;

                if (node.Wc) {
                    node.Wc.destroy();
                    node.Wc = undefined;
                }

                if (attr_value) {
                    if (attr_value[0] === "#") {
                        attr_value = attr_value.substring(1);
                        var entry = CACHE[attr_value];
                        if (entry === undefined) {
                            entry = CACHE[attr_value] = { target: this };
                        } else {
                            if (entry.target && entry.target !== this) {
                                throw "Duplicated <w href=\"#" + attr_value + "\">";
                            }
                            entry.target = this;
                            if (entry.source) {
                                debugger;
                            }
                        }
                        return;
                    }

                    // FIXME
                    var Wc = node.Wc = new WClass(node.W, {
                        node: node,
                        _w: document.createElement("w")
                    });

                    // FIXME
                    for (var event in node.listeners) {
                        Wc.on(event, function(e) {
                            var listener = listeners[event];
                            if (listener) {
                                node.W.call(listener, e).then(function(ret) {
                                    if (ret === undefined || ret) {
                                        node.W.digest();
                                    }
                                });
                            }
                        });
                    }

                    var href = attr_value, index = href.indexOf("?");
                    if (index !== -1) {
                        Wc._params = {};
                        var parts = href.substring(index + 1).split("&");
                        for (var i = 0; i < parts.length; i++) {
                            var pair = parts[i].split("=");
                            Wc._params[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1]);
                        }

                        href = href.substring(0, index);
                    }
                    page.load(href).then(function(page) {
                        page.create(Wc);
                    });
                }
                return;
            }

            if (attr_name === "name") {
                var entry = CACHE[attr_value];
                if (entry === undefined) {
                    entry = CACHE[attr_value] = { source: this };
                } else {
                    if (entry.source && entry.source !== this) {
                        throw "Duplicated <w name=\"" + attr_value + "\">";
                    }
                    entry.source = this;
                    if (entry.target) { // FIXME
                        this.target_mark_beg = entry.target.mark_beg;
                        this.target_mark_end = entry.target.mark_end;
                    }
                }
            }
        }
    });

    return WElement;
});

// ********************************************************************************************************************
// W/WWidgetElement

define('W/WWidgetElement', ['W/class', 'W/util'], function(WClass, util) {
    function WWidgetElement(W, name) {
        this.W          = W;
        this.name       = name;
        this.attributes = {};
        this.children   = [];
        this.listeners  = {};
        this.mark_beg   = document.createComment('');
        this.mark_end   = document.createComment('');
        this.target     = document.createDocumentFragment();
        W._new_w_elements.push(this);
    }

    util.extend(WWidgetElement.prototype, {
        addEventListener: function(event, listener) {
            var W = this.W;
            this.listeners[event] = function(e) {
                return new Promise(function(resolve, reject) {
                    W.call(listener, e).then(function() {
                        resolve();
                        W.digest();
                    });
                });
            };
        },

        appendChild: function(node) {
            this.children.push(node);
        },

        initialize: function() {
            if (this.initialized) return;

            this.initialized = true;

            var W = this.W;
            var imports = W.page.imports;
            if (imports && imports[this.name]) {
                var widget = imports[this.name];
                // FIXME
                var Wc = this.Wc = new WClass(W, {
                    node: this,
                    _w: document.createElement("w")
                });

                widget.create(Wc);
                return;
            }

            throw "Undefined widget: " + this.name;
        },

        remove: function(clean) {
            if (this.Wc) {
                this.Wc.destroy();
                this.Wc = undefined;
            }
        },

        removeAttribute: function(attr_name) {
            delete this.attributes[attr_name];
        },

        removeEventListener: function(event, listener) {
            delete this.listeners[event];
        },

        setAttribute: function(attr_name, attr_value) {
            this.attributes[attr_name] = attr_value;
        }
    });

    return WWidgetElement;
});

// ********************************************************************************************************************
// W/vdom

define('W/vdom',
    ['W/proto', 'W/util', 'W/filter', 'W/page', 'W/dom', 'W/WElement', 'W/WWidgetElement'],
    function(P, util, filter, page, dom, WElement, WWidgetElement) {
    var SLICE = [].slice;

    var NodeMark = dom.NodeMark;
    var NodeProxy = dom.NodeProxy;

    function VBody(W) {
        this.W        = W;
        this.children = [];
    }

    util.extend(VBody.prototype, {
        merge: function(vbody_that) {
            var node = this.node, vbody_this = this;

            var n = Math.max(vbody_this.children.length, vbody_that.children.length);
            for (var i = 0; i < n; i++) {
                var child_vnode_this = vbody_this.children[i];
                var child_vnode_that = vbody_that.children[i];

                if (child_vnode_this === undefined) {
                    node.appendChild(child_vnode_that.toNode());
                    child_vnode_this = vbody_this.children[i] = child_vnode_that;
                    child_vnode_this.parent = vbody_this;
                    continue;
                }

                if (child_vnode_that === undefined) {
                    child_vnode_this.remove();
                    continue;
                }

                if (child_vnode_this.name !== child_vnode_that.name) {
                    node.replaceChild(child_vnode_that.toNode(), child_vnode_this.node);
                    child_vnode_this.remove();
                    child_vnode_this = vbody_this.children[i] = child_vnode_that;
                    child_vnode_this.parent = vbody_this;
                    continue;
                }

                child_vnode_this.merge(child_vnode_that);
            }

            vbody_this.children.length = vbody_that.children.length;
        },

        remove: function() {
            for (var i = 0; i < this.children.length; i++) {
                this.children[i].remove(true);
            }

            var mark_beg = this.node.mark_beg, mark_end = this.node.mark_end;
            var node;
            while ((node = mark_beg.nextSibling) !== mark_end) {
                node.remove();
            }
        },

        toNode: function(mark_beg, mark_end) {
            var node = this.node = new NodeMark(mark_beg, mark_end);

            for (var i = 0; i < this.children.length; i++) {
                var child_node = this.children[i].toNode()
                node.appendChild(child_node);
            }

            return node;
        }
    });

    function VElement(W, name, type, attributes, parent) {
        this.W          = W;
        this.name       = name;
        this.type       = type;
        this.attributes = attributes;
        this.children   = [];
        this.listeners  = {};
        this.parent     = parent;
        if (parent) {
            parent.children.push(this);
        }
    }

    util.extend(VElement.prototype, {
        merge: function(vnode_that) {
            var node = this.node, vnode_this = this;
            var diff = false;

            for (var attr_name in vnode_this.attributes) {
                if (attr_name in vnode_that.attributes) {
                    var attr_value = vnode_that.attributes[attr_name];
                    if (vnode_this.attributes[attr_name] !== attr_value) {
                        node.setAttribute(attr_name, attr_value);
                        diff = true;
                    }
                } else {
                    node.removeAttribute(attr_name);
                    diff = true;
                }
            }

            for (var attr_name in vnode_that.attributes) {
                if (attr_name in vnode_this.attributes) {
                    continue;
                }
                node.setAttribute(attr_name, vnode_that.attributes[attr_name]);
                diff = true;
            }

            vnode_this.attributes = vnode_that.attributes;

            for (var event_name in vnode_this.listeners) {
                node.removeEventListener(event_name, vnode_this.listeners[event_name]);
            }

            for (var event_name in vnode_that.listeners) {
                node.addEventListener(event_name, vnode_that.listeners[event_name]);
            }

            var n = Math.max(vnode_this.children.length, vnode_that.children.length);
            for (var i = 0; i < n; i++) {
                var child_vnode_this = vnode_this.children[i];
                var child_vnode_that = vnode_that.children[i];

                if (child_vnode_this === undefined) {
                    node.appendChild(child_vnode_that.toNode());
                    child_vnode_this = vnode_this.children[i] = child_vnode_that;
                    child_vnode_this.parent = vnode_this;
                    diff = true;
                    continue;
                }

                if (child_vnode_that === undefined) {
                    child_vnode_this.remove();
                    diff = true;
                    continue;
                }

                if (child_vnode_this.name !== child_vnode_that.name) {
                    node.replaceChild(child_vnode_that.toNode(), child_vnode_this.node);
                    child_vnode_this.remove();
                    child_vnode_this = vnode_this.children[i] = child_vnode_that;
                    child_vnode_this.parent = vnode_this;
                    diff = true;
                    continue;
                }

                diff = child_vnode_this.merge(child_vnode_that) || diff;
            }

            vnode_this.children.length = vnode_that.children.length;

            // FIXME
            if (diff && node instanceof WWidgetElement) {
                node.children.length = vnode_this.children.length;
                var Wc = node.Wc;
                if (Wc && Wc._widget_digest) {
                    Wc._widget_digest();
                }
            }

            return diff;
        },

        remove: function(clean) {
            for (var i = 0; i < this.children.length; i++) {
                this.children[i].remove(clean);
            }
            this.node.remove(clean);
        },

        toNode: function() {
            var node = this.node = create_element(this.W, this.name);

            var attributes = this.attributes;
            for (var attr_name in attributes) {
                node.setAttribute(attr_name, attributes[attr_name]);
            }

            var listeners = this.listeners;
            for (var event_name in listeners) {
                node.addEventListener(event_name, listeners[event_name]);
            }

            for (var i = 0; i < this.children.length; i++) {
                node.appendChild(this.children[i].toNode());
            }

            return node;
        }
    });

    function VTextNode(W, text, filters, parent) {
        this.W       = W;
        this.text    = text;
        this.filters = filters;
        this.parent  = parent;
        if (parent) {
            parent.children.push(this);
        }
    }

    util.extend(VTextNode.prototype, {
        name: "#text",
        type: 3,

        merge: function(vnode_that) {
            var node = this.node, vnode_this = this;
            if (vnode_this.text !== vnode_that.text) {
                var text = vnode_this.text = vnode_that.text;
                var html;

                if (vnode_that.filters) {
                    var ret = filter.apply(text, vnode_that.filters);
                    text = ret.text;
                    html = ret.html;
                }

                var content = text == null ? "" : text;
                if (html) {
                    var _node;
                    while ((_node = node.target.mark_beg.nextSibling) !== node.target.mark_end) {
                        _node.remove();
                    }

                    var tpl = document.createElement('template');
                    tpl.innerHTML = content;

                    var parentNode = node.target.mark_beg.parentNode;
                    parentNode.insertBefore(tpl.content, node.target.mark_end);
                } else {
                    node.target.textContent = content;
                }

                return true;
            }
            return false;
        },

        remove: function(clean) {
            this.node.remove(clean);
        },

        toNode: function() {
            var node = this.node = create_textnode(this.W, this.text, this.filters);
            return node;
        }
    });

    function create_element(W, name) {
        if (name.indexOf("-") !== -1) {
            return new WWidgetElement(W, name);
        }

        if (name === "W") {
            return new WElement(W);
        }

        return new NodeProxy(W, document.createElement(name));
    }

    function create_textnode(W, text, filters) {
        var html;

        if (filters) {
            var ret = filter.apply(text, filters);
            if (ret instanceof Promise) { // TODO: 不再支持异步filter
                var placeholder = new NodeProxy(W, document.createComment('CONTENT'));

                ret.then(function(text) {
                    var content = text == null ? "" : text;
                    if (html) {
                        var tpl = document.createElement('template');
                        tpl.innerHTML = content;
                        placeholder.target.parentNode.replaceChild(tpl.content, placeholder.target);
                    } else {
                        placeholder.target.parentNode.replaceChild(document.createTextNode(content), placeholder.target);
                    }
                });

                return placeholder;
            }

            html = ret.html;
            text = ret.text;
        }

        var content = text == null ? "" : text;
        if (html) {
            var mark = '<!---->';
            var tpl = document.createElement('template');
            tpl.innerHTML = mark + content + mark;
            var tpl_content = tpl.content;
            tpl_content.mark_beg = tpl_content.childNodes[0];
            tpl_content.mark_end = tpl_content.childNodes[tpl_content.childNodes.length - 1];
            return new NodeProxy(W, tpl_content);
        } else {
            return new NodeProxy(W, document.createTextNode(content));
        }
    }

    function clear_text(node) {
        if (node.type === 3) {
            node.text = null;
            return;
        }

        if (node.children) {
            for (var i = 0; i < node.children.length; i++) {
                clear_text(node.children[i]);
            }
        }
    }

    util.extend(P, {
        digest: function() {
            var W = this;

            if (W.finalized) {
                console.warn("对已经释放的窗口调用了W.digest()");
                return;
            }

            var count = 10;
            do {
                delete W._dirty;

                var vbody = new VBody(W);

                // 渲染出新的虚拟DOM
                W._render(vbody);

                W._new_w_elements = [];

                // 第一次digest，需要从构建实际DOM元素并关联到虚拟DOM元素，使虚机DOM元素成为镜像
                if (W._mbody === undefined) {
                    W._mbody = vbody;
                    W._nbody = vbody.toNode(W.node.mark_beg, W.node.mark_end);
                } else {
                    // 合并新的虚拟DOM到镜像
                    W._mbody.merge(vbody);
                }

                for (var i = 0; i < W._new_w_elements.length; i++) {
                    W._new_w_elements[i].initialize();
                }
            } while (W._dirty && --count);
        },

        digest_all: function(options) { // FIXME: 不使用page.list()
            options = util.extend({
                clear_text: false
            }, options);

            var Ws = page.list();
            for (var i = 0; i < Ws.length; i++) {
                var W = Ws[i];
                if (options.clear_text) {
                    clear_text(W._mbody);
                }
                if (!W.finalized) {
                    W.digest();
                }
            }
        },

        _new_ve: function(parent, name, type, attributes) {
            return new VElement(this, name, type, attributes, parent);
        },

        _new_vt: function(parent, text) {
            return new VTextNode(this, text, arguments.length > 2 ? SLICE.call(arguments, 2) : null, parent);
        }
    });

    return {
        VBody: VBody,
        VElement: VElement,
        VTextNode: VTextNode
    };
});

// ********************************************************************************************************************
// W/event

define('W/event', ['W/proto', 'W/util'], function(P, util) {
    var SLICE = [].slice;

    var scripts = {};
    function _import(src) {
        if (src in scripts) {
            return Promise.resolve(scripts[src]);
        }

        var promise = scripts[src] = new Promise(function(resolve, reject) {
            var s = document.createElement('script');
            s.setAttribute('type', 'text/javascript');
            s.setAttribute('src', src);
            s.onload = function() {
                scripts[src] = null; // FIXME
                resolve();
            };

            document.getElementsByTagName('head')[0].appendChild(s);
        });
        return promise;
    }

    util.extend(P, {
        $: function(selector) {
            var W = this, elems = document.querySelectorAll(selector);
            var result = [];

            if (elems.length) {
                var parent = W._nbody.mark_beg.parentNode;

                loop:
                for (var i = 0; i < elems.length; i++) {
                    var elem = elems[i];
                    if (parent.contains(elem)) {
                        for (var node = W._nbody.mark_beg.nextSibling; node !== W._nbody.mark_end; node = node.nextSibling) {
                            if (node.nodeType !== 1) continue;
                            if (node.contains(elem)) {
                                result.push(elem);
                                continue loop;
                            }
                        }
                    }
                }
            }
            return result;
        },

        call: function(fn) {
            var W = this;

            if (W.finalized) {
                return;
            }

            var args = SLICE.call(arguments, 1);

            if (fn.constructor.name === "GeneratorFunction") {
                var gtor = fn.apply(W, args);

                return new Promise(function(resolve, reject) {
                    function do_next() {
                        if (W.finalized) {
                            console.log("W.call()被中止", W.node);
                            return;
                        }

                        try {
                            next = gtor.next.apply(gtor, arguments);
                        } catch (e) {
                            reject(e);
                            return;
                        }

                        handle_next(next);
                    }

                    function handle_next(next) {
                        if (next.done) {
                            resolve(next.value);
                            return;
                        }

                        var value = next.value;
                        if (value instanceof Promise) {
                            value.then(do_next, function(e) {
                                var next;
                                try {
                                    next = gtor.throw(e);
                                } catch (e1) {
                                    W.fire("error", e1);
                                    return;
                                }

                                handle_next(next);
                            });
                            return;
                        }

                        throw "Expect Promise, but got: " + value;
                    }

                    do_next();
                });
            } else {
                return Promise.resolve(fn.apply(W, args));
            }
        },

        close: function(detail) {
            var W = this;
            W.fire("close", detail);
        },

        destroy: function() {
            var W = this;
            W.fire("unload", null, false);
            W.finalized = true;

            if (W.parent && W.parent.children) {
                W.parent.children.splice(W.parent.children.indexOf(W), 1);
            }

            W.page && W.page.remove(W);
            if (W._mbody) {
                W._mbody.remove();
                W._mbody = undefined;
                W._nbody = undefined;
            }
        },

        emit: function() {
            var W = this;
            W._widget_emit();

            if (W.node.listeners && W.node.listeners["emit"]) {
                var e = new Event("emit");
                e.detail = W.node.attributes;
                W.node.listeners["emit"](e); // FIXME: listener应绑定this到对应的W
            }
        },

        fire: function(event, detail, bubbles) {
            var e = new CustomEvent(event, {
                detail: detail,
                bubbles: false,
                cancelable: true
            });
            var W = this;
            if (bubbles || bubbles === undefined) {
                while (W && W._w) {
                    if (!W._w.dispatchEvent(e)) {
                        break;
                    }
                    W = W.parent;
                }
            } else {
                W._w && W._w.dispatchEvent(e);
            }
        },

        main: function(dependencies, factory) {
            var W = this;
            if (arguments.length === 1) {
                factory = dependencies;
                W._initialize = function() {
                    W.call(factory).then(function(ret) {
                        if (ret === undefined || ret) {
                            W.digest();
                        }
                    });
                };
            } else {
                W._initialize = function() {
                    var deps = SLICE.call(dependencies);
                    var next = function() {
                        var dep = deps.shift();
                        if (dep) {
                            _import(dep).then(next);
                            return;
                        }

                        W.call(factory).then(function(ret) {
                            if (ret === undefined || ret) {
                            W.digest();
                        }
                        });
                    };
                    next();
                };
            }
        },

        on: function(event, listener) {
            var W = this, w = W._w;
            w.addEventListener(event, function(e) {
                W.call(listener, e).then(function() {
                    if (!e.defaultPrevented && W._mbody) {
                        W.digest();
                    }
                });
            }, false);
        },

        open: function(name) {
            var W = this;
            return new Promise(function(resolve, reject) {
                W.fire("open", {
                    name: name,
                    onclose: resolve
                });
            });
        },

        param: function(name) {
            var W = this;
            return W._params && W._params[name];
        },

        set_interval: function(fn, interval) {
            var W = this, lock;
            var handle = window.setInterval(function() {
                if (lock) {
                    return;
                } else {
                    lock = true;
                    W.call(fn).then(function(ret) {
                        lock = false;
                        if (ret === undefined || ret) {
                            W.digest();
                        }
                    }, function(e) {
                        lock = false;
                        throw e;
                    });
                }
            }, interval);

            W.on("unload", function() {
                window.clearInterval(handle);
            });

            return handle;
        },

        set_timeout: function(fn, timeout) {
            var W = this;
            var handle = window.setTimeout(function() {
                W.call(fn).then(function(ret) {
                    if (ret === undefined || ret) {
                        W.digest();
                    }
                });
            }, timeout);

            W.on("unload", function() {
                window.clearTimeout(handle);
            });

            return handle;
        },

        sleep: function(ms) {
            return new Promise(function(resolve, reject) {
                window.setTimeout(resolve, ms);
            });
        }
    });

    function C(node, parent) {
        var W = function() {
            W.ready.apply(W, arguments);
        };
        W.__proto__ = P; // FIXME: 非标准化方法
        W.node = node;
        W.parent = parent;
        W._w = document.createElement("w"); // 事件代理
        return W;
    }

    return C;
});

// ********************************************************************************************************************
// W/ajax

define('W/ajax', ['W/proto', 'W/util'], function(P, util) {
    var ajax_count = 0;

    Object.defineProperty(P, 'ajax_count', {
        get: function() {
            return ajax_count;
        }
    });

    util.extend(P, {
        ajax: function(method, url, data, headers) {
            return new Promise(function(resolve, reject) {
                var xhr = new XMLHttpRequest();
                xhr.open(method, url, true);
                xhr.onload = function() {
                    ajax_count--;
                    if (this.status >= 400) {
                        reject(this);
                        return;
                    }

                    var body = this.responseText;
                    resolve(body ? JSON.parse(body) : undefined, this);
                };
                xhr.onerror = function() {
                    ajax_count--;
                    reject(this);
                }
                xhr.setRequestHeader("Accept", "application/json");

                if (headers) {
                    if (headers.hasOwnProperty("Content-Type")) {
                        xhr.setRequestHeader("Content-Type", headers["Content-Type"]);
                    } else {
                        xhr.setRequestHeader("Content-Type", "application/json");
                    }
                    delete headers["Content-Type"];
                    for (var header_name in headers) {
                        xhr.setRequestHeader(header_name, headers[header_name]);
                    }
                } else {
                    xhr.setRequestHeader("Content-Type", "application/json");
                }

                xhr.send(data ? JSON.stringify(data) : null);
                ajax_count++;
            });
        },

        del: function(url) {
            return this.ajax('DELETE', url, null);
        },

        get: function(url) {
            return this.ajax('GET', url, null);
        },

        post: function(url, data, request_header) {
            return this.ajax('POST', url, data);
        },

        put: function(url, data, request_header) {
            return this.ajax('PUT', url, data);
        },

        patch: function(url, data, request_header) {
            return this.ajax('PATCH', url, data);
        }
    });
});

// ********************************************************************************************************************
// W/js

define('W/js', ['W/proto', 'W/conf', 'W/util', 'W/class', 'W/page'], function(P, conf, util, WClass, page) {
    window.W = new WClass(null, { _w: document.createElement('w') });
    window.W.js = conf;
    window.W.hot_reload = function(href) {
        page.reload(href);
    };

    var seq = 0;

    function load(template) {
        var mark_beg = document.createComment('');
        var mark_end = document.createComment('');
        template.parentNode.insertBefore(mark_beg, template);
        template.parentNode.insertBefore(mark_end, template);
        template.remove();
        page.parse("#" + (seq++), template.content).then(function(page) {
            var W = new WClass(window.W, {
                node: { mark_beg: mark_beg, mark_end: mark_end },
                _w: document.createElement('w')
            });
            page.create(W);
        });
    }

    util.extend(P, {
        load: load
    });

    document.addEventListener("DOMContentLoaded", function() {
        var templates = document.querySelectorAll("template[w-app]");
        for (var i = 0; i < templates.length; i++) {
            load(templates[i]);
        }
    }, false);
});

})();
