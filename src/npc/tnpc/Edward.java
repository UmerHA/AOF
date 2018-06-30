package npc.tnpc;

import mainPackage.MainApplet;
import npc.TNPC;

public class Edward extends TNPC {

	public Edward(int x, int y, int id) {
		super(x, y, id, "pics/monster/small/joe.png");
		this.examineText = "He might give me some information.";
		this.name = "Edward";
		this.walkingRadius = 3;
		this.sleepingTime = 1200;
		this.cow = 0.5F;
	}

	public void use () {
		CVM.startConverstion(this);
		
		String[] temp = new String[5];
		temp[0] = "Hello";
		temp[1] = "Who are you?";
		temp[2] = "Where are we?";
		temp[3] = "What can I do in this area?";
		temp[4] = "Do you know where I can get some money?";
		CVM.giveOptions(temp);
		talkID = 0;
	}
	
	public void talk1() {
		CVM.say("Hello, what can I do for you?");
		String[] temp = new String[4];
		temp[0] = "Who are you?";
		temp[1] = "Do you know where we are?";
		temp[2] = "What can I do in this area?";
		temp[3] = "Do you know where I can get some money?";
		CVM.giveOptions(temp);
		talkID = 1;
	}
	public void talk2() {
		CVM.say("My name is Edward. I used to be a famous adventurer,");
		CVM.say("but these days I help new adventurers to find their way.");
		String[] temp = new String[4];
		temp[0] = "Do you know where we are?";
		temp[1] = "What can I do in this area?";
		temp[2] = "Do you know where I can get some money?";
		temp[3] = "Thanx for the information. I'll be off then.";
		CVM.giveOptions(temp);
		talkID = 2;
	}
	public void talk3() {
		CVM.say("Yes I do. We're in the far east of <land's name>.");
		CVM.say("There's a small village to the south. I'm sure you'll find");
		CVM.say("more information there.");
		String[] temp = new String[4];
		temp[0] = "Who are you?";
		temp[1] = "What can I do in this area?";
		temp[2] = "Do you know where I can get some money?";
		temp[3] = "Thanx for the information. I'll be off then.";
		CVM.giveOptions(temp);
		talkID = 3;
	}
	public void talk4() {
		CVM.say("Well you could explore a bit if you want, train yourself in combat by");
		CVM.say("killing some monster. Otherwise you could ask the people around here if");
		CVM.say("they have somehting to do for you.");
		String[] temp = new String[5];
		temp[0] = "Do you know where we are?";
		temp[1] = "Who are you";
		temp[2] = "Do you know where I can get some money?";
		temp[3] = "I don't see any people around here.";
		temp[4] = "Thanx for the information. I'll be off then.";
		CVM.giveOptions(temp);
		talkID = 4;
	}
	public void talk5() {
		CVM.say("You could slay some monster, maybe they give you something valuable.");
		CVM.say("Otherwise just ask the people around here, if you can lend them a");
		CVM.say("hand. I'm sure they will reward you somehow.");
		String[] temp = new String[5];
		temp[0] = "Do you know where we are?";
		temp[1] = "Who are you";
		temp[2] = "What can I do in this area?";
		temp[3] = "I don't see any people around here.";
		temp[4] = "Thanx for the information. I'll be off then.";
		CVM.giveOptions(temp);
		talkID = 5;
	}
	public void talk6() {
		CVM.say("No problem "+MainApplet.actPlayer.getName()+". That's what I'm here for.");
		String[] temp = new String[3];
		temp[0] = "Where do you know my name from?";
		temp[1] = "\"That's why I'm here for?\" What do you mean?";
		temp[2] = "Thanx, bye.";
		CVM.giveOptions(temp);
		talkID = 6;
	}
	public void talk7() {
		CVM.say("There is a village to the south. I'm sure you will find some people there");
		String[] temp = new String[5];
		temp[0] = "Do you know where we are?";
		temp[1] = "Who are you";
		temp[2] = "Do you know where I can get some money?";
		temp[3] = "What can I do around here?";
		temp[4] = "Thanx for the information. I'll be off then.";
		CVM.giveOptions(temp);
		talkID = 7;
	}
	public void talk8() {
		CVM.say("Good Bye");
		CVM.stopConversation();
	}
	public void talk9() {
		CVM.say("I used to be adventurer, so I learned a lot of strange, but");
		CVM.say("fascinating stuff. Someday you'll also be able to know other");
		CVM.say(" peoples' name without asking.");
		String[] temp = new String[1];
		temp[0] = "OK, bye";
		CVM.giveOptions(temp);
		talkID = 9;
	}
	
	// for CVM
	public void giveAnswer(int i) {
		if (talkID == 0) {
			switch (i) {
			case 0:talk1();break;
			case 1:talk2();break;
			case 2:talk3();break;
			case 3:talk4();break;
			case 4:talk5();break;
			}
		} else if (talkID == 1) {
			switch (i) {
			case 0:talk2();break;
			case 1:talk3();break;
			case 2:talk4();break;
			case 3:talk5();break;
			}			
		} else if (talkID == 2) {
			switch (i) {
			case 0:talk3();break;
			case 1:talk4();break;
			case 2:talk5();break;
			case 3:talk6();break;
			}			
		} else if (talkID == 3) {
			switch (i) {
			case 0:talk2();break;
			case 1:talk4();break;
			case 2:talk5();break;
			case 3:talk6();break;
			}			
		} else if (talkID == 4) {
			switch (i) {
			case 0:talk3();break;
			case 1:talk2();break;
			case 2:talk5();break;
			case 3:talk6();break;
			case 4:talk7();break;
			}			
		} else if (talkID == 5) {
			switch (i) {
			case 0:talk3();break;
			case 1:talk2();break;
			case 2:talk4();break;
			case 3:talk7();break;
			case 4:talk6();break;
			}			
		} else if (talkID == 6) {
			switch (i) {
			case 0:talk9();break;
			case 1:talk9();break;
			case 2:talk8();break;
			}			
		} else if (talkID == 7) {
			switch (i) {
			case 0:talk3();break;
			case 1:talk2();break;
			case 2:talk5();break;
			case 3:talk4();break;
			case 4:talk6();break;
			}			
		} else if (talkID == 9) {
			switch (i) {
			case 0:talk8();break;
			}			
		}
	}
}
