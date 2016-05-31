package com.tongbanjie.baymax.parser;

import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.util.List;

/**
 * sql解析器
 */
public interface SqlParser {

    /**
     * 解析器初始化
     * @param sql 原始sql
     * @param parameters sql参数
     */
    void init(String sql, List<Object> parameters);

    /**
     * 解析sql
     * @param result 解析结果
     */
	void parse(ParseResult result);

    /**
     * sql改写 limit,聚合函数,表名,读写分离等
     * @param result
     * @param plan
     */
	void changeSql(ParseResult result, ExecutePlan plan);

}
