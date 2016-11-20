/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.seed.utils;

import java.security.SecureRandom;
import java.util.logging.Logger;

public class Helper {

	public static final SecureRandom SR = new SecureRandom();
	public static final long THE_BEGINNING = 1420063200000l;
	private final static String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";
	private static final Logger LOGGER = Logger.getLogger("Helper");

	public static String getRandString(int len, String chars) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(SR.nextInt(chars.length())));
		}
		return sb.toString();
	}

	public static String getRandStringHard(int len) {
		return getRandString(len, "0123456789-=qwertyuiop[]\\asdfghjkl;zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:ZXCVBNM<>? ");
	}

	public static String getRandStringSimple(int len) {
		return getRandString(len, CHARS);
	}

	public static String buildID() {
		return buildID(System.currentTimeMillis());
	}

	public static String buildID(long timeMillis) {
		int smalli;
		//from 2015-01-01:00:00:00 EET
		long bigi = timeMillis - THE_BEGINNING;
		int precision = 1000000;
		bigi *= precision;
		bigi += SR.nextInt(precision);
		StringBuilder sb = new StringBuilder();
		while (bigi > 0) {
			smalli = (int) (bigi % 64);
			sb.append(CHARS.charAt(smalli));
			bigi = (bigi - smalli) / 64;
		}
		return sb.toString();
	}

	public static String capitalizeFirstLetter(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}

	public static String decapitalizeFirstLetter(String text) {
		return text.substring(0, 1).toLowerCase() + text.substring(1);
	}


	public static Long ifNullThen0(Long num) {
		return coalesce(num, 0L);
	}

	public static <T> T coalesce(T... tList) {
		for (T t : tList) {
			if (t != null) {
				return t;
			}
		}
		return null;
	}
}
