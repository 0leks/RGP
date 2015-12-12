package one;

import java.util.ArrayList;

public class Shop extends Thing {
  private String name;
	public ArrayList<Item> onsale;
	public Shop(int sx, int sy, int sw, int sh, World smyworld, String sname) {
		super(sx, sy, sw, sh, smyworld);
		onsale = new ArrayList<Item>();
		name = sname;
		
	}
	
	public String getName() { return name; }
	
	public Item buy(String name) {
		for(Item i : onsale) {
			if(i.name.equals(name)) {
				i.amount--;
				return new Item(i.name, 1, myworld);
			}
		}
		return null;
	}
	public Item buy(int index) {
		if(index<onsale.size() && index>=0) {
			Item i = onsale.get(index);
			if(i.amount>0) {
				i.amount--;
				return new Item(i.name, 1, myworld);
			}
		}
		return null;
	}
	
}
