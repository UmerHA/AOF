package VMB_Version2;


import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import MapBases.*;


public class TreePanelBase extends PlainTreePanel {
	private static final long serialVersionUID = 1L;
	
	public TreePanelBase () {
		// create tree and place it in a JScrollPane
	    super("Map Bases");
		tree.getSelectionModel().addTreeSelectionListener(new tLis());
	}
	
	protected void createNodes(DefaultMutableTreeNode top) {
		node = new DefaultMutableTreeNode[nodeAmount];
	    
	    node[0] = new DefaultMutableTreeNode("Grass");
	    node[1] = new DefaultMutableTreeNode("Road");
	    node[2] = new DefaultMutableTreeNode("Underground");
	    node[3] = new DefaultMutableTreeNode("Nothing");
	    
	    top.add(node[0]);
	    top.add(node[1]);
	    top.add(node[2]);
	    top.add(node[3]);
	}

	
	class tLis implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			String name;
			try {
				name = tree.getSelectionPath().getLastPathComponent().toString();
			} catch (NullPointerException npe) {
				return;
			}
			
			MapBase temp = getMapBaseByName (name);
			VisualMapBuilder.topMapPanel().changeActiveMapBase(temp);
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

		if (name.equalsIgnoreCase("Grass"))
			id = 0;
		if (name.equalsIgnoreCase("Road"))
			id = 1;
		if (name.equalsIgnoreCase("Underground"))
			id = 2;
		if (name.equalsIgnoreCase("Nothing"))
			id = 3;
		
		return id;
	}
	public MapBase getMapBaseByName (String name) {
		MapBase mb = null;
		// x und y sind unwichtig
		int x = 0;
		int y = 0;
		
		if (name.equalsIgnoreCase("Grass"))
			mb = new Grass(x,y);
		if (name.equalsIgnoreCase("Road"))
			mb = new Road(x,y);
		if (name.equalsIgnoreCase("Underground"))
			mb = new Underground(x,y);
		if (name.equalsIgnoreCase("Nothing"))
			mb = new Nothing(x,y);
		
		return mb;
	}
}