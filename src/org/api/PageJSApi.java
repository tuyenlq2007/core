package org.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.configuration.ContextualProperties;
import org.configuration.SetUp;
import org.exception.StopTest;
import org.openqa.selenium.WebDriver;
import org.selenium.session.DriverPool;

public abstract class PageJSApi {
	protected final ContextualProperties m_pro;
	protected final WebDriver driver;
	protected final String object_repository;

	public PageJSApi(String object_repository) {
		this.driver = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver());
		this.object_repository = object_repository;
		this.m_pro = new ContextualProperties();
		getProperties(SetUp.getSetup().getPropertyFile());
	}

	protected void getProperties(final String fileName, final String... xPaths) {
		InputStream mappingReader = null;
		try {
			mappingReader = new FileInputStream(System.getProperty("user.dir") + File.separator + "properties"
					+ File.separator + fileName + ".properties");
			m_pro.load(mappingReader);
		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (mappingReader != null) {
				try {
					mappingReader.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String execute() throws StopTest {
		// TODO Auto-generated method stub
		return null;
	}

}
