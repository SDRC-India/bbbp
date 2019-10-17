package org.sdrc.bbbp.util;

public class Constants {

	// This secret is hardcoded because we are using symmetric key encryption.
	// Note : while sharing code in Github/Public Platform, Please change the coded
	// secret.

	public static final String SECRET = "@@sdrc_coded_secret";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	public class Mail {
		public static final String SMTP_HOST_KEY = "smtp.host.key";
		public static final String SOCKETFACTORY_PORT_KEY = "socketFactory.port.key";
		public static final String SOCKETFACTORY_CLASS_KEY = "socketFactory.class.key";
		public static final String SMTP_AUTH_KEY = "smtp.auth.key";
		public static final String SMTP_PORT_KEY = "smtp.port.key";

		public static final String SMTP_HOST = "smtp.host";

		public static final String SOCKETFACTORY_PORT = "socketFactory.port";
		public static final String SOCKETFACTORY_CLASS = "socketFactory.class";
		public static final String SMTP_AUTH = "smtp.auth";
		public static final String SMTP_PORT = "smtp.port";
		public static final String AUTHENTICATION_USERID = "authentication.userid";
		public static final String AUTHENTICATION_PASSWORD = "authentication.password";
		public static final String TO_EMAIL_IDS = "to.email.ids";
		public static final String EMAIL_SUBJECT = 	"email.subject";
		public static final String EMAIL_DISCLAIMER = "email.disclaimer";
	}
	
	public static final String ORGANISATION_TYPE_ID = "organisation.type.id";
	public static final String CONTACT_US_TO_EMAIL = "contact.us.to.email";

}
