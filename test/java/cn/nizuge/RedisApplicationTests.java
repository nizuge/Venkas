package cn.nizuge;

import cn.nizuge.jedis.JedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableScheduling
@EnableCaching
public class RedisApplicationTests {

	@Autowired
	JedisService jedisService;

	@Test
	public void contextLoads() {
		Jedis jedis = jedisService.getJedis();
		Assert.assertEquals("111",jedis.get("aaa"));
	}

}
