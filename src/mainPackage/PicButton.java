package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class PicButton extends JPanel {
	private static final long serialVersionUID = 1L;

	private static String directory = "";

	private Image outPic;
	private Image inPic;
	private Image inClickPic;
	private Image forePic; // foreground Image

	private Image actPic; // active Image
	private boolean mouseInPanel;
	private mLis mlis;

	private ActionListener actLis;
	private String acmd;

	private String text = "";
	private int txtX = 0;
	private int txtY = 0;
	private Color txtColor = new Color(70, 70, 70);

	private String fontType = "SansSerif";
	private int fontStyle = Font.PLAIN;
	private int fontSize = 15;

	private Font font;

	// <-- Contructors :
	public PicButton() {
		super();
		this.contructor();
	}

	public PicButton(Image pic1, Image pic2) {
		super();
		this.inPic = pic1;
		this.inClickPic = pic2;
		this.contructor();
	}

	public PicButton(String str1, String str2) {
		super();
		str1 = directory + str1;
		str2 = directory + str2;
		this.inPic = Toolkit.getDefaultToolkit().getImage(str1);
		this.inClickPic = Toolkit.getDefaultToolkit().getImage(str2);
		this.contructor();
	}

	public PicButton(Image pic1, Image pic2, Image pic3) {
		super();
		this.inPic = pic1;
		this.inClickPic = pic2;
		this.forePic = pic3;
		this.contructor();
	}

	public PicButton(String str1, String str2, String str3) {
		super();
		str1 = directory + str1;
		str2 = directory + str2;
		str3 = directory + str3;
		this.inPic = Toolkit.getDefaultToolkit().getImage(str1);
		this.inClickPic = Toolkit.getDefaultToolkit().getImage(str2);
		this.forePic = Toolkit.getDefaultToolkit().getImage(str3);
		this.contructor();
	}

	private void contructor() {
		this.outPic = this.inPic;
		this.actPic = this.inPic;

		mlis = new mLis();
		addMouseListener(mlis);
		this.font = new Font(this.fontType, this.fontStyle, this.fontSize);
	}

	// --> Constructor : End

	// <-- Image methods :
	public void setInClickPic(Image pic) {
		this.inClickPic = pic;
	}

	public void setInClickPic(String str) {
		str = directory + str;
		this.inClickPic = Toolkit.getDefaultToolkit().getImage(str);
	}

	public void setInPic(Image pic) {
		this.inPic = pic;
	}

	public void setInPic(String str) {
		str = directory + str;
		this.inPic = Toolkit.getDefaultToolkit().getImage(str);
	}

	public void setOutPic(Image pic) {
		this.outPic = pic;
	}

	public void setOutPic(String str) {
		str = directory + str;
		this.outPic = Toolkit.getDefaultToolkit().getImage(str);
	}

	public Image getInClickPic() {
		return this.inClickPic;
	}

	public Image getInPic() {
		return this.inPic;
	}

	public Image getOutPic() {
		return this.outPic;
	}

	public Image getForePic() {
		return this.forePic;
	}

	// --> Ism : End

	// --> Directory methods :
	public static void setDirectory(String dir) {
		directory = dir;
	}

	public static String getDirectory() {
		return directory;
	}

	// <--

	// <-- Font methods :
	public void setFont(Font font) {
		this.font = font;
		//repaint();
	}

	public boolean setFontType(String str) {
		boolean strIsValid = false;

		if (str.equals("Dialog"))
			strIsValid = true;
		if (str.equals("DialogInput"))
			strIsValid = true;
		if (str.equals("Monospaced"))
			strIsValid = true;
		if (str.equals("Serif"))
			strIsValid = true;
		if (str.equals("SansSerif"))
			strIsValid = true;

		if (!strIsValid)
			return false;

		this.fontType = str;
		refreshFont();
		//repaint();
		return true;
	}

	public boolean setFontStyle(int type) {
		boolean typeIsValid = false;
		switch (type) {
		case Font.PLAIN:
			typeIsValid = true;
			break;
		case Font.BOLD:
			typeIsValid = true;
			break;
		case Font.ITALIC:
			typeIsValid = true;
			break;
		}

		if (!typeIsValid)
			return false;

		this.fontStyle = type;
		refreshFont();
		//repaint();
		return true;
	}

	public void setFontSize(int size) {
		this.fontSize = size;
		refreshFont();
		//repaint();
	}

	public String getFontType() {
		return this.fontType;
	}

	public int getFontStyle() {
		return this.fontStyle;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void refreshFont() {
		this.font = new Font(this.fontType, this.fontStyle, this.fontSize);
	}

	// --> Font methods : End

	// <-- Text methods
	public void setText(String txt, int x, int y) {
		this.text = txt;
		this.txtX = x;
		this.txtY = y;
		//repaint();
	}

	public void setText(String txt) {
		this.text = txt;
		//repaint();
	}

	public void setTextPosition(int x, int y) {
		this.txtX = x;
		this.txtY = y;
		//repaint();
	}

	public void setTextPosition(Point p) {
		this.txtX = p.x;
		this.txtY = p.y;
		//repaint();
	}

	public void setTextColor(Color col) {
		this.txtColor = col;
	}

	public String getText() {
		return this.text;
	}

	public Point getTextLocation() {
		return new Point(this.txtX, this.txtY);
	}

	public Color getTextColor() {
		return this.txtColor;
	}

	// --> Text methods : End

	public void paint(Graphics g) {
		g.drawImage(actPic, 0, 0, this);
		g.drawImage(forePic, 0, 0, this);

		Color col = g.getColor(); // ask current color
		g.setColor(txtColor); // temporaly set color to txtColor
		g.setFont(font);
		g.drawString(text, txtX, txtY); // draw String
		g.setColor(col); // reset color to original color
	}

	// <-- This methods and classes give this panel it's functionality :
	public void setActionCommand(String str) {
		this.acmd = str;
	}

	class mLis extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			actPic = inClickPic;
			mouseInPanel = true;
			//repaint();
		}

		public void mouseReleased(MouseEvent e) {
			actPic = inPic;
			//repaint();
			if ((actLis != null) && (mouseInPanel))
				actLis.actionPerformed(new ActionEvent(new Object(), 1, acmd)); // <-- Ich hab kA wieso 1 ...
		}

		public void mouseEntered(MouseEvent e) {
			actPic = inPic;
			//repaint();
		}

		public void mouseExited(MouseEvent e) {
			actPic = outPic;
			mouseInPanel = false;
			//repaint();
		}

	}

	public void addActionListener(ActionListener actLis) {
		this.actLis = actLis;
	}

	public void removeActionListener() {
		this.actLis = null;
	}
	// <-- End
}
