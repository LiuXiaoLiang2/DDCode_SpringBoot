package com.ddcode.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis配置类
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Resource
    public RedisProperties redisProperties;


    public static Map<Integer, RedisTemplate<Serializable, Object>> redisTemplateMap = new HashMap<>();

    @PostConstruct
    public void initRedisTemp() throws Exception {
        for (int i = 0; i <= 15; i++) {
            redisTemplateMap.put(i, getRedisTemplate(i));
        }
    }

    /**
     * 获取redisTemplate实例
     *
     * @param db
     * @return
     */
    public RedisTemplate<Serializable, Object> getRedisTemplate(int db) {
        final RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory factory = factory();
        factory.setDatabase(db);
        redisTemplate.setConnectionFactory(factory);
        //这两步必须在后面执行
        //设置以后再重置属性
        factory.afterPropertiesSet();
        factory.resetConnection();
        return serializer(redisTemplate);
    }

    /**
     * redis单机配置
     *
     * @return
     */
    public RedisStandaloneConfiguration redisConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        //设置密码
        if (redisProperties.getPassword() != null) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        return redisStandaloneConfiguration;
    }

    /**
     * redis哨兵配置
     *
     * @return
     */
    public RedisSentinelConfiguration getSentinelConfiguration() {
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        if (sentinel != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.setMaster(sentinel.getMaster());
            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                config.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            config.setSentinels(createSentinels(sentinel));
            return config;
        }
        return null;
    }

    /**
     * 获取哨兵节点
     *
     * @param sentinel
     * @return
     */
    public List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            String[] parts = StringUtils.split(node, ":");
            Assert.state(parts.length == 2, "redis哨兵地址配置不合法！");
            nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
        }
        return nodes;
    }

    /**
     * redis集群配置
     *
     * @return
     */
    public RedisClusterConfiguration getRedisClusterConfiguration() {
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (cluster != null) {
            RedisClusterConfiguration config = new RedisClusterConfiguration();
            config.setClusterNodes(createCluster(cluster));
            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                config.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            config.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
            return config;
        }
        return null;
    }

    /**
     * 获取集群节点
     *
     * @param cluster
     * @return
     */
    public List<RedisNode> createCluster(RedisProperties.Cluster cluster) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : cluster.getNodes()) {
            String[] parts = StringUtils.split(node, ":");
            Assert.state(parts.length == 2, "redis哨兵地址配置不合法！");
            nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
        }
        return nodes;
    }


    /**
     * 连接池配置
     *
     * @return
     */
    public GenericObjectPoolConfig redisPool() {
        GenericObjectPoolConfig genericObjectPoolConfig =
                new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        genericObjectPoolConfig.setTestOnBorrow(true);
        genericObjectPoolConfig.setTestWhileIdle(true);
        genericObjectPoolConfig.setTestOnReturn(false);
        genericObjectPoolConfig.setMaxWaitMillis(5000);
        return genericObjectPoolConfig;
    }

    /**
     * redis客户端配置
     *
     * @return
     */
    public LettuceClientConfiguration clientConfiguration() {
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.commandTimeout(redisProperties.getLettuce().getShutdownTimeout());
        builder.shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout());
        builder.poolConfig(redisPool());
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
        return lettuceClientConfiguration;
    }

    /**
     * redis获取连接工厂
     *
     * @return
     */
    @Scope(scopeName = "prototype")
    public LettuceConnectionFactory factory() {
        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = null;
        if (redisProperties.getSentinel() == null && redisProperties.getCluster() == null) {  //单机模式
            lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration(), clientConfiguration());
            // 属性设置后
            //lettuceConnectionFactory.afterPropertiesSet();
            // 重置连接
            //lettuceConnectionFactory.resetConnection();
        } else if (redisProperties.getCluster() == null) {                                      //哨兵模式
            lettuceConnectionFactory = new LettuceConnectionFactory(getSentinelConfiguration(), clientConfiguration());
            lettuceConnectionFactory.afterPropertiesSet();
            // 重置连接
            //lettuceConnectionFactory.resetConnection();
        } else {                                                                                 //集群模式
            lettuceConnectionFactory = new LettuceConnectionFactory(getRedisClusterConfiguration(), clientConfiguration());
            lettuceConnectionFactory.afterPropertiesSet();
            // 重置连接
            //lettuceConnectionFactory.resetConnection();
        }
        return lettuceConnectionFactory;
    }

    /**
     * 序列化
     *
     * @param redisTemplate
     * @return
     */
    public RedisTemplate<Serializable, Object> serializer(RedisTemplate redisTemplate) {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
//        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }



}
