
public class Item {
	String name;
	int price;
	int quantity;
	
	public Item(String n, int p, int q) {
		name = n;
		price = p;
		quantity = q;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int p) {
		price = p;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int q) {
		quantity = q;
	}
}
