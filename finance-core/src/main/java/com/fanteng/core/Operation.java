package com.fanteng.core;

public enum Operation {

	/** = */
	EQ,
	/** != */
	NE,
	/** 利用Map来进行多个等于的限制 */
	ALL_EQ,
	/** > */
	GT,
	/** >= */
	GE,
	/** < */
	LT,
	/** <= */
	LE,
	/** BETWEEN */
	BETWEEN,
	/** 左模糊 */
	LIKE_LEFT,
	/** 右模糊 */
	LIKE_RIGHT,
	/** 全模糊 */
	LIKE_ANY,
	/** in */
	IN,
	/** and */
	AND,
	/** or */
	OR,
	/** 正序排序 */
	ASC,
	/** 倒序排序 */
	DESC

}
