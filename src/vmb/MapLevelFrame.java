package vmb;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MapLevelFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private ListPanel lPanel;
	private ButtonPanel bPanel;
	private LabelPanel lbPanel;
	
	private byte selectedLV;
	
	private JPanel content;
	
	public MapLevelFrame () {
		content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		lPanel = new ListPanel();
		bPanel = new ButtonPanel();
		lbPanel = new LabelPanel();
		
		content.setLayout(new BoxLayout(content,
                BoxLayout.LINE_AXIS));
		content.add(lPanel);
		content.add(Box.createHorizontalStrut(5));
		content.add(new JSeparator(JSeparator.VERTICAL));
		content.add(Box.createHorizontalStrut(5));
		content.add(bPanel);
		content.add(Box.createHorizontalStrut(5));
		content.add(new JSeparator(JSeparator.VERTICAL));
		content.add(Box.createHorizontalStrut(5));
		content.add(lbPanel);

		setLayout(new BorderLayout());
		add(content,"Center");
		
		setAlwaysOnTop(true);
		setTitle("Visual Map Builder V2 - Map Level Options");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(500,200);
	}	
	public void setVisible(boolean bool) {
		lbPanel.refreshLabels();
		lPanel.remakeList();
		super.setVisible(bool);
	}
	
	
	private void createNewLV (boolean higher) {
		int newLV;
		if (higher) {
			newLV = VisualMapBuilder.topMapPanel().getHighestLevel()+1;
			VisualMapBuilder.topMapPanel().setHighestLevel((byte)newLV);
		} else {
			newLV = VisualMapBuilder.topMapPanel().getLowestLevel()-1;
			VisualMapBuilder.topMapPanel().setLowestLevel((byte)newLV);
		}
		
		/* -> choose the base*/
		Object[] bases = {"Grass", "Road", "Underground", "Nothing"};
		String base = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Choose the ground base for the new level:",
		                    "Visual Map Builder V2",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    bases,
		                    "Grass");

		//If canceled
		if ((base == null) || (base.length() < 1)) 
			return;
		
		//Otherwise just go on
		
		/* <- */
		
		
		try {
			String fileName1 = "bin/data/BaseData" + newLV;
			String fileName2 = "bin/data/MapData" + newLV;
			String fileName3 = "bin/data/MonData" + newLV;

			// create files :
			File File1 = new File(fileName1);
			File1.createNewFile();
			File File2 = new File(fileName2);
			File2.createNewFile();
			File File3 = new File(fileName3);
			File3.createNewFile();

			// write into files :

			try {

				BufferedWriter toFile1 = new BufferedWriter(new FileWriter(
						fileName1));

				toFile1.write("[" + base + "]");// level base
				toFile1.newLine();

				toFile1.close();

				// MainProg.alert("Data Saved");
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		lPanel.remakeList();
		lbPanel.refreshLabels();
	}
	private void deleteLV (byte level) {
		System.out.println("MapLevelFrame : Why do you want to delete poor map level "+level+" ...?");
		
		byte maxLV = VisualMapBuilder.topMapPanel().getHighestLevel();
		byte minLV = VisualMapBuilder.topMapPanel().getLowestLevel();
		
		System.out.println("MapLevelFrame.deleteLV : maxLV = "+maxLV+" ; minLV = "+minLV);
		
		if (level == maxLV) {
			TopMapPanel tmp = VisualMapBuilder.topMapPanel();
			tmp.setHighestLevel((byte) (tmp.getHighestLevel()-1));
		} else if (level == minLV ){
			TopMapPanel tmp = VisualMapBuilder.topMapPanel();
			tmp.setLowestLevel((byte) (tmp.getLowestLevel()+1));
		} else {
			VisualMapBuilder.alert("You can only delete the highest or lowest map level");
			return;
		}
			
		
		File aFile = new File("bin/data/BaseData"+level);
		File bFile = new File("bin/data/MapData"+level);
		File cFile = new File("bin/data/MonData"+level);
		
		String info = "\n\nThe level's size :\n     -> Base  : "+aFile.length()+
		" bytes\n     -> Fields : "+bFile.length()+" bytes\n     -> NPCs  : "+cFile.length()+" bytes";
		
		Object[] options = {"Yes, I'm sure","No"};
		int n = JOptionPane.showOptionDialog(this,
				"Are you sure you want to delete map level "+level+"?"+info,
				"Visual Map Builder V2",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		
		if (n==1)
			return;
		
		//now delete file
		aFile.delete();
		bFile.delete();
		cFile.delete();
		
		
		lPanel.remakeList();
		lbPanel.refreshLabels();
	}
	
	/*
	 * test :
	public static void main (String[] args) {
		new MapLevelFrame().setVisible(true);
	}
	*/
	
	class ListPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JList<String> list;
		private DefaultListModel<String> listModel;
		
		public ListPanel () {
			listModel = new DefaultListModel<String>();
			
			list = new JList<String>(listModel);
			list.setLayoutOrientation(JList.VERTICAL);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.addListSelectionListener(new lLis());
			
			setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			setLayout(new BorderLayout());
			
			add(list,"Center");
		}
		
		class lLis implements ListSelectionListener {
			@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
		        //System.out.println("MapLevelFrame.ListPanel.lLis :: now selecting : "+ ((JList )e.getSource()).getSelectedIndex());
				bPanel.setLvButtonEnabled(true);
				try {
					selectedLV = Byte.parseByte(((String) ((JList<String> )e.getSource()).getSelectedValue()).trim());
				} catch (NullPointerException npe) {
					return;//just do nothing
				}
				System.out.println("MapLevelFrame : now selecting level "+selectedLV);
			}
		}
		public void remakeList () {
			int minLV = VisualMapBuilder.topMapPanel().getLowestLevel();
			int maxLV = VisualMapBuilder.topMapPanel().getHighestLevel();
			
			int levelAmount = (int) Math.abs(minLV)+maxLV+1;
			String[] data = new String[levelAmount];
			
			for (int i=0;i<levelAmount;i++) {
				data[i] = String.valueOf(i+minLV);
			}
			
			list.setListData(data);
			VisualMapBuilder.lvPanel().refresh();
		}
	}
	class ButtonPanel extends JPanel {
		private static final long serialVersionUID = 2L;

		JButton addTop, addBtm;
		JButton show, delete;
		
		public ButtonPanel () {
			addTop = new JButton("Add higher level");
			addBtm = new JButton("Add lower  level");
			show = new JButton("Show in VMB");
			delete = new JButton("Delete Level");

			
			setLayout(new GridLayout(4,1));
			add(addTop);
			add(addBtm);
			add(show);
			add(delete);
			
			setLvButtonEnabled (false);
			actLis actlis = new actLis();
			addTop.addActionListener(actlis);
			addBtm.addActionListener(actlis);
			show.addActionListener(actlis);
			delete.addActionListener(actlis);
		}
		
		public void setLvButtonEnabled (boolean bool) {
			show.setEnabled(bool);
			delete.setEnabled(bool);
		}
		
		public Component add (Component comp) {
			JPanel temp = new JPanel();
			temp.setLayout(new BorderLayout());
			temp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			temp.add(comp,"Center");
			super.add(temp);
			return comp;
		}
		
		class actLis implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Add higher level"))
					createNewLV(true);
				if (e.getActionCommand().equals("Add lower  level"))
					createNewLV(false);
				if (e.getActionCommand().equals("Show in VMB"))
					VisualMapBuilder.lvPanel().setSelection(lPanel.list.getSelectedIndex());//so wird die JComboBox auch gleich aktualisiert
				if (e.getActionCommand().equals("Delete Level"))
					deleteLV(selectedLV);
				
				
			}
		}
	}
	class LabelPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		JLabel[] lb = new JLabel[3];
		
		public LabelPanel () {
			lb[0] = new JLabel("Highest LV : "+VisualMapBuilder.topMapPanel().getHighestLevel());
			lb[1] = new JLabel("Lowest  LV : "+VisualMapBuilder.topMapPanel().getLowestLevel());
			
			setLayout(new GridLayout(4,1));
			add(lb[0]);
			add(lb[1]);
		}

		public Component add (Component comp) {
			JPanel temp = new JPanel();
			temp.setLayout(new FlowLayout());
			temp.add(comp);
			super.add(temp);
			return comp;
		}
		public void refreshLabels() {
			lb[0].setText("Highest LV : "+VisualMapBuilder.topMapPanel().getHighestLevel());
			lb[1].setText("Lowest  LV : "+VisualMapBuilder.topMapPanel().getLowestLevel());
		}
	}

	
}
