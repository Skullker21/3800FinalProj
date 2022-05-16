import java.util.HashMap;

public class Cart {
	HashMap<String, Item> items = new HashMap<String, Item>();
	
	public Cart() {
		
	}
	public Cart(HashMap<String, Item> i){
		items = i;
	}
	
	// adds item to the list of items
	// adds 1 to quanity if item is already on list
	public void add(Item i) {
		String name = i.getName();
		if(items.containsKey(name)) {
			Item temp = items.get(name);
			int quantity = temp.getQuantity();
			
			temp.setQuantity(quantity + i.getQuantity());
		}
		else {
			items.put(i.getName(), i);
		}
	}
	
	// subtracts 1 to quanity if item is already on list
	// if quantity = 0, remove item from hashmap
	public void remove(Item i) {
		String name = i.getName();
		if(items.containsKey(name)) {
			Item storedItem = items.get(name);
			int numberOfItems = storedItem.getQuantity();
			if(numberOfItems == 1) {
				items.remove(name);
			}
			else {
				storedItem.setQuantity(numberOfItems - 1);
			}
		}
		else {
			System.out.println(name + " does not currently exist in the cart.");
		}
	}
	
	public void removeCategory(String name) {
		items.remove(name);
	}
	public void print() {
		items.forEach((key, value)
                -> System.out.println(value.getName() + " - Quantity: " + value.getQuantity()));
	}
}
