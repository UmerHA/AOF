package client.items;

public class Empty extends Item {

	public Empty() {
		super ("items/empty.png");
		this.name = "";
		this.internalName = this.name;
		this.examineText = "This inventory slot is empty";
		this.weight = 0;
	}
}
