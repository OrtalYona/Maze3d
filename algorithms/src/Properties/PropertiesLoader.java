package Properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesLoader {
	
	
	private static PropertiesLoader instance;
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	private PropertiesLoader() 
	{
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("properties.xml"));
			properties = (Properties)decoder.readObject();
			decoder.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
		
		public static PropertiesLoader getInstance() {
			if (instance == null) 
				instance = new PropertiesLoader();
			return instance;
		}

}
