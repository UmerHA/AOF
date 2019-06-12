package npc.tnpc;

import mainPackage.MainApplet;
import npc.TNPC;


public class Joe extends TNPC {
	public Joe(int x, int y, int id) {
		super(x, y, id, "joe.png");
		this.examineText = "It's just an average joe.";
	}

	public void use() {
		CVM.startConverstion(this);
		
		if (MainApplet.actPlayer.cocoMilk == 0) {
			talk0();
		} else if (MainApplet.actPlayer.cocoMilk == 1){
			talk11();
		} else {
			talk21();
		}
	}
	private void talk0() {
		CVM.say("Hi mate, what can I do for you?");
		String[] temp = new String[3];
		temp[0] = "Who are you?";
		temp[1] = "How are you?";
		temp[2] = "Where are we?";
		CVM.giveOptions(temp);
		talkID = 0;	
	}
	private void talk1() {
		CVM.say("I'm Joe.");
		String[] temp = new String[3];
		temp[0] = "Hahahahaha ... Joe ^^";
		temp[1] = "How are you";
		temp[2] = "Where are we?";
		CVM.giveOptions(temp);
		talkID = 1;
	}
	private void talk2() {
		CVM.say("Fine as ever, thanks.");
		CVM.say("...");
		CVM.say("But ...");

		String[] temp = new String[3];
		temp[0] = "But what ?";
		temp[1] = "Come on, spit it out.";
		temp[2] = "Where are we?";
		CVM.giveOptions(temp);
		talkID = 2;
	}
	private void talk3() {
		CVM.say("Buddy ... I am as clueless as you are");
		CVM.say("Yesterday I was peacefully harvesting my potatoes ...");
		CVM.say("... and now I'm here =(");
		
		String[] temp = new String[2];
		temp[0] = "Well, I guess I have to explore this area by myself...";
		temp[1] = "Can I help you somehow?";
		CVM.giveOptions(temp);
		talkID = 3;
	}
	private void talk4() {
		CVM.say("At least my mother didn't hate me enough to name me "+MainApplet.actPlayer.getName()+".");
		CVM.stopConversation();
	}
	private void talk5() {
		CVM.say("You know ...");
		CVM.say("I'm here, in the middle of nowhere. My feet hurt and I'd kill");
		CVM.say("for some coconut milk.");
		CVM.say("Could  you get me some?");
		String[] temp = new String[3];
		temp[0] = "Sorry, but I'm busy at the moment.";
		temp[1] = "Well, I don't have anything else to do. Why not?";
		temp[2] = "Why coconut milk?";
		CVM.giveOptions(temp);
		talkID = 5;
	}
	private void talk6() {
		CVM.say("Yeah, Good luck with that.");
		CVM.stopConversation();
	}
	private void talk7() {
		CVM.say("Well ok. Why would you like to help a mere farmer ...");
		CVM.stopConversation();
	}
	private void talk8() {
		CVM.say("Many thanks. I really appreciate that.");
		talk10();
	}
	private void talk9() {
		CVM.say("Because I love coconuts.");
		CVM.say("So, are you gonna help me?");
		String[] temp = new String[2];
		temp[0] = "Sorry, but I'm busy at the moment.";
		temp[1] = "Well, I don't have anything else to do. Why not?";
		CVM.giveOptions(temp);
		talkID = 9;
	}
	private void talk10() {
		CVM.say("There has to be a coconut tree somewhere around here.");
		CVM.say("I can smell the coconut milk. It has to be somewhere here");
		CVM.say("Right under my noes ...");
		CVM.stopConversation();
		MainApplet.actPlayer.cocoMilk = 1;
	}
	
