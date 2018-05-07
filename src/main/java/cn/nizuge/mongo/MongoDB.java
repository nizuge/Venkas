package cn.nizuge.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import cn.nizuge.config.GeneralConfig;
import cn.nizuge.util.MyCryption;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

@Component
public class MongoDB implements MongoDBService {
    @Autowired
    GeneralConfig config;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MongoDB.class);
    private final MongoClient mongoClient = new MongoClient("127.0.0.1");
    private final MongoDatabase database = mongoClient.getDatabase("Venkas");
    private final MongoCollection<Document> adherentCollection = database.getCollection("adherent");

    public MongoDB(){
        logger.info("======= 初始化MongoDB =======");
    }

    @Override
    public boolean registerAdherent(String name, String password) {
        FindIterable<Document> documents = adherentCollection.find(eq("ZID",name));
        if(null != documents.first()){
            logger.warn("该ZID已被占用");
            return false;
        }
        adherentCollection.insertOne(new Document("ZID",name).append("PWD", MyCryption.encrypt(password)));
        return true;
    }

    @Override
    public boolean updateAdherentInfo(Map<Object, Object> updateInfo) {
        return false;
    }

    @Override
    public MongoCollection<Document> getSecurityUserCollection() {
        return adherentCollection;
    }
    /*   waterSlideCollection.deleteOne(eq("visitor_id",visitorId));*/

        /*Bson filter = eq("visitor_id", visitorId);
        Bson change = push("videos", videoUrl);
        waterSlideCollection.updateOne(filter, change);*/




}
