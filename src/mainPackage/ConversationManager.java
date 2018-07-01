package mainPackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import npc.TNPC;


public class ConversationManager {
	private TNPC npc; // the one you're talking with
	private String[] phrase; // your possible replies
	
	private int amount; // amount of phrases
	private int index; // id of phrase that's currently displayed
	
	private JTextField CVM_output;
	
	public ConversationManager () {		
		CVM_output = MainApplet.getGamePanel().chatbox().CVM_Field();
		CVM_output.addKeyListener(new keyLis());
	}

	public void scrollUp () {
		index --;
		if (index<1)
			index = 1;
		
		CVM_output.setText(phrase[index-1] + "  ["+index+"/"+amount+"]");
	}
	public void scrollDown () {
		index ++;
		if (index>amount)
			index = amount;
		
		CVM_output.setText(phrase[index-1] + "  ["+index+"/"+amount+"]");
	}
		
	public void startConverstion (TNPC npc) {
		this.npc = npc;
		npc.setInAction(true);
		MainApplet.actPlayer.setInAction(true);
		MainApplet.getGamePanel().chatbox().setInConversation(true);
		MainApplet.getGamePanel().chatbox().CVM_Field().grabFocus();
		
		System.out.println("CV started");
	}
	public void stopConversation () {
		npc.setInAction(false);
		MainApplet.actPlayer.setInAction(false);
		CVM_output.setText("");
		MainApplet.getGamePanel().chatbox().setInConversation(false);
		MainApplet.getGamePanel().fieldScreen().grabFocus();
		
		System.out.println("CV stopped");
	}

	class keyLis extends KeyAdapter {		
		public void keyPressed (KeyEvent e) {	
			
			switch (e.getKeyCode()) {
			case 87 :
			case 38 : scrollUp();break;
			case 83 :
			case 40 : scrollDown();break;
			case 10 : giveAnswer();break;
			case 27 : stopConversation();break;
			}
		}
	}
	
	
	private void giveAnswer () {
		MainApplet.addRedInfo(MainApplet.actPlayer.getName() + " : "+phrase[index-1]);
		npc.giveAnswer(index-1);
	}


	// npc will call these methods :
	public void say (String txt) {
		MainApplet.addBlueInfo(npc.getName() + " : "+txt);
	}
	public void giveOptions (String[] phrase) {
		this.phrase = phrase;
		index = 1;
		amount = phrase.length;
		CVM_output.setText(phrase[0] + "  [1/"+amount+"]");
	}



}
