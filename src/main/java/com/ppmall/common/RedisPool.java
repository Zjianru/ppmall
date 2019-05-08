package com.ppmall.common;

import com.ppmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/4/7
 * com.ppmall.common
 */
public class RedisPool {
	/**
	 * Jedis 连接池
	 */
	private static JedisPool jedisPool;
	/**
	 * redis 连接端口
	 */
	private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379"));
	/**
	 * redis 链接ip地址
	 */
	private static String redisIp = PropertiesUtil.getProperty("redis.ip", "127.0.0.1");
	/**
	 * 最大连接数
	 */
	private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
	/**
	 * Jedis连接池中idle状态的jedis实例最大个数
	 */
	private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
	/**
	 * Jedis连接池中idle状态（空闲状态）的jedis实例最小个数
	 */
	private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
	/**
	 * 从jedis连接池中获取连接时  校验并返回可用的连接 true为可用
	 */
	private static Boolean testOnBorrow = Boolean.parseBoolean((PropertiesUtil.getProperty("redis.test.borrow", "true")));
	/**
	 * 把链接放回jedis连接池时 校验并返回可用的连接
	 */
	private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));
	/**
	 * 当链接全部非空闲时 阻塞队列中其他请求  true 阻塞 直到超时
	 */
	private static Boolean blockWhenExhausted = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.blockWhenExhausted", "true"));

	/**
	 * 初始化连接池
	 */
	private static void initPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setTestOnReturn(testOnReturn);
		jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
		jedisPool = new JedisPool(jedisPoolConfig, redisIp, redisPort, 1000 * 2);
	}

	static {
		initPool();
	}

	/**
	 * 从连接池中获取Jedis对象资源
	 * @return Jedis 对象
	 */
	public static Jedis getJedisResource() {
		return jedisPool.getResource();
	}


}
