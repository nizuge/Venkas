package com.nizuge.controller;


import com.nizuge.config.GeneralConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class MainController{

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

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

    @RequestMapping(value = "/logout")
    public String loginout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            logger.info("用户"+userDetails.getUsername()+"进行注销");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";

    }

}
