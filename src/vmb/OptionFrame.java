package vmb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private showBasePanel baseP;
	private showFieldPanel fieldP;
	private showNpcPanel npcP;
	private southPanel southP;
	private buttonPanel buttonP;
	
	private final String path = VisualMapBuilder.PATH + "vmbData";
	
	//default values:
	private boolean baseShowField = false;
	private boolean baseShowNPC = false;
	private boolean fieldShowBase = true;
	private boolean fieldShowNPC = false;;
	private boolean npcShowBase = true;
	private boolean npcShowField = true;
	private boolean showSaveMsg = true;
	private boolean saveMapXY = true;
	
	private OptionFrame optFrame;
	
	public OptionFrame () {
		optFrame = this;
		
		baseP = new showBasePanel();
		fieldP = new showFieldPanel();
		npcP = new showNpcPanel();
		southP = new southPanel();
		buttonP = new buttonPanel();
		
		readDataFromFile();
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(4,1));
		content.add(baseP);
		content.add(fieldP);
		content.add(npcP);
		content.add(southP);
		
		setLayout(new BorderLayout());
		add(content,"Center");
		add(buttonP,"South");
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Visual Map Builder V2 - Options");
		setSize(310,500);
		setResizable(false);
		
		VisualMapBuilder.topMapPanel().repaint();
	}
	
	private void readDataFromFile () {
		String[] data = new String[15];
		short lines = 0;

		try {
			File file = new File(path);
			
			if (!file.exists())
				createDataFile();
			
			
			FileInputStream fileStream = new FileInputStream(file);
			DataInputStream dataStream = new DataInputStream(fileStream);
			BufferedReader fromFile = new BufferedReader(new InputStreamReader(
					dataStream));

			String strLine;
			// Read File Line By Line

			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					// System.out.println("|"+ data[lines-1] +"|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		baseShowField = parseBool(data[0]);
		baseShowNPC = parseBool(data[1]);
		fieldShowBase = parseBool(data[2]);
		fieldShowNPC = parseBool(data[3]);
		npcShowBase = parseBool(data[4]);
		npcShowField = parseBool(data[5]);
		showSaveMsg = parseBool(data[6]);
		saveMapXY = parseBool(data[7]);
		
		if (saveMapXY) {
			VisualMapBuilder.topMapPanel().setMapX(Integer.parseInt(data[8]));
			VisualMapBuilder.topMapPanel().setMapX(Integer.parseInt(data[9]));
		}
		VisualMapBuilder.topMapPanel().setLevel(Byte.parseByte(data[10]));
		VisualMapBuilder.topMapPanel().setLowestLevel(Byte.parseByte(data[11]));
		VisualMapBuilder.topMapPanel().setHighestLevel(Byte.parseByte(data[12]));
		VisualMapBuilder.topMapPanel().repaint();
		VisualMapBuilder.lvPanel().refresh();
		refreshCheckBoxes();
	}
	private boolean parseBool (String str) {
		if (str.equalsIgnoreCase("true"))
			return true;
		if (str.equalsIgnoreCase("1"))
			return true;
		
		return false;
	}
	private String unparseBool (boolean bool) {
		if (bool) {
			return String.valueOf(1);
		} else {
			return String.valueOf(0);
		}
	}
	public void saveData() {
		try {
			// delete and re-create file :
			File aFile = new File(path);
			aFile.delete();
			aFile.createNewFile();

			// write into file :

			try {

				BufferedWriter toFile = new BufferedWriter(new FileWriter(
						path));
				int mapX = VisualMapBuilder.topMapPanel().getMapX();
				int mapY = VisualMapBuilder.topMapPanel().getMapY();
				int mapLV = VisualMapBuilder.topMapPanel().getMapLV();
				byte minLV = VisualMapBuilder.topMapPanel().getLowestLevel();
				byte maxLV = VisualMapBuilder.topMapPanel().getHighestLevel();
				
				toFile.write(unparseBool(baseShowField));
				toFile.newLine();
				toFile.write(unparseBool(baseShowNPC));
				toFile.newLine();
				toFile.write(unparseBool(fieldShowBase));
				toFile.newLine();
				toFile.write(unparseBool(fieldShowNPC));
				toFile.newLine();
				toFile.write(unparseBool(npcShowBase));
				toFile.newLine();
				toFile.write(unparseBool(npcShowField));
				toFile.newLine();
				toFile.write(unparseBool(showSaveMsg));
				toFile.newLine();
				toFile.write(unparseBool(saveMapXY));
				toFile.newLine();
				toFile.write(String.valueOf(mapX));
				toFile.newLine();
				toFile.write(String.valueOf(mapY));
				toFile.newLine();
				toFile.write(String.valueOf(mapLV));
				toFile.newLine();
				toFile.write(String.valueOf(minLV));
				toFile.newLine();
				toFile.write(String.valueOf(maxLV));
				toFile.newLine();

				toFile.close();

				// MainProg.alert("Data Saved");
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void refreshCheckBoxes() {
		if (baseShowField) {
			baseP.field.setSelected(true);
		} else {
			baseP.field.setSelected(false);
		}
		//---
		if (baseShowNPC) {
			baseP.npc.setSelected(true);
		} else {
			baseP.npc.setSelected(false);
		}		
		//--->
		if (fieldShowBase) {
			fieldP.base.setSelected(true);
		} else {
			fieldP.base.setSelected(false);
		}
		//---
		if (fieldShowNPC) {
			fieldP.npc.setSelected(true);
		} else {
			fieldP.npc.setSelected(false);
		}
		//--->
		if (npcShowBase) {
			npcP.base.setSelected(true);
		} else {
			npcP.base.setSelected(false);
		}	
		//---
		if (npcShowField) {
			npcP.field.setSelected(true);
		} else {
			npcP.field.setSelected(false);
		}
		//------>
		if (saveMapXY) {
			southP.CB_saveMapXY.setSelected(true);
		} else {
			southP.CB_saveMapXY.setSelected(true);
		}	
		//---
		if (showSaveMsg) {
			southP.CB_showSaveMsg.setSelected(true);
		} else {
			southP.CB_showSaveMsg.setSelected(true);
		}
	}
	
	class showBasePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		JCheckBox base,field,npc;
		public showBasePanel () {
			base = new JCheckBox("show Base");
			field = new JCheckBox("show Fields");
			npc = new JCheckBox("show NPCs");
			
			base.setSelected(true);
			field.setSelected(false);
			npc.setSelected(false);
			base.setEnabled(false);
			
			baseShowField = false;
			baseShowNPC = false;
			
			setLayout(new GridLayout(3,1));
			add(base);
			add(field);
			add(npc);
			
			JPanel content = new JPanel();
			content.setLayout(new GridLayout(3,1));
			content.add(base);
			content.add(field);
			content.add(npc);
			content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			
			setLayout(new BorderLayout());
			add(content,"Center");
			setBorder(BorderFactory.createTitledBorder("Base tab options :"));
			
			field.addItemListener(new itemLis());
			npc.addItemListener(new itemLis());
		}
		class itemLis implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				String acmd = ((JCheckBox)e.getSource()).getText();
				JCheckBox src = (JCheckBox) e.getSource();
				
				if (acmd.equalsIgnoreCase("show fields")) {
					if (src.isSelected()) {
						baseShowField = true;
					} else {
						baseShowField = false;
					}
				}
				if (acmd.equalsIgnoreCase("show npcs")) {
					if (src.isSelected()) {
						baseShowNPC = true;
					} else {
						baseShowNPC = false;
					}
				}
				VisualMapBuilder.topMapPanel().repaint();//to repaint map
			}
		}
	}
	class showFieldPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		JCheckBox base,field,npc;
		public showFieldPanel () {
			base = new JCheckBox("show Base");
			field = new JCheckBox("show Fields");
			npc = new JCheckBox("show NPCs");
			
			base.setSelected(true);
			field.setSelected(true);
			npc.setSelected(false);
			
			field.setEnabled(false);
			setLayout(new GridLayout(3,1));
			add(base);
			add(field);
			add(npc);
			
			JPanel content = new JPanel();
			content.setLayout(new GridLayout(3,1));
			content.add(base);
			content.add(field);
			content.add(npc);
			content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			
			setLayout(new BorderLayout());
			add(content,"Center");
			setBorder(BorderFactory.createTitledBorder("Field tab options :"));
			
			base.addItemListener(new itemLis());
			npc.addItemListener(new itemLis());
		}
		class itemLis implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				String acmd = ((JCheckBox)e.getSource()).getText();
				JCheckBox src = (JCheckBox) e.getSource();
				
				if (acmd.equalsIgnoreCase("show base")) {
					if (src.isSelected()) {
						fieldShowBase = true;
					} else {
						fieldShowBase = false;
					}
				}
				if (acmd.equalsIgnoreCase("show npcs")) {
					if (src.isSelected()) {
						fieldShowNPC = true;
					} else {
						fieldShowNPC = false;
					}
				}
				VisualMapBuilder.topMapPanel().repaint();//to repaint map
			}
		}
	}
	class showNpcPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		JCheckBox base,field,npc;
		public showNpcPanel () {	
			base = new JCheckBox("show Base");
			field = new JCheckBox("show Fields");
			npc = new JCheckBox("show NPCs");
			
			base.setSelected(true);
			field.setSelected(true);
			npc.setSelected(true);
			
			npc.setEnabled(false);
			
			JPanel content = new JPanel();
			content.setLayout(new GridLayout(3,1));
			content.add(base);
			content.add(field);
			content.add(npc);
			content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			
			setLayout(new BorderLayout());
			add(content,"Center");
			setBorder(BorderFactory.createTitledBorder("NPC tab options :"));
			
			base.addItemListener(new itemLis());
			field.addItemListener(new itemLis());
		}
		class itemLis implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				String acmd = ((JCheckBox)e.getSource()).getText();
				JCheckBox src = (JCheckBox) e.getSource();
				
				if (acmd.equalsIgnoreCase("show base")) {
					if (src.isSelected()) {
						npcShowBase = true;
					} else {
						npcShowBase = false;
					}
				}
				if (acmd.equalsIgnoreCase("show fields")) {
					if (src.isSelected()) {
						npcShowField = true;
					} else {
						npcShowField = false;
					}
				}
				VisualMapBuilder.topMapPanel().repaint();//to repaint map
			}
		}
	}
	class southPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		JCheckBox CB_saveMapXY,CB_showSaveMsg;
		public southPanel () {	
			CB_saveMapXY = new JCheckBox("save mapX and mapY after each session");
			CB_showSaveMsg = new JCheckBox("show a message, when level saved");
			
			CB_saveMapXY.setSelected(true);
			CB_showSaveMsg.setSelected(true);
			
			JPanel content = new JPanel();
			content.setLayout(new GridLayout(2,1));
			content.add(CB_saveMapXY);
			content.add(CB_showSaveMsg);
			content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			
			setLayout(new BorderLayout());
			add(content,"Center");
			setBorder(BorderFactory.createTitledBorder("Other options :"));
			
			CB_saveMapXY.addItemListener(new itemLis());
			CB_showSaveMsg.addItemListener(new itemLis());
		}
		class itemLis implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				String acmd = ((JCheckBox)e.getSource()).getText();
				JCheckBox src = (JCheckBox) e.getSource();
				
				if (acmd.equalsIgnoreCase("show a message, when level saved")) {
					if (src.isSelected()) {
						showSaveMsg = true;
					} else {
						showSaveMsg = false;
					}
				}
				if (acmd.equalsIgnoreCase("save mapX and mapY after each session")) {
					if (src.isSelected()) {
						npcShowField = true;
					} else {
						npcShowField = false;
					}
				}
				VisualMapBuilder.topMapPanel().repaint();//to repaint map
			}
		}
	}
	class buttonPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		JButton ok;
		public buttonPanel () {	
			ok = new JButton("Done");
			
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			content.add(ok,"Center");
			content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			
			ok.addActionListener(new actLis());
			
			setLayout(new BorderLayout());
			add(content,"Center");
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		class actLis implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				optFrame.setVisible(false);
			}
		}
	}
	
	/*
	//test
	public static void main (String[] args) {
		new OptionFrame().setVisible(true);
	}
	*/

	public boolean baseShowField() {
		return this.baseShowField;
	}
	public boolean baseShowNPC() {
		return this.baseShowNPC;
	}
	public boolean fieldShowBase() {
		return this.fieldShowBase;
	}
	public boolean fieldShowNPC() {
		return this.fieldShowNPC;
	}
	public boolean npcShowBase() {
		return this.npcShowBase;
	}
	public boolean showSaveMsg() {
		return this.showSaveMsg;
	}
	public boolean saveMapXY() {
		return this.saveMapXY;
	}
	public boolean npcShowField() {
		return this.npcShowField;
	}

	private void createDataFile () {
		try {
			File file = new File(path);
			file.createNewFile();
			BufferedWriter toFile = new BufferedWriter(new FileWriter(
					path));

			toFile.write("0");toFile.newLine();
			toFile.write("0");toFile.newLine();
			toFile.write("1");toFile.newLine();
			toFile.write("0");toFile.newLine();
			toFile.write("1");toFile.newLine();
			toFile.write("1");toFile.newLine();
			toFile.write("1");toFile.newLine();
			toFile.write("1");toFile.newLine();
			toFile.write("0");toFile.newLine();
			toFile.write("0");toFile.newLine();
			toFile.write("0");toFile.newLine();
			toFile.write("0");toFile.newLine();
			toFile.write("0");toFile.newLine();

			toFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
