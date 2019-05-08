package com.ppmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/5/7
 * com.ppmall.util
 */
@Slf4j
public class JsonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	static {

		// 对象所有字段全部转换
		objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
		// 取消默认转换 timestamps 形式
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		// 忽略空 bean 转 json 错误
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
		// 日期格式统一
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 忽略属性在 Json 中存在，但是 Bean 中不存在的情况
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
	}

	/**
	 * obj 转 String
	 * @param obj 待转化对象
	 * @param <T> 泛型
	 * @return String or null
	 */
	public static <T> String obj2String (T obj){
			if (obj == null){
				return null;
			}
		try {
			return obj instanceof String ? String.valueOf(obj) :objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			log.warn("Parse error",e);
			return null;
		}
	}

	/**
	 * obj 转 String 美化版
	 * @param obj 待转化对象
	 * @param <T> 泛型
	 * @return String or null
	 */
	public static <T> String obj2StringPretty (T obj){
		if (obj == null){
			return null;
		}
		try {
			return obj instanceof String ? String.valueOf(obj) :objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (IOException e) {
			log.warn("Parse error",e);
			return null;
		}
	}

	/**
	 * String 转 obj
	 * @param string 待转化 Sting
	 * @param clazz 指定 class
	 * @param <T> 泛型
	 * @return T
	 */
	public  static <T> T string2Obj(String string,Class<T> clazz){
		if (StringUtils.isEmpty(string) || clazz == null){
			return null;
		}
		try {
			return clazz.equals(String.class)? (T) string :objectMapper.readValue(string,clazz);
		} catch (IOException e) {
			log.warn("Parse error",e);
			return null;
		}
	}

	/**
	 * 嵌套类型（List/Map/Set）反序列化
	 * @param string 待转化 Sting
	 * @param typeReference typeReference
	 * @param <T> 泛型
	 * @return T
	 */
	public static  <T> T string2Obj(String string, TypeReference typeReference){
		if (StringUtils.isEmpty(string) || typeReference == null){
			return null;
		}
		try {
			return (T) (typeReference.getType().equals(String.class) ? string : objectMapper.readValue(string, typeReference));
		} catch (IOException e) {
			log.warn("Parse error",e);
			return null;
		}
	}

	/**
	 * 嵌套类型（List/Map/Set）反序列化
	 * @param string 待转化 Sting
	 * @param collectionClass 集合类
	 * @param elementClasses 转换类
	 * @param <T> 泛型
	 * @return T
	 */
	public static <T> T string2Obj(String string,Class<?> collectionClass,Class<?>... elementClasses){
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
		try {
			return objectMapper.readValue(string,javaType);
		} catch (IOException e) {
			log.warn("Parse error",e);
			return null;
		}
	}

}
