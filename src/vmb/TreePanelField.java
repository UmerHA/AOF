package vmb;


import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import client.map.MapField;
import client.map.mapFields.*;
import client.map.mapObjects.*;


public class TreePanelField extends PlainTreePanel {
	private static final long serialVersionUID = 1L;
	
	public TreePanelField () {		
	    super("Map Fields");
		tree.getSelectionModel().addTreeSelectionListener(new tLis());
	}
	
	protected void createNodes(DefaultMutableTreeNode top) {
		node = new DefaultMutableTreeNode[nodeAmount];
	    
	    node[0] = new DefaultMutableTreeNode("Fields");
	    node[1] = new DefaultMutableTreeNode("Objects");
	    
	    node[2] = new DefaultMutableTreeNode("Non-Fences");
	    node[3] = new DefaultMutableTreeNode("Fences");  
	    
	    node[4] = new DefaultMutableTreeNode("Rock");
	    node[5] = new DefaultMutableTreeNode("Tree");
	    node[6] = new DefaultMutableTreeNode("Tropical Tree");	    
	    node[7] = new DefaultMutableTreeNode("Ladder Up");
	    node[8] = new DefaultMutableTreeNode("Ladder Down");
	    
	    node[9] = new DefaultMutableTreeNode("Plain");
	    node[10] = new DefaultMutableTreeNode("Lava");
	    node[11] = new DefaultMutableTreeNode("Lava Hole");
	    node[12] = new DefaultMutableTreeNode("Water (Type 1)");
	    node[13] = new DefaultMutableTreeNode("Water (Type 2)");
	    node[14] = new DefaultMutableTreeNode("Water (Type 3)");
	    node[15] = new DefaultMutableTreeNode("Sand");
	    node[16] = new DefaultMutableTreeNode("Wood");
	    
	    node[17] = new DefaultMutableTreeNode("1-sided");
	    node[18] = new DefaultMutableTreeNode("2-sided");
	    node[19] = new DefaultMutableTreeNode("3-sided");
	    
	    node[20] = new DefaultMutableTreeNode("North");
	    node[21] = new DefaultMutableTreeNode("East");
	    node[22] = new DefaultMutableTreeNode("South");
	    node[23] = new DefaultMutableTreeNode("West");
	    
	    node[24] = new DefaultMutableTreeNode("NE");
	    node[25] = new DefaultMutableTreeNode("NW");
	    node[26] = new DefaultMutableTreeNode("SE");
	    node[27] = new DefaultMutableTreeNode("SW");
	    
	    node[28] = new DefaultMutableTreeNode("NSE");
	    node[29] = new DefaultMutableTreeNode("NSW");
	    node[30] = new DefaultMutableTreeNode("NWE");
	    node[31] = new DefaultMutableTreeNode("SWE");
	   
	    
	    for (int i=20;i<24;i++)
	    	node[17].add(node[i]);
	    for (int i=24;i<28;i++)
	    	node[18].add(node[i]);
	    for (int i=28;i<32;i++)
	    	node[19].add(node[i]);
	    
	    
	    for (int i=10;i<17;i++)
	    	node[2].add(node[i]);
	    for (int i=17;i<20;i++)
	    	node[3].add(node[i]);
	    
	    for (int i=2;i<4;i++)
	    	node[0].add(node[i]);
	    for (int i=4;i<9;i++)
	    	node[1].add(node[i]);
	    
	    top.add(node[0]);
	    top.add(node[1]);
	    top.add(node[9]);
	}

	
	class tLis implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			String name;
			try {
				name = tree.getSelectionPath().getLastPathComponent().toString();
			} catch (NullPointerException npe) {
				return;
			}
			MapField temp = getMapFieldByName (name);
			VisualMapBuilder.topMapPanel().changeActiveMapField(temp);
		}
	}

	public void select (String name) {	
		int id = getIdByName(name);
		
		TreePath path = new TreePath(node[id].getPath());		
		tree.scrollPathToVisible(path);
		tree.setSelectionPath(path);
	}

	
	public int getIdByName(String name) {
		int id = 0;
				
		if (name.equalsIgnoreCase("Rock"))
			id = 4;
		if (name.equalsIgnoreCase("Tree"))
			id = 5;
		if (name.equalsIgnoreCase("TropicalTree"))
			id = 6;
		if (name.equalsIgnoreCase("LadderUp"))
			id = 7;
		if (name.equalsIgnoreCase("LadderDown"))
			id = 8;
		
		if (name.equalsIgnoreCase("PlainField"))
			id = 9;
		if (name.equalsIgnoreCase("Lava"))
			id = 10;
		if (name.equalsIgnoreCase("LavaHole"))
			id = 11;
		if (name.equalsIgnoreCase("Water"))
			id = 12;
		if (name.equalsIgnoreCase("Water1"))
			id = 13;
		if (name.equalsIgnoreCase("Water2"))
			id = 14;
		if (name.equalsIgnoreCase("Sand"))
			id = 15;
		if (name.equalsIgnoreCase("Wood"))
			id = 16;
		
		if (name.equalsIgnoreCase("FenceNorth"))
			id = 20;
		if (name.equalsIgnoreCase("FenceEast"))
			id = 21;
		if (name.equalsIgnoreCase("FenceSouth"))
			id = 22;
		if (name.equalsIgnoreCase("FenceWest"))
			id = 23;

		
		if (name.equalsIgnoreCase("FenceNE"))
			id = 24;
		if (name.equalsIgnoreCase("FenceNW"))
			id = 25;
		if (name.equalsIgnoreCase("FenceSE"))
			id = 26;
		if (name.equalsIgnoreCase("FenceSW"))
			id = 27;
		
		if (name.equalsIgnoreCase("FenceNSE"))
			id = 28;
		if (name.equalsIgnoreCase("FenceNSW"))
			id = 29;
		if (name.equalsIgnoreCase("FenceNWE"))
			id = 30;
		if (name.equalsIgnoreCase("FenceSWE"))
			id = 31;

		
		return id;
	}

	public MapField getMapFieldByName (String name) {
		MapField mp = null;
		
		if (name.equalsIgnoreCase("Plain"))
			mp = new PlainField();
		
		if (name.equalsIgnoreCase("Rock"))
			mp = new Rock();
		if (name.equalsIgnoreCase("Tree"))
			mp = new Tree();
		if (name.equalsIgnoreCase("Tropical Tree"))
			mp = new TropicalTree();
		if (name.equalsIgnoreCase("Ladder Up"))
			mp = new LadderUp();
		if (name.equalsIgnoreCase("Ladder Down"))
			mp = new LadderDown();
		
		if (name.equalsIgnoreCase("North"))
			mp = new FenceNorth();
		if (name.equalsIgnoreCase("East"))
			mp = new FenceEast();
		if (name.equalsIgnoreCase("South"))
			mp = new FenceSouth();
		if (name.equalsIgnoreCase("West"))
			mp = new FenceWest();

		
		if (name.equalsIgnoreCase("NE"))
			mp = new FenceNE();
		if (name.equalsIgnoreCase("NW"))
			mp = new FenceNW();
		if (name.equalsIgnoreCase("SE"))
			mp = new FenceSE();
		if (name.equalsIgnoreCase("SW"))
			mp = new FenceSW();
		
		if (name.equalsIgnoreCase("NSE"))
			mp = new FenceNSE();
		if (name.equalsIgnoreCase("NSW"))
			mp = new FenceNSW();
		if (name.equalsIgnoreCase("NWE"))
			mp = new FenceNWE();
		if (name.equalsIgnoreCase("SWE"))
			mp = new FenceSWE();
		
		if (name.equalsIgnoreCase("Lava"))
			mp = new Lava();
		if (name.equalsIgnoreCase("Lava Hole"))
			mp = new LavaHole();
		if (name.equalsIgnoreCase("Water (Type 1)"))
			mp = new Water();
		if (name.equalsIgnoreCase("Water (Type 2)"))
			mp = new Water1();
		if (name.equalsIgnoreCase("Water (Type 3)"))
			mp = new Water2();
		if (name.equalsIgnoreCase("Sand"))
			mp = new Sand();
		if (name.equalsIgnoreCase("Wood"))
			mp = new Wood();
		
		return mp;
	}

}
