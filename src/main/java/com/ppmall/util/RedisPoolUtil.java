package com.ppmall.util;

import com.ppmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/4/19
 * com.ppmall.util
 */

@Slf4j
public class RedisPoolUtil {
	/**
	 * Set Method
	 * @param key 待插入 key
	 * @param value 待插入的key的value
	 * @return String
	 */
	public static String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisResource();
			return jedis.set(key, value);
		} catch (Exception e) {
			log.error("set key:{} value:{},error:", key, value, e);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
					log.error("close redis conn fail : ", e);
				}
			}
		}
		return null;
	}

	/**
	 * SetEx Method
	 * @param key key
	 * @param extime 秒
	 * @param value 值
	 * @return String
	 */
	public static String setEx(String key, int extime, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisResource();
			return jedis.setex(key,extime,value);
		} catch (Exception e) {
			log.error("setex key:{} Extime:{} value:{},error:", key,extime,value, e);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
					log.error("close redis conn fail : ", e);
				}
			}
		}
		return null;
	}

	/**
	 *  expire Method
	 * @param key key
	 * @param extime 过期时间
	 * @return Long
	 */
	public static Long expire(String key, int extime) {
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisResource();
			return jedis.expire(key, extime);
		} catch (Exception e) {
			log.error("expire Error -----> key:{} extime:{},error:", key, extime, e);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
					log.error("close redis conn fail : ", e);
				}
			}
		}
		return null;
	}

	/**
	 * get Method
	 * @param key key
	 * @return value in Redis
	 */
	public static String get(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisResource();
			return jedis.get(key);
		} catch (Exception e) {
			log.error("Get Error in key :{}", key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
					log.error("close redis conn fail : ", e);
				}
			}
		}
		return null;
	}

	/**
	 * Delete Method
	 * @param key delete key
	 * @return integer number
	 */
	public static Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisResource();
			return jedis.del(key);
		} catch (Exception e) {
			log.error("delete Error in key:{}", key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
					log.error("close redis conn fail : ", e);
				}
			}
		}
		return null;
	}


}