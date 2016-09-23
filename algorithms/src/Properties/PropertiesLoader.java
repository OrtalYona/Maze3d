package Properties;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * read and write properties
 * 
 * @author
 *
 */
public class PropertiesLoader {

	private static Properties properties;

	public static Properties getInstance() throws Exception {
		if (properties == null)
			properties = read("newPro.xml");
		return properties;
	}

	public static void write(Properties pro, String fileName) throws Exception {

		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
		encoder.writeObject(pro);
		encoder.flush();
		encoder.close();
	}

	public static Properties read(String fileName) throws Exception {

		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
		Properties o = (Properties) decoder.readObject();
		decoder.close();
		return o;
	}
}