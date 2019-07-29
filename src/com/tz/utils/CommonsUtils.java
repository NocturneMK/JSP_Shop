package com.tz.utils;

import java.util.UUID;

/**
 * 生成UUID的工具类
 * @author Nocturne
 *
 */
public class CommonsUtils {
	
	//生成UUID
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}
