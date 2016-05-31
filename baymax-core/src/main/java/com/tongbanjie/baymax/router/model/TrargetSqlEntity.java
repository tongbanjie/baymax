package com.tongbanjie.baymax.router.model;

import com.tongbanjie.baymax.parser.model.SqlType;

/**
 * 路由后用于执行的SQL
 * 
 * @author dawei
 *
 */
public class TrargetSqlEntity {
	
	/**
	 * 如果partition == null 表示在默认分区上执行
     *
     * 取值DataSourceGroup.identity
	 */
	private String partition;

    /**
     * 逻辑表名 sql 中使用的表名 实际没有这个表
     */
	private String logicTableName;

    /**
     * 目标表名 如 order_12
     */
	private String targetTableName;

    /**
     * 原始的sql语句
     */
	private String originalSql;

    /**
     * 改写后的sql语句(表名,limit,聚合函数等)
     */
	private String targetSql;

    /**
     * sql类型
     */
	private SqlType sqlType;

    /**
     * 需要改写的limit值
     */
    private int limitStart;

    /**
     * 需要改写的limit值
     */
    private int limitSize;
	
	@Override
	public String toString() {
		return new StringBuffer()
		.append(String.format("[partition:%s]\n[logicTableName:%s]\n[targetTableName:%s]\n[originalSql:%s]\n[targetSql:%s]\n",
                new Object[]{partition, logicTableName, targetTableName, originalSql, targetSql == null ? "" : targetSql.replaceAll("\n", " ")})).toString();
				
	}

	public String getLogicTableName() {
		return logicTableName;
	}

	public void setLogicTableName(String logicTableName) {
		this.logicTableName = logicTableName;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getOriginalSql() {
		return originalSql;
	}

	public void setOriginalSql(String originalSql) {
		this.originalSql = originalSql;
	}

	public String getTargetSql() {
		return targetSql;
	}

	public void setTargetSql(String targetSql) {
		this.targetSql = targetSql;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public SqlType getSqlType() {
		return sqlType;
	}

	public void setSqlType(SqlType sqlType) {
		this.sqlType = sqlType;
	}

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(int limitSize) {
        this.limitSize = limitSize;
    }
}
