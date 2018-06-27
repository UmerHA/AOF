package Items;

public class Empty extends Item {

	public Empty() {
		super ("pics/items/empty.jpg");
		this.name = "";
		this.internalName = this.name;
		this.examineText = "This inventory slot is empty";
		this.weight = 0;
	}
}
