package com.lordofthejars.querydslplugin;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.lordofthejars.querydslplugin.messages"; //$NON-NLS-1$

	public static String DriverClassName_label;
	public static String DatabaseUrl_label;

	public static String DatabaseUser_label;
	public static String DatabasePassword_label;

	public static String DriverLocation_label;
	public static String Browser_button;
	
	public static String ErrorLoadingJar_error_msg;
	public static String ErrorExportingMetadata_error_msg;

	public static String QueryDSLPlugin_error_msg;
	public static String QueryDSL_msg;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
