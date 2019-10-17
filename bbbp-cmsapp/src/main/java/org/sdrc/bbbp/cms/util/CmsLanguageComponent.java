package org.sdrc.bbbp.cms.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 30-Nov-2018 11:48:20 AM
 */
@Component
public class CmsLanguageComponent {

	//get all getter methods of CmsDataJson
	public static Map<String, Object> beanProperties(Object bean) {
	    try {
	        Map<String, Object> map = new HashMap<>();
	        Arrays.asList(Introspector.getBeanInfo(bean.getClass(), Object.class)
	                                  .getPropertyDescriptors())
	              .stream()
	              // filter out properties with setters only
	              .filter(pd -> Objects.nonNull(pd.getReadMethod()))
	              .forEach(pd -> { // invoke method to get value
	                  try {
	                      Object value = pd.getReadMethod().invoke(bean);
	                      if (value != null) {
	                          map.put(pd.getName(), value);
	                      }
	                  } catch (Exception e) {
	                      // add proper error handling here
	                  }
	              });
	        return map;
	    } catch (IntrospectionException e) {
	        // and here too
	        return Collections.emptyMap();
	    }
	}
}
