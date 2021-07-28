package com.tsf.demo.mysql.controller;

import com.tsf.demo.mysql.dao.TsfUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2021/5/27
 */
@RestController
public class QueryController {

    @Autowired
    private TsfUserDao userDao;

    @RequestMapping("query")
    public String fun() {
        String token= "";
        try {
             token = userDao.query("test-user-1");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return token;
    }

    @RequestMapping("querydm")
    public String fun2() {
        String token= "";
        try {
            token = userDao.queryDM("test-user-1");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return token;
    }

    @RequestMapping("queryoracle")
    public String fun3() {
        String token= "";
        try {
            token = userDao.queryOracle("test-user-1");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return token;
    }

}
