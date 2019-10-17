package org.sdrc.bbbp.cms.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in) Created On: 13-Jul-2018 4:19:43
 *         PM create a Hibernate UserType, which maps the CmsData object into a
 *         JSON document and defines the mapping to an SQL type.
 */
public class CmsDataJsonType {
/*implements UserType {
	
	 private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.JAVA_OBJECT };
	}

	@Override
	public Class<CmsDataJson> returnedClass() {
		return CmsDataJson.class;
	}

	@Override
	public boolean equals(final Object obj1, final Object obj2) throws HibernateException {
		if (obj1 == null) {
			return obj2 == null;
		}
		return obj1.equals(obj2);
	}

	@Override
	public int hashCode(final Object obj) throws HibernateException {
		return obj.hashCode();
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session,
			final Object owner) throws HibernateException, SQLException {
		final String cellContent = rs.getString(names[0]);
		if (cellContent == null) {
			return null;
		}
		try {
//			final ObjectMapper mapper = new ObjectMapper();
			return objectMapper.readValue(cellContent.getBytes("UTF-8"), returnedClass());
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
		}
	}

	 @Override
	    public void nullSafeSet(final PreparedStatement ps, final Object value, final int idx,
	                            final SessionImplementor session) throws HibernateException, SQLException {
	        if (value == null) {
	            ps.setNull(idx, Types.OTHER);
	            return;
	        }
	        try {
//	            final ObjectMapper mapper = new ObjectMapper();
	            final StringWriter w = new StringWriter();
	            objectMapper.writeValue(w, value);
	            w.flush();
	            ps.setObject(idx, w.toString(), Types.OTHER);
	        } catch (final Exception ex) {
	            throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
	        }
	    }
	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		try {
			// use serialization to create a deep copy
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(value);
			oos.flush();
			oos.close();
			bos.close();

			ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
			return new ObjectInputStream(bais).readObject();
		} catch (ClassNotFoundException | IOException ex) {
			throw new HibernateException(ex);
		}
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) this.deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return this.deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return this.deepCopy(original);
	}*/

}
