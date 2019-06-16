
package client.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.App;
import client.connection.Connector;

public class ChatPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private boolean listLoaded = false;
	private final int maxFr = 50;
	private final int maxGr = 50;
	
	private JPanel friendPane, groupPane;
	private ChatLabel[] friend = new ChatLabel[maxFr];
	private ChatLabel[] group  = new ChatLabel[maxGr];
	private short frNum = 0;
	private short grNum = 0;
	private final String frName = "Friends";
	private final String grName = "Group";
	private JPanel center;//needs to be global for cardLayout
	
	private static boolean testing = false;
	private byte frlgCount = 0;
	
	public ChatPanel () {
		setPreferredSize(new Dimension(250,450));
		setMinimumSize(new Dimension(250,450));
		
		/* CENTER */
		friendPane = new JPanel();
		groupPane = new JPanel();
		
		friendPane.setLayout(new GridLayout(50,1));
		 groupPane.setLayout(new GridLayout(50,1));
		
		for (int i=0;i<50;i++) {
			friend[i] = new ChatLabel("");
			group [i] = new ChatLabel("");
			
			if (testing) {
				if (i<1) {
					friend[i].setText("friend ["+i+"]");
					group[i].setText("group  ["+i+"]");
				}
			}
			
			friendPane.add(friend[i]);
			 groupPane.add(group[i]);
		}
		JScrollPane scrollPaneFr = new JScrollPane(friendPane);
		JScrollPane scrollPaneGr = new JScrollPane(groupPane);
		
		center = new JPanel();
		center.setLayout(new CardLayout());
		center.add(scrollPaneFr,frName);
		center.add(scrollPaneGr,grName);
		
		/* SOUTH */
		Chat_SouthernPanel south = new Chat_SouthernPanel(this);
		/* NORTH  */
		JButton showFriend = new JButton("Friends");
		JButton showGroup  = new JButton("Group");
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1,2));
		north.add(showFriend);
		north.add(showGroup);
		
		showFriend.setFocusable(false);
		showGroup .setFocusable(false);
		
		showFriend.addActionListener(new actLis());
		showGroup .addActionListener(new actLis());
		
		/* MAIN */
		setLayout(new BorderLayout());
		add(north,"North");
		add(south,"South");
		add(center,"Center");
	}

	public void reformat () {
		String[] tempFr = new String[frNum];
		String[] tempGr = new String[grNum];
		
		short i1 = 0;
		short i2 = 0;
		
		for (int i=0;i<frNum;i++) {
			tempFr[i] = "";
			if (!friend[i].getText().trim().equals("")) {
				tempFr[i1] = friend[i].getText();
				i1++;
			}	
		}
		for (int i=0;i<grNum;i++) {
			tempGr[i] = "";
			if (!group[i].getText().trim().equals("")) {
				tempGr[i2] = group[i].getText();
				i2++;
			}	
		}
		
		for (short i=0;i<frNum;i++) {
			friend[i].setText(tempFr[i]);
			group [i].setText(tempGr[i]);
		}
		repaint();
	}
	public void repaint () {
		if (listLoaded == false) {
			if (!testing)
				if (frlgCount == 0) {
					frlgCount++;
				} else {
					Connector.Send("frlg~#");
				}
			listLoaded = true;
		}
		super.repaint();
	}
	
	public void setFriendData (String[] data) {
		frNum = 0;
		for (int i=0;i<maxFr;i++) //first clear all
			friend[i].setText("");
		if (data[1].endsWith("nfr"))//no friends
			data[1] = "";
		
		for (int i=0;i<(data.length)/2;i++) {
			friend[i].setText(data[2*i]);
			friend[i].setActive(data[2*i+1]);
			
			if (!data[i].trim().equals("")) {
				frNum++;
			}
		}
		repaint();
	}
	
	public void removeFriend (String name) {
		System.out.println("ChatPanel : removing friend ...");
		
		//first check, if that user is a friend - if not just return
		short index = 0;
		for (short i=0;i<frNum;i++) {
			System.out.println("ChatPanel.removeFriend :: ("+i+") "+friend[i].getName());
			System.out.println("ChatPanel.removeFriend :: ("+i+") "+friend[i]);
			if (friend[i].getText().equals(name))
				index = i;
		}
		
		System.out.println("ChatPanel.removeFriend :: index = "+index);
		
		if (index == 0)
			return;
		
		friend[index].setText("");
		reformat();
		
		if (!testing)
			Connector.Send("frd~"+name+"#");
	}
	public void addFriend (String name) {
		for (short i=0;i<frNum;i++) {
			if (name.trim().equals(friend[i].getText().trim())) {
				App.addInfo(name+" is already on your friends list.");
				return;
			}
		}		
		
		
		if (!testing)
			Connector.Send("fra~"+name+"#");
		
		friend[frNum].setText(name);
		frNum++;
	}
	
	class actLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("friends"))
				showFriends();
			if (e.getActionCommand().equalsIgnoreCase("group"))
				showGroup();
		}
	}
	
	public void showFriends () {
		((CardLayout) center.getLayout()).show(center, frName);
	}
	public void showGroup () {
		((CardLayout) center.getLayout()).show(center, grName);
	}
	

	/* for test purposes : */

	public static void main (String[] args) {
		javax.swing.JFrame f = new javax.swing.JFrame();
		f.setLayout(new java.awt.BorderLayout());
		
		testing = true;
		f.add(new ChatPanel());
		
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	
}
