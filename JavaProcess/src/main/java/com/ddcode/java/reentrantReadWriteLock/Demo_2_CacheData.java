package com.ddcode.java.reentrantReadWriteLock;

import com.ddcode.java.reentrantReadWriteLock.dao.EmpDao;
import com.ddcode.java.reentrantReadWriteLock.dao.EmpDaoCache;
import com.ddcode.java.reentrantReadWriteLock.dao.EmpDaoCachePro;
import com.ddcode.java.reentrantReadWriteLock.po.Emp;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.cacheData")
public class Demo_2_CacheData {

    public static void main(String[] args) {
//        test1();
        test2();
    }


    public static void test1(){
        EmpDao empDao = new EmpDao();
        Emp emp = empDao.queryOne(Emp.class, "select * from emp where empno = ?", 7369);
        log.info("emp {}", emp);

        empDao.update("update emp set sal = ? where empno = ?", 20000, 7369);
        emp = empDao.queryOne(Emp.class, "select * from emp where empno = ?", 7369);
        log.info("emp {}", emp);
    }


    public static void test2(){
        EmpDao empDao = new EmpDaoCachePro();
        Emp emp = empDao.queryOne(Emp.class, "select * from emp where empno = ?", 7369);
        log.info("emp {}", emp);

        emp = empDao.queryOne(Emp.class, "select * from emp where empno = ?", 7369);
        log.info("emp {}", emp);

        emp = empDao.queryOne(Emp.class, "select * from emp where empno = ?", 7369);
        log.info("emp {}", emp);

        log.info("更新一次");
        empDao.update("update emp set sal = ? where empno = ?", 20000, 7369);

        emp = empDao.queryOne(Emp.class, "select * from emp where empno = ?", 7369);
        log.info("emp {}", emp);
    }
}
