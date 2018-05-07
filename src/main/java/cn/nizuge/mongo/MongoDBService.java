package cn.nizuge.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MongoDBService {

    boolean registerAdherent(String name, String password);

    boolean updateAdherentInfo(Map<Object, Object> updateInfo);

    MongoCollection<Document> getSecurityUserCollection();
}
