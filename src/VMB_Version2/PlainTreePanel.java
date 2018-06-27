package VMB_Version2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public abstract class PlainTreePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JTree tree;
	protected DefaultMutableTreeNode[] node;
	protected final int nodeAmount = 35;
	protected JPanel buttonPanel;
	protected JButton showAll, hideAll;
	protected JPanel content;

	public PlainTreePanel (String topNodeName) {		
		// create tree and place it in a JScrollPane
	    DefaultMutableTreeNode top =
	        new DefaultMutableTreeNode(topNodeName);
	    createNodes(top);
		tree = new JTree(top);
		JScrollPane treeView = new JScrollPane(tree);
		tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		//tree.getSelectionModel().addTreeSelectionListener(new tLis());
		
		// create the southern panel containing 2 buttons
		buttonPanel = new JPanel();
		showAll = new JButton("Expand All");
		hideAll = new JButton("Collapse All");
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(showAll,"North");
		buttonPanel.add(hideAll,"South");
		
		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(treeView,"Center");
		content.add(buttonPanel,"South");
		
		setLayout(new BorderLayout());
		add(content,"Center");
		
		showAll.addActionListener(new actLis());
		hideAll.addActionListener(new actLis());
		tree.addKeyListener(VisualMapBuilder.topMapPanel().getSmallKeyLis());
	}
	
	//override :
	protected void createNodes(DefaultMutableTreeNode top) {}
	protected int getIdByName (String name) {return 0;}
	protected void select (String name) {}
	
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			String acmd = e.getActionCommand();
			if (acmd.equalsIgnoreCase("expand all"))
				expandAll();
			if (acmd.equalsIgnoreCase("collapse All"))
				collapseAll();
		}
	}
	
	//don't override
	public void collapseAll () {
	    int row = tree.getRowCount() - 1;
	    while (row >= 0) {
	      tree.collapseRow(row);
	      row--;
	    }
	 }
	public void expandAll () {
		collapseAll();
		for (int i=0;i<4;i++)
			expandLevel();
	}
	public void expandLevel () {
	    int row = tree.getRowCount() - 1;
	    while (row >= 0) {
	      tree.expandRow(row);
	      row--;
	    }
	}
	public void deselectAll() {
		tree.clearSelection();
	}
}
