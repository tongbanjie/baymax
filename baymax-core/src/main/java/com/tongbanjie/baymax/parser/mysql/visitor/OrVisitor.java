package com.tongbanjie.baymax.parser.mysql.visitor;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.wall.spi.WallVisitorUtils;
import com.tongbanjie.baymax.parser.utils.BooleanUtil;

import java.util.*;

/**
 * 解析有Or的Where条件 返回List<List<TableStat.Condition>>
 *     [and] or [and]
 *
 * @Author si.dawei
 *
 */
public class OrVisitor extends MySqlSchemaStatVisitor {

    public List<OrEntity> orEntity = new LinkedList<OrEntity>();

    @Override
	public boolean visit(SQLBinaryOpExpr x) {
        x.getLeft().setParent(x);
        x.getRight().setParent(x);

        if (!SQLBinaryOperator.BooleanOr.equals(x.getOperator())) {
            return super.visit(x);
        }

        // true * (a and b) = (a and b) 没有意义
        if (Boolean.TRUE.equals(WallVisitorUtils.getValue(x))) {
            return false;
        }

        // 这是一个Or表达式 这个or代表的集合是要和当前已经获取的Condition列表 相乘的.
        orEntity.add(new OrEntity(this, x));
        return false;
    }
    @Override
    public void endVisit(MySqlDeleteStatement x) {

    }
    public void reset() {
        getConditions().clear();
        orEntity = new LinkedList<OrEntity>();
    }

    public static class OrEntity{

        private OrVisitor orVisitor;

        private SQLObject x;

        private List<TableStat.Condition> conditions;

        public OrEntity(OrVisitor orVisitor, SQLObject x){
            this.orVisitor = orVisitor;
            this.x = x;
        }

        public void setConditions(List<TableStat.Condition> conditions0, List<OrEntity> entitys){
            // copy
            if (conditions0 != null && conditions0.size() != 0){
                this.conditions = new ArrayList<TableStat.Condition>(conditions0.size());
                for (TableStat.Condition con : conditions0){
                    this.conditions.add(con);
                }
            }
            if (entitys != null){
                for (OrEntity entity : entitys){
                    entity.conditions = this.conditions;
                }
            }
        }

        private List<List<TableStat.Condition>> mergeConditions(){

            List<List<TableStat.Condition>> orConditions = null;

            if (x instanceof SQLBinaryOpExpr){
                SQLExpr leftExpr = ((SQLBinaryOpExpr) x).getLeft();
                SQLExpr rightExpr = ((SQLBinaryOpExpr) x).getRight();


                List<List<TableStat.Condition>> left = null;
                List<List<TableStat.Condition>> right = null;

                if (!BooleanUtil.isConditionAlwaysFalse(leftExpr)){
                    left = new OrEntity(orVisitor, leftExpr).getOrConditions();
                }

                if (!BooleanUtil.isConditionAlwaysFalse(rightExpr)){
                    right = new OrEntity(orVisitor, (rightExpr)).getOrConditions();
                }

                orConditions = new ArrayList<List<TableStat.Condition>>();
                if (conditions != null && conditions.size() > 0){
                    // 做笛卡尔乘
                    ArrayList<List<TableStat.Condition>> conditionList = new ArrayList<List<TableStat.Condition>>();
                    conditionList.add(conditions);
                    if (left != null){
                        left = dikerMerge(left, conditionList);
                    }
                    if (right != null){
                        right = dikerMerge(right, conditionList);
                    }
                }
                if (left != null){
                    orConditions.addAll(left);
                }
                if (right != null){
                    orConditions.addAll(right);
                }
            }

            return orConditions;
        }

        public List<List<TableStat.Condition>> dikerMerge(List<OrEntity> entities){
            List<List<TableStat.Condition>> result = entities.get(0).mergeConditions();
            if (entities.size() == 1){
                return result;
            }
            // 做笛卡尔乘
            for (int i = 1; i < entities.size(); i++) {
                List<List<TableStat.Condition>> merge =  entities.get(i).mergeConditions();
                result = dikerMerge(result, merge);
            }
            return result;
        }

        /**
         * 笛卡尔乘
         * @param c1
         * @param c2
         * @return
         */
        public List<List<TableStat.Condition>> dikerMerge(List<List<TableStat.Condition>> c1, List<List<TableStat.Condition>> c2){
            if (c1 == null && c2 == null){
                return null;
            }
            if (c1 == null){
                return c2;
            }
            if (c2 == null){
                return c1;
            }
            List<List<TableStat.Condition>> result = new ArrayList<List<TableStat.Condition>>();
            for (Iterator<List<TableStat.Condition>> iteratorc1 = c1.iterator(); iteratorc1.hasNext(); ) {
                List<TableStat.Condition> inc1 = iteratorc1.next();
                if (inc1 == null){
                    continue;
                }
                for (int i = 0; i < c2.size(); i++) {
                    List<TableStat.Condition> inc2 = c2.get(i);
                    if (inc2 == null){
                        continue;
                    }
                    List<TableStat.Condition> newList = new ArrayList<TableStat.Condition>(inc1);
                    newList.addAll(inc2);
                    // or true
                    result.add(newList);
                }
                iteratorc1.remove();
            }
            return result;
        }

        public List<List<TableStat.Condition>> getOrConditions() {
            // 遍历
            orVisitor.accept(x);
            // 获取Or节点
            List<OrEntity> entitys = orVisitor.orEntity;
            // 设置条件
            setConditions(orVisitor.getConditions(), entitys);
            //
            orVisitor.reset();

            if (entitys != null && entitys.size() != 0){
                // 笛卡尔乘积
                return dikerMerge(entitys);
            }else {
                List<List<TableStat.Condition>> conditions = new ArrayList<List<TableStat.Condition>>(1);
                conditions.add(this.conditions);
                return conditions;
            }
        }
    }
}
