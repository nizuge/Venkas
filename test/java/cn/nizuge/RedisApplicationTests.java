package cn.nizuge;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableScheduling
@EnableCaching
public class RedisApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
		//stringRedisTemplate.delete("aaa");
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
		Assert.assertEquals(new Long(4), stringRedisTemplate.opsForZSet().count("jj",-2,10));
		stringRedisTemplate.opsForHash().put("hash","hehe","xixi");
		Assert.assertEquals("xixi", stringRedisTemplate.opsForHash().get("hash","hehe"));
		//另一种简单调用redis的方式

	}

}
