package client.doodle;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.App;
import client.Map;
import client.map.MapField;
import client.map.MapObject;
import client.map.mapBases.Grass;
import client.map.mapFields.Water;
import client.map.mapObjects.Tree;

public class FileLoaderTest {

	public static void main(String[] args) {
		loadImage();
		loadFile();
		
		new FileLoaderTest();
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

	public FileLoaderTest() {
		JFrame jframe = new JFrame();
		
		jframe.setLayout(new BorderLayout());
		jframe.add(new ImageShowerPanel(), BorderLayout.CENTER);
		jframe.add(new JButton("I'm a button"), BorderLayout.SOUTH);
		
		jframe.setSize(300, 300);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class ImageShowerPanel extends JPanel {
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		
		MapField grass = new Grass();
		MapField water = new Water();
		MapObject tree = new Tree();
		
		public ImageShowerPanel() {
			try {
				img1 = ImageIO.read(App.class.getResource("/resources/pics/fields/grass.jpg"));
				img2 = ImageIO.read(App.class.getResource("/resources/pics/fields/Sand.jpg"));
				
				System.out.println(img1.getHeight() + " " + img1.getWidth());
				System.out.println(img2.getHeight() + " " + img2.getWidth());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void paint(Graphics g) {
			g.drawImage(img1, 50, 50, null);
			g.drawImage(img2, 50, 100, null);
			

			g.drawImage(grass.getImage(), 30, 200, null);
			g.drawImage(water.getImage(), 60, 200, null);
			g.drawImage(tree.getImage(), 90, 200, null);
		}
	}
}
