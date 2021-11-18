package org.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SetUp {
	private static String sPropertyFile = "";
	private static int iDriver = 0;
	private static int iSpeed = 0;
	private static String sBrowser = "";
	private static String sRemoteSlaveNode = "";
	private static String initialBrowserUrl = "";
	private static String hostName = "";
	private static String sqlInstanceName = "";
	private static String database = "";
	private static String userName = "";
	private static String password = "";
	private static String sUdid = "";
	private static String sPlatformVersion = "";
	private static String sAppPackage = "";
	private static String sAppActivity = "";
	
	private static SetUp sSetup = new SetUp();

	public static SetUp getSetup() {
		return sSetup;
	}

	public void sethostName(final String sValue) {
		hostName = sValue;
	}
	
	public String gethostName() {
		return hostName;
	}
	
	public void setsqlInstanceName(final String sValue) {
		sqlInstanceName = sValue;
	}
	
	public String getsqlInstanceName() {
		return sqlInstanceName;
	}
	
	public void setdatabase(final String sValue) {
		database = sValue;
	}
	
	public String getdatabase() {
		return database;
	}
	
	public void setuserName(final String sValue) {
		userName = sValue;
	}
	
	public String getuserName() {
		return userName;
	}
	
	public void setpassword(final String sValue) {
		password = sValue;
	}
	
	public String getpassword() {
		return password;
	}
	
	
	public void setinitialBrowserUrl(final String sValue) {
		initialBrowserUrl = sValue;
	}
	
	public String getInitialBrowserUrl() {
		return initialBrowserUrl;
	}
	
	public void setDriver(final int iValue) {
		iDriver = iValue;
	}

	public void setSpeed(final int iValue) {
		iSpeed = iValue;
	}
	
	
	public int getSpeed() {
		return iSpeed;
	}
	
	public int getDriver() {
		return iDriver;
	}
	
	//Android
	public void setUdid(final String sValue) {
		sUdid = sValue;
	}

	public String getUdid() {
		return sUdid;
	}
	
	public void setPlatformVersion(final String sValue) {
		sPlatformVersion = sValue;
	}

	public String getPlatformVersion() {
		return sPlatformVersion;
	}
	
	public void setAppPackage(final String sValue) {
		sAppPackage = sValue;
	}

	public String getAppPackage() {
		return sAppPackage;
	}
	
	public void setAppActivity(final String sValue) {
		sAppActivity = sValue;
	}

	public String getAppActivity() {
		return sAppActivity;
	}
	//End Android
	
	public void setBrowser(final String sValue) {
		sBrowser = sValue;
	}

	public String getBrowser() {
		return sBrowser;
	}
	
	public void setRemoteSlaveNode(final String sValue) {
		sRemoteSlaveNode = sValue;
	}

	public String getRemoteSlaveNode() {
		return sRemoteSlaveNode;
	}
	
	public void setPropertyFile(final String sValue) {
		sPropertyFile = sValue;
	}

	public String getPropertyFile() {
		return sPropertyFile;
	}

	public void setProperty(final String propertiesFile, final String sProperty, final String sValue) {
		// TODO Auto-generated method stub
		final String sFileLocation = System.getProperty("user.dir") + File.separator + "properties" + File.separator
				+ propertiesFile;
		final ContextualProperties prop = new ContextualProperties();
		InputStream in = null;
		OutputStream output = null;
		try {

			in = new FileInputStream(sFileLocation);
			prop.load(in);
			in.close();

			output = new FileOutputStream(sFileLocation);
			// set the properties value
			prop.setProperty(sProperty, sValue);

			// save properties to project root folder
			prop.store(output, null);
			output.close();

		} catch (final IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}

			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}