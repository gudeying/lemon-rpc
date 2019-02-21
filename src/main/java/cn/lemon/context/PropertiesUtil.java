package cn.lemon.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Map<String,PropertiesUtil> propertiesUtilsHolder = null;
    
    private static Map<PropertiesUtil,Properties> propertiesMap = null;

    private volatile boolean propertiesLoaded;

    private PropertiesUtil(){
    	
    }

    static{
		propertiesUtilsHolder = new HashMap<String, PropertiesUtil>();
		propertiesMap = new HashMap<PropertiesUtil, Properties>();
	}

	/**
	 * �Ƿ�������
	 */
	private boolean propertiesLoaded(){
		int retryTime = 0;
		int retryTimeout = 1000;
		int sleep = 500;
		while(!propertiesLoaded && retryTime<retryTimeout){
			try {
				Thread.sleep(sleep);
				retryTime+=sleep;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return propertiesLoaded;
	}


	/**
	 * ����Resource��ȡproperties
	 */
	public static Properties getPropertiesByResource(String propertiesPath){
		InputStream inputStream = null;
		Properties properties = null;
		try{
			inputStream = PropertiesUtil.class.getResourceAsStream(propertiesPath);
			if(inputStream!=null){
				properties = new Properties();
				properties.load(inputStream);
			}
		} catch (Exception e) {
			LOGGER.error("getInstance occur error,cause:",e);
		} finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

    /**
     * ��ȡʵ��
     */
    public static synchronized PropertiesUtil getInstance(String propertiesPath){
    	PropertiesUtil propertiesUtil = propertiesUtilsHolder.get(propertiesPath);
    	if(null==propertiesUtil){
    		LOGGER.info("[PropertiesUtil] instance is null with propertiesPath={},will create a new instance directly.",propertiesPath);
			InputStream inputStream = null;
			try{
				propertiesUtil = new PropertiesUtil();
				Properties properties = new Properties();
				inputStream = PropertiesUtil.class.getResourceAsStream(propertiesPath);
				if(inputStream!=null){
					properties.load(inputStream);
					propertiesUtilsHolder.put(propertiesPath, propertiesUtil);
					propertiesMap.put(propertiesUtil, properties);

					LOGGER.info("[PropertiesUtil] instance init success.");
					propertiesUtil.propertiesLoaded = true;
				}
			} catch (Exception e) {
				LOGGER.error("[PropertiesUtil] getInstance error,cause:{}",e.getMessage(),e);
			} finally{
				try {
					if(inputStream!=null){
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
    	return propertiesUtil;
    }
    
    /**
     * ���������Ϣ��Stringֵ
     */
    public String getString(String key){
    	if(propertiesLoaded()){
			Properties properties = propertiesMap.get(this);
			return null != properties ? properties.getProperty(key) : null;
		}
		return null;
    }
    
    /**
     * ���������Ϣ��booleanֵ
     */
    public boolean getBoolean(String key){
    	String value = getString(key);
    	return "true".equalsIgnoreCase(value);
    }
    
    /**
     * ���������Ϣ��intֵ
     */
    public int getInt(String key,int defaultValue){
    	String value = getString(key);
    	int intValue;
    	try{
    		intValue = Integer.parseInt(value);
    	}catch(Exception e){
    		intValue = defaultValue;
    	}
    	return intValue;
    }
    
    /**
     * ���������Ϣ��longֵ
     */
    public long getLong(String key,long defaultValue){
    	String value = getString(key);
    	long longValue;
    	try{
    		longValue = Long.parseLong(value);
    	}catch(Exception e){
    		longValue = defaultValue;
    	}
    	return longValue;
    }

}
