package com.test.ORM.SORM.test;

import com.test.ORM.SORM.Core.Query;
import com.test.ORM.SORM.Core.QueryFactory;
import com.test.ORM.SORM.Utils.TimerUtils;
import com.test.ORM.SORM.Vo.empVO;

/**
 * A Test Class which can be invoked by client.
 */
public class Test {
    public static void main(String[] args) {
        useConnectionPool();
    }

    public static void useConnectionPool() {
        //2s
        TimerUtils.timer(() -> {
            for (int i = 0; i < 3000; i++) regularQuery();
        });
    }

    public static void withoutConnPool() {
        //9s
        TimerUtils.timer(() -> {
            for (int i = 0; i < 3000; i++) regularQuery();
        });
    }

    public static void regularQuery() {
        Query q = QueryFactory.createQuery();
        var sql = "select e.id, e.name, salary+bonus 'totalSalary', age, d.name 'deptName', d.address 'deptAddr' from emp e\n" +
                "join dept d on e.deptId=d.id;";
        q.queryRows(sql, empVO.class).forEach(System.out::println);
    }
}
