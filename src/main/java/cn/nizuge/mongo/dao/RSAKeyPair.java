package cn.nizuge.mongo.dao;

import cn.nizuge.quadrant.pojo.KeyPairBase64;
import cn.nizuge.util.RSAUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static com.mongodb.client.model.Filters.eq;


@Component
public class RSAKeyPair {

    private MongoCollection<Document> rsaKeyPairCollection;
    private String base64PublicKey;
    private String base64PrivateKey;
    @Autowired
    VenkasDB venkasDB;
    @Autowired
    RSAUtil rsaUtil;

    public void loadBase64KeyPair() {
        rsaKeyPairCollection = venkasDB.getCollection("key_pair");
        FindIterable<Document> documents = rsaKeyPairCollection.find(eq("KPID",0));
        if(documents.first() == null){
            KeyPairBase64 keyPairBase64 =  rsaUtil.createKeys(2048);
            base64PrivateKey = keyPairBase64.getPrivateKey();
            base64PublicKey = keyPairBase64.getPublicKey();
        }else {
            Document document = documents.first();
            base64PublicKey = document.getString("PUBKEY");
            base64PrivateKey = document.getString("PRIKEY");
        }

    }

    public void saveKeyPair(KeyPairBase64 keyPairBase64) {
        rsaKeyPairCollection.insertOne(new Document("KPID",keyPairBase64.getKpId())
                .append("PUBKEY", keyPairBase64.getPublicKey())
                .append("PRIKEY",keyPairBase64.getPrivateKey()));
    }

    public String getBase64PublicKey() {
        return base64PublicKey;
    }

    public String getBase64PrivateKey() { return base64PrivateKey;}
}
