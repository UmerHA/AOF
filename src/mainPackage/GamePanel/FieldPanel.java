package mainPackage.GamePanel;

import javax.swing.JPanel;

import connection.Connector;
import mainPackage.ExternalPlayer;
import mainPackage.MainApplet;
import mainPackage.Map;
import map.MapObject;
import npc.NPC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class FieldPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private mapPanel mapP;
	
	public FieldPanel () {
		super();

		mapP = new mapPanel();
		
		setBackground (Color.WHITE);
		MainApplet.map = new Map();
		setFocusable(true);
		
		setLayout(new BorderLayout());
		add(mapP,"Center");
	}
	public void setBounds () {
		setBounds(0, 0, MainApplet.WIDTH(), MainApplet.HEIGHT());
	}
	
	class MMLis extends MouseMotionAdapter {
		public void mouseMoved (MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			int mx = MainApplet.toMapCoord (x)+MainApplet.map.getMapX();
			int my = MainApplet.toMapCoord (y)+MainApplet.map.getMapY();
			

			String monster = MainApplet.map.getNameOfOwner(mx, my);
			String field = MainApplet.map.getNameOfField (mx,my);
			
			String info = (/*mx + " | " + my + "  ("+ x + " | " + y + ") :  "+*/"["+field+"]   "+monster);
			if (!monster.equals("")) {
				NPC temp = MainApplet.map.getOwner(mx, my);
				info += "  : {"+temp.getOption1()+" / "+temp.getOption2()+"}";
			}
			if (MainApplet.map.Fields[mx][my].isObject()) {
				MapObject temp = (MapObject) MainApplet.map.Fields[mx][my];
				info += " {"+temp.getOption1()+" / examine}";
			}
			
			//if there's an external player, show his info instead of the field/npc-info
			String temp = ExternalPlayer.getPlayerNameByPos(mx, my, MainApplet.map.getMapLV());
			if (temp != null)
				info = temp + " {whisper / trade}";
			
			MainApplet.setFlyingInfo(info);
			
		}
	}
	class MLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {			
			mapP.grabFocus();			

			int x = e.getX();
			int y = e.getY();
			
			int mx = MainApplet.toMapCoord (x)+MainApplet.map.getMapX();;
			int my = MainApplet.toMapCoord (y)+MainApplet.map.getMapY();;
			
			//if there's an external player, perform his action instead of the mapObject/NPC-action
			String tempExPlayer = ExternalPlayer.getPlayerNameByPos(mx, my, MainApplet.map.getMapLV());
			if (tempExPlayer != null) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					//whisper
					MainApplet.getGamePanel().chatbox().input().setText("/w "+tempExPlayer+" ");
					MainApplet.getGamePanel().chatbox().input().grabFocus();
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					//trade
					MainApplet.getGamePanel().invitScreen().setName(tempExPlayer,false);
					Connector.Send("trinv~"+tempExPlayer+"#");
				}
				return;
			}
			
			if (MainApplet.map.Fields[mx][my].isObject()) {
				MapObject temp = (MapObject) MainApplet.map.Fields[mx][my];
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					temp.examine();
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					int px = MainApplet.actPlayer.getMapX();
					int py = MainApplet.actPlayer.getMapY();
				
					if ((Math.abs(px-mx)<2)&&(Math.abs(py-my)<2)) {
						temp.use();
					} else {
						MainApplet.addInfo("I can't reach it, it's too far way.");
					}
				}
			}
			
			
			
			NPC npc = MainApplet.map.getOwner(mx, my);
			if (npc == null)
				return;
			
			if (e.getButton() == MouseEvent.BUTTON1) {
				//System.out.println("FieldPanel$MLis.mouseClicked : Left mouse button clicked");
				int px = MainApplet.actPlayer.getMapX();
				int py = MainApplet.actPlayer.getMapY();
				int nx = npc.getMapX();
				int ny = npc.getMapY();
				
				if ((Math.abs(px-nx)<2)&&(Math.abs(py-ny)<2)) {
					npc.use();
				} else {
					MainApplet.addInfo("I can't reach it, it's too far way.");
				}
				
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				MainApplet.addInfo(npc.getExamineText());
				//System.out.println("FieldPanel$MLis.mouseClicked : Right mouse button clicked");
			} 
			
			

		}
		public void mouseExited (MouseEvent e) {
			MainApplet.setFlyingInfo("");
		}
	}
	class keyLis extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			//System.out.println("FieldPanel$keyLis.keyPressed :: "+e.getKeyCode());
			
			/*
			 * currnely no minimap
			int x = 0;
			int y = 0;
			 */
			//System.out.println("FieldPanel.keyLis :: keyPressed");
			
			switch (e.getKeyCode()) {
			case 65://a
			case 37:MainApplet.actPlayer.move((short) 1);break;//left
			case 87://w
			case 38:MainApplet.actPlayer.move((short) 2);break;//up
			case 68://d
			case 39:MainApplet.actPlayer.move((short) 3);break;//right
			case 83://s
			case 40:MainApplet.actPlayer.move((short) 4);break;//down
			}
			/*
			 * there's currently no minimap
			case 65:x--;break;//a
			case 87:y--;break;//w
			case 68:x++;break;//d
			case 83:y++;break;//s
			}
			*/
			if (e.getKeyCode() == KeyEvent.VK_F1)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 0);
			if (e.getKeyCode() == KeyEvent.VK_F2)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 1);
			if (e.getKeyCode() == KeyEvent.VK_F3)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 2);
			if (e.getKeyCode() == KeyEvent.VK_F4)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 3);
			if (e.getKeyCode() == KeyEvent.VK_F5)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 4);

			
			/*
			 * Due to layout changes there's currently no mini map.
			 * 
			panels[11].moveMap(x, y);
			 */
			//System.out.println(e.getKeyCode());
		}
	}

 	class mapPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public mapPanel() {
			setFocusable(true);
			addMouseMotionListener (new MMLis()); 
			addMouseListener (new MLis());
			addKeyListener (new keyLis());
		}
		public void paint (Graphics g) {
			MainApplet.map.drawMap(0, 0, g);
			MainApplet.actPlayer.paint(g);
		}
	}
}
