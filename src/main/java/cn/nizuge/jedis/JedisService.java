package cn.nizuge.jedis;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class JedisService {
    private static Jedis jedis = new Jedis("127.0.0.1",6379);

    public Jedis getJedis() {
        return jedis;
    }

    public Object  getObject(String key){
        if(jedis.exists(key.getBytes())){
            byte[] b = jedis.get(key.getBytes());
            return JSerialize.unserizlize(b);
        }
        return null;
    }
    public String setObject(String key,Object obj){
        return jedis.set(key.getBytes(),JSerialize.serialize(obj));
    }

    public String setString(String key,String value){
        return jedis.set(key,value);
    }

    public String getString(String key){
        return jedis.get(key);
    }

    public boolean existsKey(String key){
        return jedis.exists(key);
    }
}

