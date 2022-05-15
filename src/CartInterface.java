import java.util.Scanner;

public class CartInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Item lemon = new Item("Lemon", 1, 1);
		Item rtx3090 = new Item("RTX 3090", 2000, 1);
		Item apple = new Item("Apple", 2, 1);
		
		Cart userCart = new Cart();
		
		System.out.println("Here are our items:\n"
				+ "Lemons: $1\n"
				+ "RTX 3090: $2000\n"
				+ "Apples: $2\n\n"
				+ "Type the name of item you want to purchase.\n"
				+ "You can also type \"cart\" to display your cart's information.\n"
				+ "Type \"remove\" to remove an item from your cart.\""
				+ "Type \"exit\" to close the application.");
		
		Scanner scn = new Scanner(System.in);
		
		String userInput = scn.next();
		while(!userInput.equals("exit")) {
			if(userInput.equals("Lemons")) {
				userCart.add(lemon);
			}
			else if(userInput.equals("RTX 3090")) {
				userCart.add(rtx3090);
			}
			else if(userInput.equals("Apples")) {
				userCart.add(apple);
			}
			else if(userInput.equals("remove")) {
				removingItems(userCart);
			}
			else if(userInput.equals("cart")) {
				userCart.print();;
			}
			userInput = scn.nextLine();
		}
		
		saveCart();
		
	}
	private static void removingItems(Cart userCart) {
		Item lemon = new Item("Lemon", 1, 1);
		Item rtx3090 = new Item("RTX 3090", 2000, 1);
		Item apple = new Item("Apple", 2, 1);
		
		System.out.println("Type the name of the item you want to remove.\n"
				+ "Type \"exit\" to continue adding to your cart.");
		
		Scanner scn = new Scanner(System.in);
		
		String userInput = scn.next();
		while(!userInput.equals("exit")) {
			if(userInput.equals("Lemons")) {
				userCart.remove(lemon);
			}
			else if(userInput.equals("RTX 3090")) {
				userCart.remove(rtx3090);
			}
			else if(userInput.equals("Apples")) {
				userCart.remove(apple);
			}
			userInput = scn.nextLine();
		}
	}

	private static void saveCart() {

		//POST request here

	}
}