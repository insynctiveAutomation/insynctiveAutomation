package insynctive.utils.data;

public enum TestEnvironment {

	FIREFOX("FF", "39", "Win7x64-C1", "1024x768", "Firefox 39 | Windows 7 64bits", true), 
	CHROME("Chrome", "44", "Win7x64-C1", "1024x768", "Chrome 44 | Windows 7 64bits", true), 
	IE("IE", "11", "Win7x64-Base", "1024x768", "Internet Explorer 11 | Windows 7 64bits", true), 
	IPAD("MblSafari", "8.0", "iPadAir-iOS8Sim", "1024x768", "Safari 8 | iPadAir", false), 
	IPHONE("MblSafari", "8.0", "iPhone6Plus-iOS8sim", "1080x1920", "Safari 8 | Iphone 6 PLUS", false);
	
	public final String browser;
	public final String version;
	public final String os;
	public final String screenSize;
	public final String description;
	public final boolean isDesktop;
	
	private TestEnvironment(String browser, String version, String os, String screenSize, String description, boolean isDesktop) {
		this.browser = browser;
		this.version = version;
		this.os = os;
		this.screenSize = screenSize;
		this.description = description;
		this.isDesktop = isDesktop;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
