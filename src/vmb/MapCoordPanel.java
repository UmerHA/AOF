package vmb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MapCoordPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lbX, lbY;
	private NumberOnlyTextField tfX, tfY;
	
	public MapCoordPanel () {
		JPanel content = new JPanel();
		
		lbX = new JLabel("   Map X : ");
		lbY = new JLabel("   Map Y : ");
		
		tfX = new NumberOnlyTextField();
		tfY = new NumberOnlyTextField();
		
		content.setLayout(new GridLayout(2,2));
		content.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		content.add(lbX);
		content.add(tfX);
		content.add(lbY);
		content.add(tfY);
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		add(content,"Center");
		
		tfX.getDocument().addDocumentListener(new docLis((byte)1));
		tfY.getDocument().addDocumentListener(new docLis((byte)2));
		tfX.addKeyListener(VisualMapBuilder.topMapPanel().getKeyLis());
		tfY.addKeyListener(VisualMapBuilder.topMapPanel().getKeyLis());
	}

	class docLis implements DocumentListener {
		byte type;
		public docLis (byte type) {
			this.type = type; 
		}
		
		public void changedUpdate(DocumentEvent e) {
			 //Plain text components do not fire these events
		}
		
		
		public void insertUpdate(DocumentEvent e) {
			updateMapXY();
		}
		public void removeUpdate(DocumentEvent e) {
			updateMapXY();
		}
		
		private void updateMapXY () {
			int x = 0;
			int y = 0;
			
			if (!tfX.getText().trim().equals(""))
				x = Integer.parseInt(tfX.getText());
			if (!tfY.getText().trim().equals(""))
				y = Integer.parseInt(tfY.getText());
			
			if (type == 1) {
				VisualMapBuilder.topMapPanel().setMapX(x);
			}
			if (type == 2) {
				VisualMapBuilder.topMapPanel().setMapY(y);
			}
		}
	}

	public void setMapCoords (int x, int y) {
		tfX.setText(String.valueOf(x));
		tfY.setText(String.valueOf(y));
	}
}
