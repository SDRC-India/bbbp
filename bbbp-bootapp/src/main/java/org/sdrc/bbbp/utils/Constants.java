package org.sdrc.bbbp.utils;

public class Constants {

	//This secret is hardcoded because we are using symmetric key encryption.
	// Note : while sharing code in Github/Public Platform, Please change the coded secret.
	
	public static final String SECRET = "@@sdrc_coded_secret";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

}
