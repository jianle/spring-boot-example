package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * Created by jianle on 17-6-8
 */

@RunWith(SpringRunner.class)  
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes=Application.class)
public class ApplicationTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource datasource;

    @Test
    public void datasourceTest() {
        logger.info("Test datasource config...");
        assertThat(jdbcTemplate).isNotNull();
        assertThat(datasource).isNotNull();
        assertThat(datasource instanceof  org.apache.tomcat.jdbc.pool.DataSource);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testCaching() {
        stringRedisTemplate.opsForValue().set("key", "value", 1, TimeUnit.HOURS);
        assertThat(stringRedisTemplate.opsForValue().get("key")).isEqualTo("value");
    }

    @Test
    public void testCachingObject() throws InterruptedException {

        Map<String, String> user = new HashMap<String, String>();

        user.put("userName", "admin");
        user.put("password", "password");

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("user1", user);
        operations.set("user2", user, 1, TimeUnit.HOURS);

        Thread.sleep(1000);
        boolean exists = redisTemplate.hasKey("user2");

        if (exists) {
            logger.info("exists is true.");
        } else {
            logger.info("exists is false.");
        }

        assertThat(exists);
    }
}
