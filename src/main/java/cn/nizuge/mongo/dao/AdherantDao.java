package cn.nizuge.mongo.dao;

import cn.nizuge.jedis.JedisService;
import cn.nizuge.mongo.AdherentService;
import cn.nizuge.quadrant.pojo.Adherent;
import cn.nizuge.util.RSAUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import cn.nizuge.config.GeneralConfig;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

@Component
public class AdherantDao implements AdherentService {


    private static final Logger logger = LoggerFactory.getLogger(AdherantDao.class);
    private static MongoCollection<Document> adherentCollection;

    @Autowired
    GeneralConfig config;
    @Autowired
    RSAUtil rsaUtil;
    @Autowired
    RSAKeyPair rsaKeyPair;
    @Autowired
    JedisService jedisService;

    @Override
    public FindIterable<Document> selectOneAdherent(String username) {
        return adherentCollection.find(eq("ZID",username));
    }

    @Override
    public void collectionInit(MongoCollection<Document> collection) {
        adherentCollection = collection;
    }

    @Override
    public int registerAdherent(Adherent adherent) {
        if(jedisService.existsKey("ZID:"+adherent.getUsername())){
            logger.warn("该ZID已被占用");
            return 0;
        }
        FindIterable<Document> documents = adherentCollection.find(eq("ZID",adherent.getUsername()));
        if(null != documents.first()){
            logger.warn("该ZID已被占用");
            return 0;
        }
        RSAPublicKey publicKey = null;
        try {
            publicKey = rsaUtil.getPublicKey(rsaKeyPair.getBase64PublicKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 2;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return 2;
        }
        adherentCollection.insertOne(new Document("ZID",adherent.getUsername())
                .append("PWD", rsaUtil.publicEncrypt(adherent.getPassword(),publicKey))
                .append("ACCESS",1));
        logger.info("用户注册："+adherent.getUsername());
        return 1;
    }

    @Override
    public boolean updateAdherentInfo(Map<Object, Object> updateInfo) {
        return false;
    }




    /*   waterSlideCollection.deleteOne(eq("visitor_id",visitorId));*/

        /*Bson filter = eq("visitor_id", visitorId);
        Bson change = push("videos", videoUrl);
        waterSlideCollection.updateOne(filter, change);*/


}
