package client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;

public class FileLoaderTest {

	public static void main(String[] args) {
		loadImage();
		loadFile();
	}

	public static void loadImage() {
		String picPath = "/resources/pics/SUBackPic.png";

		BufferedImage img = null;
		try {
			img = ImageIO.read(App.class.getResource(picPath));
		} catch (IOException e) {
			System.out.println("Could not read image: " + picPath);
			e.printStackTrace();
		}
		
		System.out.println("Image Dimensions: " + img.getWidth() + ", " + img.getHeight());
	}

	public static void loadFile() {
		String filePath = "/resources/data/MonData0";

		try {
			URL fileURL = App.class.getResource(filePath);
			System.out.println(fileURL);
			
			InputStream inputStream = fileURL.openStream();
			System.out.println(inputStream);

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			System.out.println(inputStreamReader);

			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			System.out.println(bufferedReader);

			String strLine;
			// Read File Line By Line

			while ((strLine = bufferedReader.readLine()) != null) {
				if (!strLine.equals("")) {
					System.out.println(strLine);
				}
			}

			bufferedReader.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
