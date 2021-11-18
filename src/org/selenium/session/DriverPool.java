package org.selenium.session;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.configuration.SetUp;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

//Singleton
public enum DriverPool {
	INSTANCE;
	private ConcurrentHashMap<Integer, WebDriver> drivers;

	DriverPool() {
		drivers = new ConcurrentHashMap<Integer, WebDriver>();
	}

	// uses: WebDriver driver = DriverPool(i).INSTANCE.getDriver();
	public WebDriver getDriver(int i) {
		if (drivers.get(i) == null) {
			WebDriver newDriver = null;
			// common

			switch (SetUp.getSetup().getBrowser().toUpperCase()) {
			case "FIREFOX":
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("version", "");
				capabilities.setBrowserName("firefox");
				capabilities.setCapability("platform", Platform.WIN10);
				try {
					newDriver = new RemoteWebDriver(new URL(SetUp.getSetup().getRemoteSlaveNode()), capabilities);
					newDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					newDriver.manage().window().maximize();

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "CHROME":
				System.setProperty("webdriver.chrome.driver", "C:\\Fitnesse\\chromedriver.exe");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				try {
					newDriver = new RemoteWebDriver(new URL(SetUp.getSetup().getRemoteSlaveNode()), cap);
					newDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					newDriver.manage().window().maximize();

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "IE":

				DesiredCapabilities caie = DesiredCapabilities.internetExplorer();
				caie.setCapability("InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION", true);
				caie.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				caie.setCapability("ignoreZoomSetting", true);
				caie.setCapability("ignoreProtectedModeSettings", true);
				// caie.setCapability("initialBrowserUrl",
				// "http://10.200.110.53:8101/LifeAsiaWeb/FirstServlet?HTTP_OAM_USERID=CSCUSER1");
				caie.setCapability("initialBrowserUrl", SetUp.getSetup().getInitialBrowserUrl());
				caie.setCapability("requireWindowFocus", "true");
				caie.setCapability("nativeEvents", true);
				caie.setCapability("browserFocus", true);
				//System.setProperty("webdriver.ie.driver", "C:\\ITS\\selenium\\IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
				// newDriver = new InternetExplorerDriver(caie);
				// InternetExplorerDriver driver = new
				// InternetExplorerDriver(caie);
				/*
				 * DesiredCapabilities caie =
				 * DesiredCapabilities.internetExplorer(); caie.setCapability(
				 * "InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION", true);
				 * caie.setCapability(InternetExplorerDriver.
				 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				 * caie.setCapability("ignoreZoomSetting", true);
				 * caie.setCapability("ignoreProtectedModeSettings", true);
				 * caie.setCapability("initialBrowserUrl",
				 * "http://10.200.110.53:8101/LifeAsiaWeb/FirstServlet?HTTP_OAM_USERID=CSCUSER1"
				 * );
				 * 
				 * System.setProperty("webdriver.ie.driver",
				 * "C:\\ITS\\selenium\\IEDriverServer.exe");
				 */
				try {
					newDriver = new RemoteWebDriver(new URL(SetUp.getSetup().getRemoteSlaveNode()), caie);
					newDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					// String currentWindow = newDriver.getWindowHandle();
					// newDriver.switchTo().window(newDriver.getWindowHandle());
					newDriver.manage().window().maximize();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				break;

			case "ANDROID_CHROME":
				/*
				 * DesiredCapabilities capab = new DesiredCapabilities();
				 * capab.setCapability(MobileCapabilityType.PLATFORM_NAME,
				 * "Android");
				 * capab.setCapability(MobileCapabilityType.PLATFORM_VERSION,
				 * "4.4"); capab.setCapability(MobileCapabilityType.DEVICE_NAME,
				 * "Android Emulator");
				 * capab.setCapability(MobileCapabilityType.BROWSER_NAME,
				 * "Chrome");
				 */
				DesiredCapabilities capi = new DesiredCapabilities();
				capi.setCapability("browserName", "chrome");
				capi.setCapability("appium-version", "1.6.5");
				capi.setCapability("VERSION", "7.0");
				capi.setCapability("deviceName", "ANDROID");
				capi.setCapability("platformName", "ANDROID");
				try {
					newDriver = new RemoteWebDriver(new URL(SetUp.getSetup().getRemoteSlaveNode()), capi);
					newDriver.manage().window().maximize();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "IOS_SAFARI":
				DesiredCapabilities capi_ios = new DesiredCapabilities();
				capi_ios.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
				capi_ios.setCapability("browserName", "Safari");
				// cap.setCapability("appium-version", "1.6.5");//1.6.5
				// capi_ios.setCapability("platformVersion", "10.2");
				// capi_ios.setCapability("udid",
				// "fd512be00c295285696b982540939bad10597c34");//
				// ee6a0749bbce351db02d5595f8ada55ed0a60dd8
				// capi_ios.setCapability("deviceName", "iPad");// luu's iPhone
				capi_ios.setCapability("udid", "ee6a0749bbce351db02d5595f8ada55ed0a60dd8");//
				capi_ios.setCapability("deviceName", "luu's iPhone");//
				capi_ios.setCapability("platformName", "iOS");
				capi_ios.setCapability("platformVersion", "9.3");
				try {
					newDriver = new RemoteWebDriver(new URL(SetUp.getSetup().getRemoteSlaveNode()), capi_ios);
					newDriver.manage().window().maximize();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "IOS":
				DesiredCapabilities cap1 = new DesiredCapabilities();
				cap1.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
				// cap.setCapability("platformVersion","11.0");
				cap1.setCapability("platformVersion", "11.1.2");
				// cap.setCapability("udid","fd512be00c295285696b982540939bad10597c34");
				// // ipad 10
				// cap.setCapability("udid",
				// "F10B07A5-B0C2-40AA-B680-ACD3147FA4F5"); //
				// ipad 11
				cap1.setCapability("udid", "db76172f264137e15fcad92d844f466be8ac6776"); // iPad																			
				cap1.setCapability("deviceName", "iPad Air 2");
				cap1.setCapability("platformName", "iOS");
				cap1.setCapability("browserName", "Safari");
				cap1.setCapability("safariInitialUrl",
						"https://uat-esapphire.aviva.com.sg/afa-ba-latest/ife-web-base-agent/login");
				cap1.setCapability("bootstrapPath",
						"/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent");
				cap1.setCapability("agentPath",
						"/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/WebDriverAgent.xcodeproj");
				cap1.setCapability("xcodeOrgId", "J69WASBDSB");
				cap1.setCapability("xcodeSigningId", "iPhone Developer");
				cap1.setCapability("autoGrantPermissions", "true");
				cap1.setCapability("autoAcceptAlerts", "true");
				cap1.setCapability(MobileCapabilityType.NO_RESET, true);
				try {
					newDriver = new IOSDriver(new URL(SetUp.getSetup().getRemoteSlaveNode()), cap1); // 20.203.185.9;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
					
			case "APPIUM_ANDROID":
				// Set the Desired Capabilities
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("deviceName", "My Phone");
				//caps.setCapability("udid", "9b1668597d94"); // Give Device ID of your mobile phone
				caps.setCapability("udid", SetUp.getSetup().getUdid());
				caps.setCapability("platformName", "Android");
				//caps.setCapability("platformVersion", "8.1.0");
				caps.setCapability("platformVersion", SetUp.getSetup().getPlatformVersion());
				//caps.setCapability("appPackage", "com.android.vending");
				caps.setCapability("appPackage", SetUp.getSetup().getAppPackage());
				//caps.setCapability("appActivity", "com.android.vending.AssetBrowserActivity");
				caps.setCapability("appActivity", SetUp.getSetup().getAppActivity());
				caps.setCapability("noReset", "true");

				// Instantiate Appium Driver
				try {
					newDriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

				} catch (MalformedURLException e) {
					System.out.println(e.getMessage());
				}

				break;
			}

			drivers.put(i, newDriver);
		}
		return drivers.get(i);
	}

	public void switch_frame(int i, String framename) {
		drivers.get(i).switchTo().frame(framename);
	}

	public void switch_parentframe(int i) {
		drivers.get(i).switchTo().parentFrame();
	}

	public void quit(int i) {
		drivers.get(i).quit();
		drivers.remove(i);
	}
}
