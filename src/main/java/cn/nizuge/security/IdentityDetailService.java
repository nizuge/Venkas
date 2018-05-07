package cn.nizuge.security;

import cn.nizuge.mongo.MongoDB;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Component
public class IdentityDetailService implements UserDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(IdentityDetailService.class);

    @Autowired
    MongoDB mongoDB;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long startTime = System.currentTimeMillis();
        Document result = mongoDB.getSecurityUserCollection().find(eq("ZID",username)).first();
        if(result==null){
            throw new UsernameNotFoundException(username+" not exist");
        }
        logger.info("用户登录："+username);
        Integer access = null;
        try {
            access = result.getInteger("ACCESS");
        }catch (Exception e){
            logger.error("用户:"+username+"权限等级获取失败");
            logger.error(e.getMessage());
            access = 1;
        }
        //装配到UserDetails，相当于生成了一个<user>标签
        UserDetails userDetails = new User(result.getString("ZID"),result.getString("PWD"), true, true, true, true,getAuthorities(access) );
        try {
            if( System.currentTimeMillis()-startTime < 2000)
                Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    public Collection<GrantedAuthority> getAuthorities(int access){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(3);
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(access==5){
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if(access == 7){
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authList.add(new SimpleGrantedAuthority("ROLE_SYS"));
        }
        return authList;
    }

}