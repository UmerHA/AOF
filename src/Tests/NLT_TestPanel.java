package Tests;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/* NLT = null layout test */

@SuppressWarnings("serial")
public class NLT_TestPanel extends JPanel {
	private JLayeredPane laypane;
	private NLT_ColPanel movePanel;
	private mmLis mmlis = new mmLis();
	private boolean showMovePanel = false;
	private final int startPopUps = 5;
	
	private NLT_PanelSwitcher menu;
	private boolean menuVisible = true;
	private int menuX = 15;
	private int menuY = 200;
	
	NLT_PopUpCreator puc;
	private boolean pucVisible = true;
	private int pucX = 1000;
	private int pucY = 10;	
	
	NLT_ColPanelCreator cpc;
	private boolean cpcVisible = true;
	private int cpcX = 1260;
	private int cpcY = 10;
	
	public NLT_TestPanel () {
		laypane = new JLayeredPane();
		laypane.setOpaque(true);
		
		setLayout(new BorderLayout());
		add(laypane,"Center");
		
		// back panel :
		NLT_BackPanel back = new NLT_BackPanel();
		back.setBounds(0, 0, getWidth(), getHeight());
		laypane.add(back,new Integer(-1));
		
		// move panel :
		if (showMovePanel) {
			movePanel = new NLT_ColPanel();
			movePanel.setBounds(0, 400, 150, 100);
			laypane.add(movePanel,new Integer(1));
			laypane.addMouseMotionListener(mmlis);
			movePanel.register(laypane);/* needs to be registered before it can be focused*/
			movePanel.focus();
		}
		
		// color panels and pop up :
		for (int i=0;i<startPopUps;i++)
			addNewColPanel();
		addNewPopUp("Are you a watermelon?","Yes","No");
		
		// menu panel :
		menu = new NLT_PanelSwitcher();
		menu.setBounds(menuX, menuY,270,490);
		laypane.add(menu, new Integer(0));
		for (byte i=0;i<4;i++) {
			NLT_ColPanel temp = new NLT_ColPanel();
			temp.setDragAble(false);
			((NLT_PanelSwitcher) menu).addPanel(temp, "Panel "+i);
		}
		NLT_PanelSlot slotPanel = new NLT_PanelSlot();
		slotPanel.setDragAble(false);
		slotPanel.setBounds(0, 0,250,450);
		((NLT_PanelSwitcher) menu).addPanel(slotPanel, "Slots");
		((NLT_PanelSwitcher) menu).setActivePanel(3);
		
		// pop up creator :
		puc = new NLT_PopUpCreator(this);
		puc.setBounds(pucX, pucY,250,160);
		laypane.add(puc, new Integer(0));
		
		// color panel creator :
		cpc = new NLT_ColPanelCreator(this);
		cpc.setBounds(cpcX, cpcY,180,40);
		laypane.add(cpc, new Integer(0));
		
		menu.register(laypane);
		puc.register(laypane);
		cpc.register(laypane);
		
		if (showMovePanel) {
			menu.addMouseMotionListener(new mmLis());
			puc.addMouseMotionListener(new mmLis());
			cpc.addMouseMotionListener(new mmLis());
			movePanel.addMouseMotionListener(new mmLis());// strange, but neccessary
		}
		
		/* key binding */
		laypane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("M"),"showHideMenu");
		laypane.getActionMap().put("showHideMenu",
		                             new showHidePanelSwitcher());
		
		laypane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"),"showHidePUC");
		laypane.getActionMap().put("showHidePUC",
		                             new showHidePUC());
		
		laypane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"),"showHideCPC");
		laypane.getActionMap().put("showHideCPC",
		                             new showHideCPC());
		/* - */
	}
	
	class mmLis extends MouseMotionAdapter {
		public void mouseMoved (MouseEvent e) {
			if (!showMovePanel)
				return;
			movePanel.setLocation(e.getX(), e.getY());
		}
		public void mouseDragged (MouseEvent e) {
			if (!showMovePanel)
				return;
			movePanel.setLocation(e.getX(), e.getY());
		}
	}
	public MouseMotionListener mmLis() {
		return mmlis;
	}

	private final byte offsetX = 15;
	private final byte offsetY = 15;
	private static int popUpNum = 1;
	
	public void addNewPopUp (String msg,String yes,String no) {
		popUpNum++;
		
		NLT_PopUp temp = new NLT_PopUp(this,msg,yes,no);
		temp.setBounds(offsetX*popUpNum,offsetY*popUpNum,250,60);
		laypane.add(temp,new Integer(0));
		temp.register(laypane);
		temp.focus();
		if (showMovePanel)
			temp.addMouseMotionListener(new mmLis());
		
		if (showMovePanel)
			movePanel.focus();
	}

	public void addNewColPanel () {
		int x,y,w,h;
		x = (int) (Math.floor(Math.random()*1600));
		y = (int) (Math.floor(Math.random()*1000));
		w = (int) (Math.floor(Math.random()*800));
		h = (int) (Math.floor(Math.random()*600));
		
		NLT_ColPanel temp = new NLT_ColPanel();
		temp.setBounds(x, y, w, h);
		laypane.add(temp, new Integer(0));
		temp.register(laypane);
		temp.focus();
		if (showMovePanel) {
			temp.addMouseMotionListener(new mmLis());
			movePanel.focus();
		}
	}
	
	/* main method */
	public static void main (String[] args) {
		NLT_TestPanel p = new NLT_TestPanel();
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		f.add(p,"Center");
		f.setTitle("NullLayout Test Window");
		f.setSize(1680,1020);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	/* - */
	
	/* for key binding : */
	class showHidePanelSwitcher extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (menuVisible) {//hide menu
				menuX = menu.getLocation().x;
				menuY = menu.getLocation().y;
				menu.setBounds(0,0,0,0);
			} else {//show menu
				menu.setBounds(menuX,menuY,270,490);
			}
			menuVisible = !menuVisible;
			menu.focus();
			if (showMovePanel)
				movePanel.focus();
		}
	}
	class showHidePUC extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (pucVisible) {//hide menu
				pucX = puc.getLocation().x;
				pucY = puc.getLocation().y;
				puc.setBounds(0,0,0,0);
			} else {//show menu
				puc.setBounds(pucX,pucY,250,160);
			}
			pucVisible = !pucVisible;
			puc.focus();
			if (showMovePanel)
				movePanel.focus();
		}
	}
	class showHideCPC extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (cpcVisible) {//hide menu
				cpcX = cpc.getLocation().x;
				cpcY = cpc.getLocation().y;
				cpc.setBounds(0,0,0,0);
			} else {//show menu
				cpc.setBounds(cpcX,cpcY,180,40);
			}
			cpcVisible = !cpcVisible;
			cpc.focus();
			if (showMovePanel)
				movePanel.focus();
		}
	}
}
