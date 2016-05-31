package com.tongbanjie.baymax.sql;

/**
 * Created by sidawei on 16/3/18.
 */
public interface SelectTestSql {
    // 单表
    String single1 = "select * from table1 where a = 1 and b = 2";
    String single2 = "select * from table1 where a = 1 or  a = 2";

    String single3 = "select * from table1 where (a = 1 or b = 2) and c = 3";
    String single4 = "select * from table1 where (a = 1 or b = 2) or c = 3";

    String single5 = "select * from table1 where (a = 1 and b = 2) and c = 3";
    String single6 = "select * from table1 where (a = 1 and b = 2) or c = 3";

    String single7 = "select * from table1 where (a = 0 or b = 1) and (c = 0 or d = 0)";
    String single8 = "select * from table1 where (a = 0 or b = 1) or (c = 0 or d = 0)";

    String single9 = "select * from table1 where (a = 0 and b = 1) and (c = 0 or d = 0)";
    String single10 = "select * from table1 where (a = 0 and b = 1) or (c = 0 or d = 0)";

    String single11 = "select * from table1 where (a = 0 or b = 1) and (c = 0 and d = 0)";
    String single12 = "select * from table1 where (a = 0 or b = 1) or (c = 0 and d = 0)";

    String single13 = "select * from table1 where (a = 0 or b = 1) and (c = 0 or d = 0)";
    String single14 = "select * from table1 where (a = 0 or b = 1) or (c = 0 or d = 0)";

    // 子查询
    String sb1 = "select * from table1 where a = (select a from table2 where b = 0)";
    String sb2 = "select * from (select a,b from table2 where b = 2) where a = 1";

    // join
    String join1 = "select * from table1 t1,table2 t2 where t1.id = t2.id and t1.id = 100";

    // left
    String join10 = "select * from table1 t1 left join table2 t2 where t1.id = t2.id and t2.id = 100";

    // self
    String join11 = "select * from table1 t1 left join table1 t2 where t1.id = t2.id and t2.id = 100";

    // right

    // union
    String union1 = "(select a from table1 where a = 1) union (select a from table2 where b = 2)";
    String union2 = "(select a from table1 where a = 1) union (select a from table2 where b = 2 or c = 3)";

    // in
    String in1 = "select a from table1 where (a in (1,2,3) or b = 1) and c = 1";

    // between
    String b1 = "select a from table1 where a = 1 and (b between 2 and ?)";




}
