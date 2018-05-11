package cn.nizuge.mongo.dao;

import cn.nizuge.mongo.AdherentService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VenkasDB {

    private MongoClient mongoClient;
    private MongoDatabase database ;

    @Autowired
    AdherentService adherentService;

    public void initVenkasDB(){
        this.mongoClient = new MongoClient("127.0.0.1");
        this.database = mongoClient.getDatabase("Venkas");
        adherentService.collectionInit(database.getCollection("adherent"));
    }

    public MongoCollection<Document> getCollection(String name){
        return database.getCollection(name);
    }

}
