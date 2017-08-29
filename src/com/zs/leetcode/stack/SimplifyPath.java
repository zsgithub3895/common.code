package com.zs.leetcode.stack;

import java.util.Stack;

public class SimplifyPath {

	/*
	当遇到“/../"则需要返回上级目录，需检查上级目录是否为空。
	当遇到"/./"则表示是本级目录，无需做任何特殊操作。
	当遇到"//"则表示是本级目录，无需做任何操作。
	当遇到其他字符则表示是文件夹名，无需简化。
	当字符串是空或者遇到”/../”，则需要返回一个"/"。
	当遇见"/a//b"，则需要简化为"/a/b"。

         需要两个栈来解决问题:
	先将字符串依"/"分割出来，然后检查每个分割出来的字符串。
	当字符串为空或者为"."，不做任何操作。
	当字符串不为".."，则将字符串入栈。
	当字符串为"..", 则弹栈（返回上级目录）。*/
	
	public static void main(String[] args) {
		System.out.println(simplifyPath("/a/./b/../../c/"));
	}
	
	public static String simplifyPath(String path) {
		Stack<String> stack = new Stack<String>();
		String[] list = path.split("/");
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(".") || list[i].length() == 0) {
				continue;
			}else if (!list[i].equals("..")) {
				stack.push(list[i]);
			} else {
				if(!stack.isEmpty())
				   stack.pop();
			}
		}
	
		StringBuilder res = new StringBuilder();
		Stack<String> temp = new Stack<String>();
		while (!stack.isEmpty()) {
			temp.push(stack.pop());
		}
		while (!temp.isEmpty()) {
			res.append("/" + temp.pop());
		}
		if (res.length() == 0)
			res.append("/");
		return res.toString();
	}
}
