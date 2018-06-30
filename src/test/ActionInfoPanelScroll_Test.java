package test;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import mainPackage.ActionInfoPanelScroll;

public class ActionInfoPanelScroll_Test {
	public static void main (String[] args) {
		ActionInfoPanelScroll aips = new ActionInfoPanelScroll();
		JFrame f = new JFrame("TestWin");
		f.setSize(200,450);
		f.setLayout(new BorderLayout());
		f.add(aips);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i=0;i<30;i++)
			aips.addInfo("("+i+")");
	}
}
