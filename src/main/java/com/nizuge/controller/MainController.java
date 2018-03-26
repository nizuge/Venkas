package com.nizuge.controller;


import com.nizuge.config.GeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
