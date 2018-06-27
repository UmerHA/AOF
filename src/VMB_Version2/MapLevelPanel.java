package VMB_Version2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapLevelPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lv;
	private JButton addLV;
	private JComboBox<String> chooseLV;
	private JPanel north, south;
	private byte curLV = 0;// current LV
	private actLis actlis;

	public MapLevelPanel() {
		lv = new JLabel("LV ");
		addLV = new JButton("add LV");
		chooseLV = new JComboBox<String>();
		north = new JPanel();
		south = new JPanel();

		north.add(lv);
		north.add(chooseLV);
		south.add(addLV);

		setLayout(new GridLayout(2, 1));
		add(north);
		add(south);

		actlis = new actLis();
		chooseLV.setActionCommand("choose lv");
		chooseLV.addActionListener(actlis);
		addLV.setActionCommand("add lv");
		addLV.addActionListener(actlis);

		chooseLV.setFocusable(false);
		addLV.setFocusable(false);
	}

	class actLis implements ActionListener {
		boolean settingData = false;

		public void actionPerformed(ActionEvent e) {
			if (settingData)
				return;

			String acmd = e.getActionCommand();

			// JComboBox [chooseLV]
			if (acmd.equalsIgnoreCase("choose lv")) {
				byte newLV = Byte.parseByte(((String) chooseLV
						.getSelectedItem()).trim());

				if (curLV != newLV) {
					curLV = newLV;
					VisualMapBuilder.topMapPanel().setLevel(curLV);
					System.out.println("LevelPanel.actLis :: lv changed");
				} else {
					System.out.println("LevelPanel.actLis :: lv not changed");
				}
			}

			// Jbutton [addLV]
			if (acmd.equalsIgnoreCase("add lv")) {
				VisualMapBuilder.lvWin().setVisible(true);
			}
		}
	}

	public void refresh() {
		// recalc data
		int minLV = VisualMapBuilder.topMapPanel().getLowestLevel();
		int maxLV = VisualMapBuilder.topMapPanel().getHighestLevel();

		int levelAmount = (int) Math.abs(minLV) + maxLV + 1;
		String[] data = new String[levelAmount];

		for (int i = 0; i < levelAmount; i++)
			data[i] = String.valueOf(i + minLV);

		// show new data
		actlis.settingData = true;
		chooseLV.removeAllItems();
		for (int k = 0; k < data.length; k++)
			chooseLV.addItem(data[k]);
		actlis.settingData = false;

		// select current lv
		int index = 0;
		for (int n = 0; n < levelAmount; n++) {
			if (VisualMapBuilder.topMapPanel().getMapLV() == n + minLV) {
				index = n;break;
			}
		}
		
		chooseLV.setSelectedIndex(index);
	}

	public void setSelection(int index) {
		chooseLV.setSelectedIndex(index);
	}
}
