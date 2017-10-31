package com.zs.servlet.commen;

public class Constants {
	//审核状态
	public static final int AUDIT_PENDING = 0;	//待审核
	public static final int AUDIT_PASS = 1; //审核通过
	public static final int AUDIT_NOTPASS = 2; //审核不通过
	//数据来源
	public static final int ORIGIN_MANUAL = 0;//人工新增
	public static final int ORIGIN_AUTO = 1;//平台对接新增
	//数据状态
	public static final int NORMAL= 0;//正常
	public static final int IS_DELETED = 1;//删除状态
	
	/* Redis key */
	/** 球队小组赛积分排行 */
	public static final String TEAMSCORERANK = "TEAMSCORERANK";
	/** 球队排名 */
	public static final String TEAMRANK = "TEAMRANK";
	/** 球员赛事排名 */
	public static final String PLAYERCOMPETITIONRANK = "PLAYERCOMPETITIONRANK";
	/** 球员赛季排名 */
	public static final String PLAYERSEASONRANK = "PLAYERSEASONRANK";
	/** 运营位前缀 */
	public static final String OPERATEPOSITION_PREFIX = "OPERPOSI";
	
	//运营数据名称
	/** 赛事 */
	public static final String COMPETITION = "COMPETITION";
	/** 赛季 */
	public static final String SEASON = "SEASON";
	/** 轮次 */
	public static final String ROUND = "ROUND";
	/** 比赛 */
	public static final String MATCH = "MATCH";
	/** 球队 */
	public static final String TEAM = "TEAM";
	/** 球员 */
	public static final String PLAYER = "PLAYER";
	
	/* 连接返回结果 */
	public static final String EXEC_TIME_OUT = "连接超时";
	public static final String EXEC_REFUSE_CONECTION = "拒绝连接";
	public static final String EXEC_OTHER = "其他异常";
	
	/**赛事接口ID*/
	public static final int SPORT_ITEM_ID = 1;
	
	/**比赛状态*/
	public static final long NOT_START = 0;//未开始
	
	public static final int SCORE_RANK_TYPE = 1;//1-积分榜形式
	public static final int RISE_RANK_TYPE = 2;//2-胜负晋级形式
	
	public static final int ORDER_SUCCESS = 1;//预约成功
	public static final int ORDER_NOT = 0;//未预约
	
	/** 发布类型 */
	public static final int PUBLISH_RANK = 1;//发布榜单
	public static final int PUBLISH_OPERATEPOSITION = 2;//发布运营位
	
	/** 赛事信息 */
	public static final String COMPETITION_INFO = "COMPETITION_INFO";
	/** 赛季信息 */
	public static final String SEASON_INFO = "SEASON_INFO";
	/** 轮次信息 */
	public static final String STAGEROUND_INFO = "STAGEROUND_INFO";
	/** 比赛信息 */
	public static final String MATCH_INFO = "MATCH_INFO";
	/** 球队信息 */
	public static final String TEAM_INFO = "TEAM_INFO";
	/** 球员信息 */
	public static final String PLAYER_INFO = "PLAYER_INFO";
}
