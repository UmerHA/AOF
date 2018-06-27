package VMB_Version2;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class TreePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private TreePanelBase base;
	private TreePanelField field;
	private TreePanelNPC npc;
	
	private PlainTreePanel actPanel;
	
	private final String baseName = "Base";
	private final String fieldName = "Field";
	private final String npcName = "NPC";
	
	public TreePanel () {
		base = new TreePanelBase();
		field = new TreePanelField();
		npc = new TreePanelNPC();
		
		setLayout(new CardLayout());
		add(base,baseName);
		add(field,fieldName);
		add(npc,npcName);
	}
	
	public void showBase () {
		((CardLayout)getLayout()).show(this, baseName);
		actPanel = base;
	}
	public void showField () {
		((CardLayout)getLayout()).show(this, fieldName);
		actPanel = field;
	}
	public void showNPC () {
		((CardLayout)getLayout()).show(this, npcName);
		actPanel = npc;
	}

	
	public TreePanelBase treeBase () {
		return this.base;
	}
	public TreePanelField treeField () {
		return this.field;
	}
	public TreePanelNPC treeNPC () {
		return this.npc;
	}

	public void deselectAll () {
		base.deselectAll();
		field.deselectAll();
		npc.deselectAll();
	}
	public void select (String name) {
		actPanel.select(name);
	}
}
