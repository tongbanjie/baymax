package com.tongbanjie.baymax.parser.mysql.visitor;

import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.utils.StringUtil;

/**
 * Created by sidawei on 16/1/27.
 *
 * 表名替换,逻辑表名替换为真实表名
 */
public class ReplaceTableNameVisitor extends MySqlASTVisitorAdapter {

    private String originalName;

    private String newName;

    private boolean isReplase = false;

    private SQLIdentifierExpr node;

    public ReplaceTableNameVisitor(String originalName, String newName){
        if (originalName == null || originalName.length() == 0
                || newName == null || newName.length() == 0){
            throw new BayMaxException("替换表名不能为空:" + originalName + "," +newName);
        }

        this.originalName = originalName;
        this.newName = newName;
    }
    @Override
    public boolean visit(SQLExprTableSource astNode) {
        if (StringUtil.removeBackquote(astNode.toString()).equals(originalName)){
            if (isReplase){
                throw new BayMaxException("分区表名在一个Sql中只能出现一次:" + originalName + "," +newName);
            }else {
                node = (SQLIdentifierExpr) astNode.getExpr();
                node.setName(newName);
                isReplase = true;
            }
        }
        return true;
    }

    public void reset(){
        node.setName(originalName);
    }
}
