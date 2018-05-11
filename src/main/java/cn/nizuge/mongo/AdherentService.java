package cn.nizuge.mongo;

import cn.nizuge.quadrant.pojo.Adherent;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AdherentService {

    void collectionInit(MongoCollection<Document> collection);

    int registerAdherent(Adherent adherent);

    boolean updateAdherentInfo(Map<Object, Object> updateInfo);

    FindIterable<Document> selectOneAdherent(String username);

}
