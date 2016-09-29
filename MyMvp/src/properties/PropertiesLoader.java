package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesLoader.
 */
public class PropertiesLoader {

	/** The instance. */
	private static PropertiesLoader instance;

	/** The properties. */
	private Properties properties;

	/**
	 * Instantiates a new properties loader to a properties instance
	 */
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

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Gets the single instance of PropertiesLoader using the singleton pattern
	 *
	 * @return single instance of PropertiesLoader
	 */
	public static PropertiesLoader getInstance() {
		if (instance == null)
			instance = new PropertiesLoader();
		return instance;
	}

	/**
	 * Sets the properties loader using a different properties file
	 *
	 * @param filePath
	 *            the new properties loader
	 */
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
