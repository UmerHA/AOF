package vmb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CoordPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int actNum = 0;
	private byte type;
	private Color backCol = new Color(72,106,242);
	private Color actCol = new Color(252,242,65);
	private Color fontCol = Color.BLACK;
	private int fieldNum;
	
	private TopMapPanel parent;
	
	//types
	public final static byte VERTICAL = 1;
	public final static byte HORIZONTAL = 2;
	
	public CoordPanel (byte type) {
		this.type = type;
		
		if (type == VERTICAL) {
			this.setPreferredSize(new Dimension(30,0));
		} 
		if (type == HORIZONTAL) {
			this.setPreferredSize(new Dimension(0,30));
		}
	}
	public void setTopMapPanel (TopMapPanel tmp) {
		this.parent = tmp;
	}
	
	public void paint (Graphics g) {
		g.setColor(backCol);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (type == VERTICAL) {
			g.setColor(actCol);
			if (parent.drawRect()) {
				int start = parent.toMapCoord(parent.tmpStrY());
				int stop = parent.toMapCoord(parent.tmpStpY());
				int l = stop-start;
				
				//System.out.println("CoordPanel.paint :: start = "+start+" ; stop = "+stop+" ; l = "+l);
				
				g.fillRect(0, (start-1)*30, getWidth(), (l+1)*30);
			} else {
				g.fillRect(0, (actNum-1)*30, getWidth(), 30);
			}
			
			g.setColor(fontCol);
			refershFieldNum();
			for (int i=0;i<fieldNum;i++) {
				int temp = (i*30)+20;
				int id = i+1+VisualMapBuilder.topMapPanel().getMapY();
				g.drawString(String.valueOf(id), 10, temp);
			}
		}
		if (type == HORIZONTAL) {
			g.setColor(actCol);
			if (parent.drawRect()) {
				int start = parent.toMapCoord(parent.tmpStrX());
				int stop = parent.toMapCoord(parent.tmpStpX());
				int l = stop-start;
				
				//System.out.println("CoordPanel.paint :: start = "+start+" ; stop = "+stop+" ; l = "+l);
				
				g.fillRect((start-1)*30, 0, (l+1)*30, getHeight());
			} else {
				g.fillRect((actNum-1)*30, 0, 30, getHeight());
			}
			
			g.setColor(fontCol);
			refershFieldNum();
			for (int i=0;i<fieldNum;i++) {
				int temp = (i*30)+10;
				int id = i+1+VisualMapBuilder.topMapPanel().getMapX();
				g.drawString(String.valueOf(id), temp, 20);
			}
		}
	}

	public void setActNum (int num) {
		this.actNum = num;
		repaint();
	}
	private void refershFieldNum () {
		if (type == HORIZONTAL)
			fieldNum = (int) Math.floor(getWidth()/30)+1;
		if (type == VERTICAL)
			fieldNum = (int) Math.floor(getHeight()/30)+1;
	}
}
