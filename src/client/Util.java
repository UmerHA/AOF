package client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Util {
	private static Logger logger = null;
	
	public static final String IMAGE_DIR = "/resources/pics/";
	public static final String DATA_DIR = "/resources/data/";
	
	public static Image getImage(String picPath) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(App.class.getResource(IMAGE_DIR + picPath));
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("Could not read image: " + IMAGE_DIR + picPath);
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static BufferedReader loadDataFileAsBufferedReader(String dataFilePath) throws IOException {
		URL fileURL = App.class.getResource(DATA_DIR + dataFilePath);
		InputStream inputStream = fileURL.openStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		return bufferedReader;
	}

	public static Logger getLogger() {
		if (logger == null) {
			logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
				
			try {
				FileHandler fileHandler = new FileHandler("log.xml", true);
				fileHandler.setLevel(Level.FINEST);
				logger.addHandler(fileHandler);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return logger;
	}

	private static final DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static Date lastDate = new Date();
	
	public static void timedLog(String message) {
		Date newDate = new Date();
		long timeDelta = newDate.getTime() - lastDate.getTime();
		
		System.out.println(fullDateFormat.format(newDate) + " [Delta: " + timeDelta + "] " + message); 
		
		lastDate = newDate;
	}
}
