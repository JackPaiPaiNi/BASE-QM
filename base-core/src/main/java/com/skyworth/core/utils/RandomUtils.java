package com.skyworth.core.utils;

import java.util.Random;

/**
 * 获取一个随机数，单实例每次生成的随机数不同，但多实例间每次生成的随机数相同
 */
public class RandomUtils {
	
	private RandomUtils(){};
	
	public static Random random = new Random(100000);
	
	/**
	 * 获取随机数产生器
	 */
	public static Random getRandom() {
		return RandomUtils.random;
	}
	
	public static void main(String[] args) {
		System.out.println(Math.abs(RandomUtils.getRandom().nextInt()));
		System.out.println(Math.abs(RandomUtils.getRandom().nextInt()));
		System.out.println(Math.abs(RandomUtils.getRandom().nextInt()));
		System.out.println(Math.abs(RandomUtils.getRandom().nextInt()));
		System.out.println(Math.abs(RandomUtils.getRandom().nextInt()));
	}
}
