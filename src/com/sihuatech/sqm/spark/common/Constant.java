package com.sihuatech.sqm.spark.common;

import java.util.Arrays;
import java.util.List;

public class Constant {
	public static final String FIRST_FRAME = "FIRST_FRAME";
	public static final String ALL_PLAY = "ALL_PLAY";// 播放总次数
	public static final String ALL_USER = "ALL_USER";// 播放用户数
	public static final String ALL_START = "ALL_START";// 开机用户数
	public static final String LAG_PLAY = "LAG_PLAY_RATE";//自然缓冲率
	public static final String LAG_USER = "LAG_USER_RATE";//卡顿用户率
	public static final String LAG_FAULT = "LAG_USER_FAULT";
	public static final String LAG_CAUSE = "LAG_USER_CAUSE";
	public static final String FAULT_USER = "FAULT_USER";//故障用户数
	public static final String PLAY_SUCC = "PLAY_SUCC";//播放成功率
	public static final String OFFLINE_ALL_PLAY = "ALL_PLAY_OFFLINE";//离线播放总次数
	public static final String OFFLINE_ALL_USER = "ALL_USER_OFFLINE";//离线播放总用户数
	public static final String OFFLINE_START_USER = "ALL_START_OFFLINE";//离线开机用户数
	public static final String PLAYCOUNTS = "PLAYCOUNTS";//日点播数
	public static final String PROGRAME_TYPE = "PROGRAME_TYPE";//业务在线用户数
	public static final String BIZONLINE = "BIZONLINE";//业务分布
	public static final String LAG_PHASE = "LAG_PHASE";//卡顿分布


	public static final String KEY_PREFIX = "EPG_RSP";//epg延迟
	public static final String KEY_PREFIX_IMAGE = "IMAGE_LOAD";//图片加载及时率
	public static final String KEY_PREFIX_IMAGE_SUCC = "IMAGE_LOAD_SUCC";//图片加载成功率
	
	public static final String DIRECTORY_DELIMITER = System.getProperty("file.separator");
	public static final String DELIMITER = "-";

	/* 时间维度取值范围 */
	public static final List<String> PERIOD_ENUM = Arrays.asList("HOUR","PEAK", "DAY", "WEEK", "MONTH", "QUARTER","HALFYEAR", "YEAR");
	public static final String DOWN_BYTES = "DOWN_BYTES";

	/* 机顶盒状态任务号 */ 
	public static final String STATE_NUM = "200";// 表示所有任务
	public static final String PLAY_NUM = "201";// 表示总播放次数、流用户数
	public static final String START_NUM = "202";// 开机用户数
	/* 机顶盒状态离线分析任务枚举值 */
	public static final List<String> STATE_TASK_ENUM = Arrays.asList(STATE_NUM, PLAY_NUM,START_NUM);

	/**
	 * 播放请求日志离线分析任务号
	 */
	public static final String PLAYREQUEST_ALL_TASK = "300";// 所有任务
	public static final String PLAYREQUEST_FIRSTFRAME_TASK = "301";// 首帧
	public static final String PLAYREQUEST_BIZONLINE_TASK = "303";// 业务在线
	public static final String PLAYREQUEST_LAG_PHASE = "305";//卡顿分布
	public static final String PLAYREQUEST_FREEZE = "306";//总业务分布  HTTP流量
	public static final String STREAM_AND_PLAY_COUNT = "307";//流用户 播放次数
	/*  播放请求离线分析任务枚举值 */
	public static final List<String> PLAYREQUEST_TASK_ENUM = Arrays.asList(PLAYREQUEST_ALL_TASK,
			PLAYREQUEST_FIRSTFRAME_TASK, PLAYREQUEST_BIZONLINE_TASK,
			PLAYREQUEST_LAG_PHASE,PLAYREQUEST_FREEZE,STREAM_AND_PLAY_COUNT);
	
	/* 卡顿行为任务号 */
	public static final String LAG_NUM = "400";// 表示所有任务
	public static final String LAGUSER_NUM = "401";// 表示卡顿用户率
	public static final String FAULT_NUM = "402";//// 故障原因
	public static final String CAUSE_NUM = "403";// 故障原因分布
	public static final String NATURAL_NUM = "404";// 自然播放率
	/* 卡顿行为离线分析任务枚举值 */
	public static final List<String> LAG_TASK_ENUM = Arrays.asList(LAG_NUM, LAGUSER_NUM,FAULT_NUM,CAUSE_NUM,NATURAL_NUM);
	
	/**
	 * 播放失败日志离线分析任务号
	 */
	public static final String PLAY_FIAL_ALL_TASK = "500";
	public static final String PLAY_SUCC_TASK = "501";
	/* 播放失败日志离线分析任务枚举值 */
	public static final List<String> FAIL_TASK_ENUM = Arrays.asList(PLAY_FIAL_ALL_TASK, PLAY_SUCC_TASK);
	
	/**
	 * EPG日志离线分析任务号
	 */
	public static final String EPG_RESPONSE_ALL_TASK = "600";// 所有任务
	public static final String EPG_RESPONSE_DISTRIBUTION_TASK = "602";// EPG时延分布
	/* EPG日志离线分析任务枚举值 */
	public static final List<String> EPG_TASK_ENUM = Arrays.asList(EPG_RESPONSE_ALL_TASK,
			EPG_RESPONSE_DISTRIBUTION_TASK);

	/**
	 * 图片加载日志离线分析任务号
	 */
	public static final String IMAGE_ALL_TASK = "700";// 所有任务
	public static final String IMAGE_FAST_RATE_TASK = "701";// 图片加载及时率
	/* 图片加载离线分析任务枚举值 */
	public static final List<String> IMAGE_TASK_ENUM = Arrays.asList(IMAGE_ALL_TASK, IMAGE_FAST_RATE_TASK);
	
}
