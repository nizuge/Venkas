package com.nizuge.controller;


import com.nizuge.config.GeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MainController{

    @Autowired
    GeneralConfig generalConfig;

    @RequestMapping(value = "/test")
    public String test(Map<String,String> map){
        map.put("test",generalConfig.getTest());
        return "test";
    }

    @RequestMapping(value = "/login")
    public String login(Map<String,String> map,HttpServletRequest request){
    String msg = "";
        if(request.getParameter("status")!=null){
            if(request.getParameter("status").equals("-1")){
                msg = "用户名或密码错误";
            }
        }
        map.put("msg",msg);
        return "login";
    }

}
