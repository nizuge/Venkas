package cn.nizuge.security;

import cn.nizuge.jedis.JedisService;
import cn.nizuge.mongo.dao.AdherantDao;
import cn.nizuge.mongo.dao.RSAKeyPair;
import cn.nizuge.quadrant.pojo.Adherent;
import cn.nizuge.util.RSAUtil;
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

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Component
public class IdentityDetailService implements UserDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(IdentityDetailService.class);
    @Autowired
    AdherantDao adherantDao;
    @Autowired
    RSAUtil rsaUtil;
    @Autowired
    RSAKeyPair rsaKeyPair;
    @Autowired
    JedisService jedisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long startTime = System.currentTimeMillis();
        Integer access;
        String password;
        Object adherentObj = jedisService.getObject("ZID:"+username);
        if(adherentObj != null && adherentObj instanceof Adherent){
            access = ((Adherent) adherentObj).getAccess();
            password = ((Adherent) adherentObj).getPassword();
        }else {
            Document result = adherantDao.selectOneAdherent(username).first();
            if(result==null){
                throw new UsernameNotFoundException(username+" not exist");
            }
            access = result.getInteger("ACCESS");
            password = result.getString("PWD");
            jedisService.setObject("ZID:"+username,new Adherent(username,password,access));
        }
        logger.info("用户登录："+username);

        RSAPrivateKey rsaPrivateKey = null;
        String decodePassword = "";
        try {
            rsaPrivateKey = rsaUtil.getPrivateKey(rsaKeyPair.getBase64PrivateKey());
            decodePassword = rsaUtil.privateDecrypt(password,rsaPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.error(e.getMessage());
        }

        //装配到UserDetails，相当于生成了一个<user>标签
        UserDetails userDetails = new User(username, decodePassword, true, true, true, true,getAuthorities(access) );
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
