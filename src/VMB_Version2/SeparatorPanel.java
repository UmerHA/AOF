package VMB_Version2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class SeparatorPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JSeparator sep;

	public SeparatorPanel() {
		Dimension dim = new Dimension(15, 10);
		this.setPreferredSize(dim);
		
		sep = new JSeparator(JSeparator.VERTICAL);

		setLayout(new BorderLayout());
		add(sep,"Center");
		
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	}
}
