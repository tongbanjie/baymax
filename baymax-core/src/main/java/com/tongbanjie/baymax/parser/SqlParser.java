package com.tongbanjie.baymax.parser;

import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.util.List;

public interface SqlParser {

    void init(String sql, List<Object> parameters);
	
	void parse(ParseResult result);

	void changeSql(ParseResult result, ExecutePlan plan);

}
