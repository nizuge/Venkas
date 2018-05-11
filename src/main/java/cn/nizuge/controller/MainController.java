package cn.nizuge.controller;


import cn.nizuge.mongo.AdherentService;
import cn.nizuge.quadrant.pojo.Adherent;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController{

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    AdherentService adherentService;


    @RequestMapping(value = "/login")
    public String login(Map<String,String> map,HttpServletRequest request){
    String msg = "";
        if(request.getParameter("status")!=null){
            if(request.getParameter("status").equals("-1")){
                msg = "用户名或密码错误";
                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    @RequestMapping(value = "/pbc/enrollCheck")
    @ResponseBody
    public String enrollCheck(Adherent adherent){
        Map<String,Object> reply = new HashMap<>();
        int status = adherentService.registerAdherent(adherent);
        switch (status){
            case 1:
                reply.put("status",200);
                reply.put("msg","很好！<br/>你已经成功的吸引了我的注意力.....<br/>也许这里比你想象的有趣的多<br/>" +
                        "的多的多的多<br/>的多的多<br/>的多<br/>!");
                break;
            case 0:
                reply.put("status",500);
                reply.put("msg","该用户名已被注册！");
                break;
            case 2:
                reply.put("status",500);
                reply.put("msg","密码进行加密时出错！");
                break;
            default:
                reply.put("status",500);
                reply.put("msg","未知错误！");
        }
        return new JSONObject(reply).toJSONString();
    }

}