	private void talk11() {
		CVM.say("Hi mate, how's your coconut search going?");

		String[] temp = new String[4];
		temp[0] = "Who are you?";
		temp[1] = "How are you";
		temp[2] = "I have a coconut in my inventory.";
		temp[3] = "I'm still searching.";
		CVM.giveOptions(temp);
		talkID = 11;
	}
	public void talk12() {
		CVM.say("The guy whom you promised to get him some coconut milk!");
		CVM.stopConversation();
	}
	public void talk13() {
		CVM.say("Fine, fine.");
		CVM.say("But it would be better with some milk of a coconut");
		CVM.stopConversation();
	}
	public void talk14() {
		CVM.say("Yahooo!! Thanks a lot!");
		CVM.say("Could you hand it over?");
		
		String[] temp = new String[2];
		temp[0] = "Yeah sure. Have some coconut milk.";
		temp[1] = "No, I'll keep it, it might be value";
		CVM.giveOptions(temp);
		talkID = 14;
	}
	public void talk15() {
		CVM.say("It's strange ... I can smell but don't see it");
		CVM.say("It has to be right under my nose ^^");
		CVM.stopConversation();
	}
	public void talk16() {
		CVM.say("Hi mate, how your coconut search going?");
	}
	public void talk17() {
		int tempInt = MainApplet.actPlayer.getInventoryManager().searchItem("Coconut");
		if (tempInt == -1) {
			CVM.say("Hey, don't try to fool me.");
			CVM.say("Come back, when you have the thing I want");
			CVM.stopConversation();
			return;
		}
		
		MainApplet.addInfo("You show Joe the coconut.");
		CVM.say("Thanks a lot buddy.");
		CVM.say("Here, have some money");
		MainApplet.addInfo("Joe takes the coconut and gives you 500 coins");
		MainApplet.actPlayer.getInventoryManager().destroyItem(tempInt);
		MainApplet.actPlayer.addCoins(500);
		MainApplet.actPlayer.cocoMilk = 2;
		CVM.stopConversation();
	}
	public void talk18() {
		int tempInt = MainApplet.actPlayer.getInventoryManager().searchItem("Coconut");
		if (tempInt == -1) {
			CVM.say("You don't even have the coconut on you.");
			CVM.say("Come back, when you have the thing I want");
			CVM.stopConversation();
			return;
		}
		
		MainApplet.addInfo("Joe seems to get very angry...");
		CVM.say("Hey buddy! Give ... me ... that ... co .. co .. nut!");
		CVM.say("I'm kind today ... Hand it over and I'll give you 400 coins.");
		CVM.say("This way no one will get hurt.");
		
		String[] temp = new String[2];
		temp[0] = "Woah OK, here. Take it.";
		temp[1] = "Nope. It's my property and I decide whom I give it to";
		CVM.giveOptions(temp);
		talkID = 18;
	}
	public void talk19() {
		CVM.say("Thanks ... Here, take your money ...");
		MainApplet.addInfo("Joe gives you 400 coins");
		MainApplet.actPlayer.getInventoryManager().destroyItem(MainApplet.actPlayer.getInventoryManager().searchItem("Coconut"));
		MainApplet.actPlayer.addCoins(400);
		MainApplet.actPlayer.cocoMilk = 2;
		CVM.stopConversation();
	}
	public void talk20() {
		MainApplet.addInfo("Joe hits you (You loose 20 HP), takes the coconut and throws 250 coins at you.");
		MainApplet.actPlayer.getInventoryManager().destroyItem(MainApplet.actPlayer.getInventoryManager().searchItem("Coconut"));
		MainApplet.actPlayer.addCoins(250);
		MainApplet.actPlayer.changeHP(-20);
		MainApplet.actPlayer.cocoMilk = 2;
		CVM.stopConversation();
	}
	
	public void talk21() {
		CVM.say("Thanks again for bringing me a coconut");
		CVM.stopConversation();
	}
	
	// for CVM
	public void giveAnswer(int i) {
		if (talkID == 0) {
			switch (i) {
			case 0:talk1();break;
			case 1:talk2();break;
			case 2:talk3();break;
			}
		} else if (talkID == 1) {
			switch (i) {
			case 0:talk4();break;
			case 1:talk2();break;
			case 2:talk3();break;
			}			
		} else if (talkID == 2) {
			switch (i) {
			case 0:talk5();break;
			case 1:talk5();break;
			case 2:talk3();break;
			}			
		} else if (talkID == 3) {
			switch (i) {
			case 0:talk6();break;
			case 1:talk5();break;
			}			
		} else if (talkID == 5) {
			switch (i) {
			case 0:talk7();break;
			case 1:talk8();break;
			case 2:talk9();break;
			}			
		} else if (talkID == 9) {
			switch (i) {
			case 0:talk7();break;
			case 1:talk8();break;
			}			
		} else if (talkID == 11) {
			switch (i) {
			case 0:talk12();break;
			case 1:talk13();break;
			case 2:talk14();break;
			case 3:talk15();break;
			}			
		} else if (talkID == 14) {
			switch (i) {
			case 0:talk17();break;
			case 1:talk18();break;
			}			
		}
	}
}
