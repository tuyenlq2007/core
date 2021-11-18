package org.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.configuration.ContextualProperties;
import org.configuration.SetUp;
import org.exception.StopTest;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.selenium.session.DriverPool;

//import utils.Wait.StopCondition;

public abstract class WebApi {
	private static final long DEFAULT_INTERVAL = 200L;
	protected static final int ARG_SCRIPT = 0;
	protected static final int ARG_WINDOW_ID = 0;
	protected static final int ARG_TIMEOUT = 1;
	protected static final int TIMEOUT = 3000;
	protected final ContextualProperties m_pro;
	protected final WebDriver driver;
	protected final String object_repository;
	protected final int iSpeed;

	public WebApi(String object_repository) {
		this.driver = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver());
		this.object_repository = object_repository;
		this.m_pro = new ContextualProperties();
		getProperties(SetUp.getSetup().getPropertyFile());
		this.iSpeed = SetUp.getSetup().getSpeed();
	}

	public WebApi() {
		this.driver = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver());
		this.object_repository = "";
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
		case "css":
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

	protected WebElement validateElement(String object_repository2) throws StopTest {
		String type = object_repository2.split("=")[0];
		String what = object_repository2.split("=")[1];
		return driver.findElement(createElementLocator(type, what));
	}
	
	protected String returnXpath(){
		String[] locators = m_pro.getProperty(object_repository).split(";");
		return locators[0].substring(locators[0].lastIndexOf("=>") + 2);
	}
	protected WebElement validateElement() throws StopTest {
		String type = object_repository.split("=")[0];
		String what = object_repository.split("=")[1];
		return driver.findElement(createElementLocator(type, what));
	}

	public WebElement findElement(String locator) throws StopTest {
		try {
			Thread.sleep(this.iSpeed);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver.findElement(createElementLocator(locator.substring(0, locator.indexOf("=>")),
				locator.substring(locator.lastIndexOf("=>") + 2)));

	}

	public String execute() throws StopTest {
		return "SUCCESS";
	}

	protected String takeScreenShot() {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
		String filepath = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator + "history" + File.separator + fileName;
		//String filepath = "C:\\Git\\integral_qc\\FitNesseRoot\\files\\history\\" + fileName;
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "files/history/" + fileName;
		
	}
	
}