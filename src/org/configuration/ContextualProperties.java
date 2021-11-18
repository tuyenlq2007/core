/*
 * @(#)ContextualProperties.java

 */

package org.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class ContextualProperties {

	private final Properties m_mapping = new Properties();

	public String getProperty(final String propertyName) {
		String result = null;
		result = m_mapping.getProperty(propertyName);
		result = (result != null) ? result : m_mapping
				.getProperty(propertyName);
		return (StringUtils.isNotBlank(result)) ? result.trim() : result;
	}

	public void load(final InputStream mappingReader) throws IOException {
		m_mapping.load(mappingReader);
	}

	public void setProperty(final String sProperty, final String sValue) {
		// TODO Auto-generated method stub
		m_mapping.setProperty(sProperty, sValue);

	}

	public void store(final OutputStream output, final String object) {
		// TODO Auto-generated method stub
		try {
			m_mapping.store(output, object);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}