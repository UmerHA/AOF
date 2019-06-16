package client.gamePanel;

import javax.swing.JPanel;

import client.App;
import client.ExternalPlayer;
import client.Map;
import client.connection.Connector;
import client.map.MapObject;
import client.npc.NPC;

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
		App.map = new Map();
		setFocusable(true);
		
		setLayout(new BorderLayout());
		add(mapP,"Center");
	}
	public void setBounds () {
		setBounds(0, 0, App.WIDTH(), App.HEIGHT());
	}
	
	class MMLis extends MouseMotionAdapter {
		public void mouseMoved (MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			int mx = App.toMapCoord (x)+App.map.getMapX();
			int my = App.toMapCoord (y)+App.map.getMapY();
			

			String monster = App.map.getNameOfOwner(mx, my);
			String field = App.map.getNameOfField (mx,my);
			
			String info = (/*mx + " | " + my + "  ("+ x + " | " + y + ") :  "+*/"["+field+"]   "+monster);
			if (!monster.equals("")) {
				NPC temp = App.map.getOwner(mx, my);
				info += "  : {"+temp.getOption1()+" / "+temp.getOption2()+"}";
			}
			if (App.map.fields[mx][my].isObject()) {
				MapObject temp = (MapObject) App.map.fields[mx][my];
				info += " {"+temp.getOption1()+" / examine}";
			}
			
			//if there's an external player, show his info instead of the field/npc-info
			String temp = ExternalPlayer.getPlayerNameByPos(mx, my, App.map.getMapLV());
			if (temp != null)
				info = temp + " {whisper / trade}";
			
			App.setFlyingInfo(info);
			
		}
	}
	class MLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {			
			mapP.grabFocus();			

			int x = e.getX();
			int y = e.getY();
			
			int mx = App.toMapCoord (x)+App.map.getMapX();;
			int my = App.toMapCoord (y)+App.map.getMapY();;
			
			//if there's an external player, perform his action instead of the mapObject/NPC-action
			String tempExPlayer = ExternalPlayer.getPlayerNameByPos(mx, my, App.map.getMapLV());
			if (tempExPlayer != null) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					//whisper
					App.getGamePanel().chatbox().input().setText("/w "+tempExPlayer+" ");
					App.getGamePanel().chatbox().input().grabFocus();
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					//trade
					App.getGamePanel().invitScreen().setName(tempExPlayer,false);
					Connector.Send("trinv~"+tempExPlayer+"#");
				}
				return;
			}
			
			if (App.map.fields[mx][my].isObject()) {
				MapObject temp = (MapObject) App.map.fields[mx][my];
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					temp.examine();
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					int px = App.actPlayer.getMapX();
					int py = App.actPlayer.getMapY();
				
					if ((Math.abs(px-mx)<2)&&(Math.abs(py-my)<2)) {
						temp.use();
					} else {
						App.addInfo("I can't reach it, it's too far way.");
					}
				}
			}
			
			
			
			NPC npc = App.map.getOwner(mx, my);
			if (npc == null)
				return;
			
			if (e.getButton() == MouseEvent.BUTTON1) {
				//System.out.println("FieldPanel$MLis.mouseClicked : Left mouse button clicked");
				int px = App.actPlayer.getMapX();
				int py = App.actPlayer.getMapY();
				int nx = npc.getMapX();
				int ny = npc.getMapY();
				
				if ((Math.abs(px-nx)<2)&&(Math.abs(py-ny)<2)) {
					npc.use();
				} else {
					App.addInfo("I can't reach it, it's too far way.");
				}
				
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				App.addInfo(npc.getExamineText());
				//System.out.println("FieldPanel$MLis.mouseClicked : Right mouse button clicked");
			} 
			
			

		}
		public void mouseExited (MouseEvent e) {
			App.setFlyingInfo("");
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
			case 37:App.actPlayer.move((short) 1);break;//left
			case 87://w
			case 38:App.actPlayer.move((short) 2);break;//up
			case 68://d
			case 39:App.actPlayer.move((short) 3);break;//right
			case 83://s
			case 40:App.actPlayer.move((short) 4);break;//down
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
				App.getGamePanel().menuPanel().setActivePanel((byte) 0);
			if (e.getKeyCode() == KeyEvent.VK_F2)
				App.getGamePanel().menuPanel().setActivePanel((byte) 1);
			if (e.getKeyCode() == KeyEvent.VK_F3)
				App.getGamePanel().menuPanel().setActivePanel((byte) 2);
			if (e.getKeyCode() == KeyEvent.VK_F4)
				App.getGamePanel().menuPanel().setActivePanel((byte) 3);
			if (e.getKeyCode() == KeyEvent.VK_F5)
				App.getGamePanel().menuPanel().setActivePanel((byte) 4);

			
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
			App.map.drawMap(0, 0, g);
			App.actPlayer.paint(g);
		}
	}
}
