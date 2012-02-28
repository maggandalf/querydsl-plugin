package com.lordofthejars.querydslplugin.util;

public class StringUtils {

	private StringUtils() {
		super();
	}

	public static final boolean hasText(String text) {
		return text != null && !"".equals(text);
	}

}
