package cn.nizuge.mongo;

import cn.nizuge.quadrant.pojo.Adherent;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MongoDBService {

    int registerAdherent(Adherent adherent);

    boolean updateAdherentInfo(Map<Object, Object> updateInfo);

    MongoCollection<Document> getSecurityUserCollection();
}
