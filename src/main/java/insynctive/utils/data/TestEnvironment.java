package insynctive.utils.data;

import org.openqa.selenium.remote.BrowserType;

public enum TestEnvironment {

	FIREFOX("FF", "39", "Win7x64-C1", "1024x768", 
			BrowserType.FIREFOX, "39.0", "Windows 7",
			"Firefox 39 | Windows 7 64bits", true), 
	
	
	CHROME("Chrome", "44", "Win7x64-C1", "1024x768", 
			BrowserType.CHROME, "44.0", "Windows 7",
			"Chrome 44 | Windows 7 64bits", true), 
	
	
	IPAD("MblSafari", "8.0", "iPadAir-iOS8Sim", "1024x768", 
			BrowserType.IPAD, "9.2", "mac",
			"Safari 8 | iPadAir", false), 
	
	
	IPHONE("MblSafari", "8.0", "iPhone6Plus-iOS8sim", "1080x1920", 
			BrowserType.IPHONE, "9.2", "mac",
			"Safari 8 | Iphone 6 PLUS", false),
	
	
	IE_10("IE", "10", "Win7x64-C2", "1024x768", 
			BrowserType.IE, "10.0", "Windows 7",
			"Internet Explorer 10 | Windows 7 64bits", true),
	
	
	IE_11("IE", "11", "Win7x64-Base", "1024x768", 
			BrowserType.IE, "11.0", "Windows 7", 
			"Internet Explorer 11 | Windows 7 64bits", true);
	
	public final String browserCrossBrowser;
	public final String versionCrossBrowser;
	public final String osCrossBrowser;
	public final String screenSizeCrossBrowser;
	
	public final String browserSauceLabs;
	public final String versionSauceLabs;
	public final String osSauceLabs;
	
	public final String description;
	public final boolean isDesktop;
	
	private TestEnvironment(
			String browserCrossBrowser,  String versionCrossBrowser, String osCrossBrowser, String screenSizeCrossBrowser, 
			String browserSauceLabs,  String versionSauceLabs, String osSauceLabs,
			String description, boolean isDesktop) {
		
		this.browserCrossBrowser = browserCrossBrowser;
		this.versionCrossBrowser = versionCrossBrowser;
		this.osCrossBrowser = osCrossBrowser;
		this.screenSizeCrossBrowser = screenSizeCrossBrowser;
		
		this.browserSauceLabs = browserSauceLabs;
		this.versionSauceLabs = versionSauceLabs;
		this.osSauceLabs = osSauceLabs;
		
		this.description = description;
		this.isDesktop = isDesktop;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
