package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;

	public PropertiesLoader() {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("properties.xml"));
			properties = (Properties) decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public static PropertiesLoader getInstance() {
		if (instance == null)
			instance = new PropertiesLoader();
		return instance;
	}
	
	public void setPropertiesLoader(String filePath) {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(filePath));
			properties = (Properties) decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
