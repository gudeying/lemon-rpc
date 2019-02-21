package cn.lemon.context;

public class CommonConstants {
	public static final int SERVER_PORT = 6666;
	public static final String BASE_SCAN_PACKAGE = "cn.geeklemon";
	public static final String proxyPackage = PropertiesUtil.getInstance("/application.properties").getString("");
}
