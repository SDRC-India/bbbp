package org.sdrc.bbbp.cms.domain;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * 13-Jul-2018, 4:11:42 PM
 * This is a customized PostgreSQLDialect of 9.4 and above to store JSON documents efficiently in a database column using jsonb.
 * Refer to app.properties where it is used
 */
public class MyPostgreSQL94Dialect extends PostgreSQL94Dialect {

	public MyPostgreSQL94Dialect() {
		super();
//		this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
		this.registerHibernateType(
	            Types.OTHER, JsonNodeBinaryType.class.getName());
	}

}