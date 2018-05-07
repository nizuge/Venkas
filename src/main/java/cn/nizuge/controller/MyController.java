package cn.nizuge.controller;

import cn.nizuge.config.GeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MyController {

    @Autowired
    GeneralConfig config;

    @RequestMapping(value = "/test")
    public String test(Map<String,String> map){
        map.put("test",config.getTest());
        return "test";
    }
}
