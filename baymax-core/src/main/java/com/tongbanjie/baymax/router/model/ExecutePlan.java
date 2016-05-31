package com.tongbanjie.baymax.router.model;

import com.tongbanjie.baymax.jdbc.merge.MergeColumn;
import com.tongbanjie.baymax.jdbc.merge.OrderbyColumn;
import com.tongbanjie.baymax.parser.model.Limit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 路由计算的结果,执行计划,描述了一个sql
 */
public class ExecutePlan {

    /**
     * 执行的类型
     */
	private ExecuteType executeType;

    /**
     * 目标sql
     */
	private List<TrargetSqlEntity> sqlList;

    /**
     * 聚合函数字段
     */
    private Map<String/*columnName*/, MergeColumn.MergeType>    mergeColumns;

    /**
     * 分组字段
     */
    private List<String/*columnName*/>                          groupbyColumns;

    /**
     * 排序字段
     */
    private List<OrderbyColumn>                                 orderbyColumns;

    private Limit                                               limit;

    /*------------------------------------------------------------------------------------*/

    @Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("[ExecuteType:%s]" + System.getProperty("line.separator"), executeType.getValue()));
		for(TrargetSqlEntity trargetSqlEntity : sqlList){
			sb.append("/*---------------------------------*/").append(System.getProperty("line.separator"));
			sb.append(trargetSqlEntity.toString());
		}
		sb.append("/*---------------------------------*/");
		return sb.toString();
	}

	public void addSql(TrargetSqlEntity sql){
		if(sqlList == null){
			sqlList = new ArrayList<TrargetSqlEntity>();
		}
		sqlList.add(sql);
	}

    /*------------------------------------------------------------------------------------*/

	public List<TrargetSqlEntity> getSqlList() {
		return sqlList;
	}

	public void setSqlList(List<TrargetSqlEntity> sqlList) {
		this.sqlList = sqlList;
	}

	public ExecuteType getExecuteType() {
		return executeType;
	}

	public void setExecuteType(ExecuteType executeType) {
		this.executeType = executeType;
	}

    public List<OrderbyColumn> getOrderbyColumns() {
        return orderbyColumns;
    }

    public void setOrderbyColumns(List<OrderbyColumn> orderbyColumns) {
        this.orderbyColumns = orderbyColumns;
    }

    public List<String> getGroupbyColumns() {
        return groupbyColumns;
    }

    public void setGroupbyColumns(List<String> groupbyColumns) {
        this.groupbyColumns = groupbyColumns;
    }

    public Map<String, MergeColumn.MergeType> getMergeColumns() {
        return mergeColumns;
    }

    public void setMergeColumns(Map<String, MergeColumn.MergeType> mergeColumns) {
        this.mergeColumns = mergeColumns;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }
}
