package com.tongbanjie.baymax.parser.mysql;

import java.util.List;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.parser.SqlParser;
import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.parser.utils.SqlTypeUtil;
import com.tongbanjie.baymax.router.model.ExecutePlan;

/**
 * explan命令解析器
 */
public class MySqlExplainSqlParser  implements SqlParser {
	
	private SqlParser sqlParser;
	
	@Override
	public void init(String sql, List<Object> parameters) {
		String newSQL = sql.substring(7).trim();

		SqlType sqlType = SqlTypeUtil.getSqlType(newSQL);

		sqlParser = SqlParserFactory.getParser(sqlType);

		if (sqlParser == null) {
			 throw new BayMaxException("Explain未找到解析器");
		}
		sqlParser.init(newSQL, parameters);
	}

	@Override
	public void parse(ParseResult result) {
		sqlParser.parse(result);
	}

	@Override
	public void changeSql(ParseResult result, ExecutePlan plan) {
		sqlParser.changeSql(result, plan);
	}

}
