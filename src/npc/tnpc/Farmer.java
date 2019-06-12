package npc.tnpc;

import npc.TNPC;

public class Farmer extends TNPC {

	public Farmer(int x, int y, int id) {
		super(x, y, id, "joe.png");
		this.examineText = "Howdy mate. Wanna buy some potatoes?";
	}

	public void use () {
		CVM.startConverstion(this);
				
		talk0();
	}
	
	private void talk0() {
		CVM.say("Get outta my house !");
		
		String[] temp = new String[3];
		temp[0] = "Woah OK";
		temp[1] = "Hey, don't be so rude!";
		temp[2] = "Can you sell me somehting? You're a farmer after all.";
		CVM.giveOptions(temp);
		talkID = 0;	
	}
	private void talk1() {
		CVM.say("Then don't just enter my house!");
		CVM.stopConversation();
	}
	private void talk2() {
		CVM.say("Hmm, I might have some plunder. Let's see");
		CVM.stopConversation();
		Shop.openShop(new shops.FarmerShop());
	}

	
	// for CVM
	public void giveAnswer(int i) {
		if (talkID == 0) {
			switch (i) {
			case 0:CVM.stopConversation();break;
			case 1:talk1();break;
			case 2:talk2();break;
			}
		}
	}
}
