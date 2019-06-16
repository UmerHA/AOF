package vmb;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import client.npc.NPC;


public class TreePanelNPC extends PlainTreePanel {
	private static final long serialVersionUID = 1L;

	private JLabel lbDelay;
	private NumberOnlyTextField txtDelay;

	private boolean isNoneSelected = false;
	private docLis doclis;
	
	public TreePanelNPC () {	
		super("NPCs");
		tree.getSelectionModel().addTreeSelectionListener(new tLis());
		
		lbDelay = new JLabel(" Delay :   ");
		txtDelay = new NumberOnlyTextField();
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1,2));
		north.add(lbDelay);
		north.add(txtDelay);
		north.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		JPanel northOut = new JPanel();
		northOut.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		northOut.setLayout(new BorderLayout());
		northOut.add(north);
		
		add(northOut,"North");
		
		doclis = new docLis();
		txtDelay.getDocument().addDocumentListener(doclis);
	}
	
	protected void createNodes(DefaultMutableTreeNode top) {
		node = new DefaultMutableTreeNode[nodeAmount];
	    
	    node[0] = new DefaultMutableTreeNode("Monster");
	    node[1] = new DefaultMutableTreeNode("TNPCs");
	    
	    node[2] = new DefaultMutableTreeNode("Ankou");
	    node[3] = new DefaultMutableTreeNode("Big kA");
	    node[4] = new DefaultMutableTreeNode("Big Goblin");
	    node[5] = new DefaultMutableTreeNode("Chicken");
	    node[6] = new DefaultMutableTreeNode("Demon");
	    node[7] = new DefaultMutableTreeNode("Dog");
	    node[8] = new DefaultMutableTreeNode("Dragon");
	    node[9] = new DefaultMutableTreeNode("Dude");
	    node[10] = new DefaultMutableTreeNode("Goblin");
	    node[11] = new DefaultMutableTreeNode("Jad");
	    node[12] = new DefaultMutableTreeNode("kA");
	    node[13] = new DefaultMutableTreeNode("Mithril Dragon");
	    node[14] = new DefaultMutableTreeNode("Phoenix");
	    node[15] = new DefaultMutableTreeNode("Sheep");
	    	
	    node[16] = new DefaultMutableTreeNode("Adventurer");
	    node[17] = new DefaultMutableTreeNode("Edward");
	    node[18] = new DefaultMutableTreeNode("Farmer");
	    node[19] = new DefaultMutableTreeNode("Joe");
	   
	    node[20] = new DefaultMutableTreeNode("None");
	    
	    for (int i=2;i<16;i++)
	    	node[0].add(node[i]);
	    for (int i=16;i<20;i++)
	    	node[1].add(node[i]);
	    
	    top.add(node[0]);
	    top.add(node[1]);
	    top.add(node[20]);
	}

	
	class tLis implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			String name;
			try {
				name = tree.getSelectionPath().getLastPathComponent().toString();
			} catch (NullPointerException npe) {
				return;
			}
			
			NPC temp = getNpcByName (name);
			
			if (name.equalsIgnoreCase("None")) {
				isNoneSelected = true;
			} else {
				isNoneSelected = false;
			}
			
			VisualMapBuilder.topMapPanel().changeActiveNPC(temp);
		}
	}

	
	public void select (String name) {			
		int id = getIdByName(name);
		
		TreePath path = new TreePath(node[id].getPath());		
		tree.scrollPathToVisible(path);
		tree.setSelectionPath(path);
		
		if (id == 20) {
			isNoneSelected = true; 
		} else { 
			isNoneSelected = false;
		}
	}

	
	public int getIdByName(String name) {
		int id = 0;
		
		if (name.equalsIgnoreCase("Ankou"))
			id = 2;
		if (name.equalsIgnoreCase("Big_kA"))
			id = 3;
		if (name.equalsIgnoreCase("BigGoblin"))
			id = 4;
		if (name.equalsIgnoreCase("Chicken"))
			id = 5;
		if (name.equalsIgnoreCase("Demon"))
			id = 6;
		if (name.equalsIgnoreCase("Dog"))
			id = 7;
		if (name.equalsIgnoreCase("Dragon"))
			id = 8;
		if (name.equalsIgnoreCase("Dude"))
			id = 9;
		if (name.equalsIgnoreCase("Goblin"))
			id = 10;
		if (name.equalsIgnoreCase("Jad"))
			id = 11;
		if (name.equalsIgnoreCase("kA"))
			id = 12;
		if (name.equalsIgnoreCase("MithrilDragon"))
			id = 13;
		if (name.equalsIgnoreCase("Phoenix"))
			id = 14;
		if (name.equalsIgnoreCase("Sheep"))
			id = 15;
		
		if (name.equalsIgnoreCase("Adventurer"))
			id = 16;
		if (name.equalsIgnoreCase("Edward"))
			id = 17;
		if (name.equalsIgnoreCase("Farmer"))
			id = 18;
		if (name.equalsIgnoreCase("Joe"))
			id = 19;
		
		if (name.equalsIgnoreCase("None"))
			id = 20;
		
		return id;
	}
	public NPC getNpcByName (String name) {
		NPC npc = null;

		// x,y unt z sind unwichtig
		int x = 0;
		int y = 0;
		int z = 0;
		
		npc = NPC.createNpcByName(name, x, y, z);
				
		/*
		 * Bei  "name.equalsIgnoreCase("None")" soll npc sowieso null bleiben 
		 */
		
		return npc;
	}

	public boolean isNoneSelected() {
		return this.isNoneSelected ;
	}
	public NumberOnlyTextField getNumField () {
		return this.txtDelay;
	}

	class docLis implements DocumentListener {
		boolean changingByProg = false;
		
		public void changedUpdate(DocumentEvent e) {
			 //Plain text components do not fire these events
		}
		
		public void insertUpdate(DocumentEvent e) {
			//System.out.println("TreePanelNPC.docLis :: changingByProg = "+changingByProg);
			
			if (!changingByProg)
				VisualMapBuilder.topMapPanel().setCurrentDelay();
			changingByProg = false;
		}
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("TreePanelNPC.docLis :: changingByProg = "+changingByProg);
			
			if (!changingByProg)
				VisualMapBuilder.topMapPanel().setCurrentDelay();
			changingByProg = false;
		}

	}
	public void setChangingByProg (boolean bool) {
		doclis.changingByProg = bool;
	}
}
