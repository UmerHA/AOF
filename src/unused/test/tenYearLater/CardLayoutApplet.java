package unused.test.tenYearLater;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.LI_Panel;

public class CardLayoutApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	JButton changeCard;
	JPanel cards;

	CardLayout cardLayout;
	String currentCard;

	String LIPANEL = "LI Panel";
	String BORINGPANEL = "Boring Panel";

	@Override
	public void init() {
		setLayout(new BorderLayout());

		changeCard = new JButton("Change Card");
		changeCard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCard();
			}
		});

		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		cards.add(new LI_Panel(), LIPANEL);
		cards.add(new BoringPanel(), BORINGPANEL);

		add(changeCard, "North");
		add(cards, "Center");

		cardLayout.show(cards, BORINGPANEL);
		currentCard = BORINGPANEL;
	}

	void changeCard() {
		if (currentCard.equals(BORINGPANEL)) {
			cardLayout.show(cards, LIPANEL);
			currentCard = LIPANEL;
		} else {
			cardLayout.show(cards, BORINGPANEL);
			currentCard = BORINGPANEL;
		}
	}

	class BoringPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.fillRect(10, 10, 110, 110);
		}
	}

	class BoringPanel2 extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.fillRect(100, 100, 110, 110);
		}
	}
}