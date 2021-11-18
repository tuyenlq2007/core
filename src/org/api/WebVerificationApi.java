package org.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.configuration.ContextualProperties;
import org.configuration.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.selenium.session.DriverPool;

public abstract class WebVerificationApi {
	protected static final int ARG_SCRIPT = 0;
	protected static final int ARG_WINDOW_ID = 0;
	protected static final int ARG_TIMEOUT = 1;
	protected static final int TIMEOUT = 3000;
	protected final ContextualProperties m_pro;
	protected final WebDriver driver;
	protected final String object_repository;
	protected final JavascriptExecutor js;
	protected final int iSpeed;
	
	public WebVerificationApi(String object_repository) {
		this.driver = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver());
		this.js = (JavascriptExecutor) driver;
		this.object_repository = object_repository;
		this.m_pro = new ContextualProperties();
		getProperties(SetUp.getSetup().getPropertyFile());
		this.iSpeed = SetUp.getSetup().getSpeed();
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

	public By createElementLocator(final String type, final String what) {
		switch (type) {
		case "className":
			return By.className(what);
		case "cssSelector":
			return By.cssSelector(what);
		case "id":
			return By.id(what);
		case "linkText":
			return By.linkText(what);
		case "name":
			return By.name(what);
		case "partialLinkText":
			return By.partialLinkText(what);
		case "tagName":
			return By.tagName(what);
		case "xpath":
			return By.xpath(what);
		case "IdOrName":
			return new ByIdOrName(what);
		default:
			throw new IllegalArgumentException("Unsupported locator type: " + type + " with value: " + what);
		}
	}


	protected WebElement validateElement() throws Exception {
		String type = object_repository.split("=")[0];
		String what = object_repository.split("=")[1];
		return driver.findElement(createElementLocator(type, what));
	}

	public WebElement findElement(String locator) throws Exception {
		try {
			Thread.sleep(this.iSpeed);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver.findElement(createElementLocator(locator.substring(0, locator.indexOf("=>")),
				locator.substring(locator.lastIndexOf("=>") + 2)));

	}

	protected String takeScreenShot() throws Exception {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
		String filepath = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + "history" + File.separator + fileName;
		// String filepath =
		// "C:\\Git\\integral_qc\\FitNesseRoot\\files\\history\\" + fileName;
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "files/history/" + fileName;

	}

	public String execute() throws Exception {
		return "";
	}

	public List<String> executes() throws Exception {
		return null;
	}
}
