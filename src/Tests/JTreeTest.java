package Tests;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class JTreeTest {
	private JTree tree;
	
	public JTreeTest () {
	    DefaultMutableTreeNode top =
	        new DefaultMutableTreeNode("Map Fields");
	    createNodes(top);
		
		tree = new JTree(top);
		JScrollPane treeView = new JScrollPane(tree);
		
		tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.getSelectionModel().addTreeSelectionListener(new tLis());
		
		JFrame f = new JFrame();
		f.add(treeView);
		f.setSize(500, 500);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createNodes(DefaultMutableTreeNode top) {
	    DefaultMutableTreeNode base = null;
	    DefaultMutableTreeNode obj = null;
	    DefaultMutableTreeNode obj2 = null;
	    
	    base = new DefaultMutableTreeNode("Map Bases");
	    top.add(base);
	    
	    obj = new DefaultMutableTreeNode("Plain");
	    base.add(obj);
	    obj = new DefaultMutableTreeNode("Basic");
	    base.add(obj);
	    obj = new DefaultMutableTreeNode("Road");
	    base.add(obj);
	    obj = new DefaultMutableTreeNode("Other");
	    base.add(obj);
	    
	    base = new DefaultMutableTreeNode("Objects");
	    top.add(base);
	    
	    obj = new DefaultMutableTreeNode("Rock");
	    base.add(obj);
	    obj = new DefaultMutableTreeNode("Tree");
	    base.add(obj);
	    obj = new DefaultMutableTreeNode("Tropical Tree");
	    base.add(obj);
	    obj = new DefaultMutableTreeNode("Chest");
	    base.add(obj);

	    base = new DefaultMutableTreeNode("1");
	    top.add(base);
	    obj = new DefaultMutableTreeNode("2");
	    obj2 = new DefaultMutableTreeNode("3");
	    obj.add(obj2);
	    base.add(obj);
	}
	
	public static void main (String[] args) {
		new JTreeTest();
	}

	class tLis implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent arg0) {
			System.out.println("You're now choosing : "+tree.getSelectionPath().getLastPathComponent());
		}
	}
}
