package cn.nizuge.mongo;

import cn.nizuge.quadrant.pojo.Adherent;
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
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

@Component
public class MongoDB implements MongoDBService {
    @Autowired
    GeneralConfig config;

    private static final Logger logger = LoggerFactory.getLogger(MongoDB.class);
    private final MongoClient mongoClient = new MongoClient("127.0.0.1");
    private final MongoDatabase database = mongoClient.getDatabase("Venkas");
    private final MongoCollection<Document> adherentCollection = database.getCollection("adherent");

    public MongoDB(){
        logger.info("======= 初始化MongoDB =======");
    }

    @Override
    public int registerAdherent(Adherent adherent) {
        FindIterable<Document> documents = adherentCollection.find(eq("ZID",adherent.getUsername()));
        if(null != documents.first()){
            logger.warn("该ZID已被占用");
            return 0;
        }
        adherentCollection.insertOne(new Document("ZID",adherent.getUsername())
                .append("PWD", MyCryption.encrypt(adherent.getPassword()))
                .append("ACCESS",1));
        return 1;
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
