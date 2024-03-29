import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CartInterface {

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		// items to buy
		Item lemon = new Item("Lemon", 1, 1);
		Item rtx3090 = new Item("RTX 3090", 2000, 1);
		Item apple = new Item("Apple", 2, 1);

		// makes user's cart
		Cart userCart = new Cart();

		Scanner scn = new Scanner(System.in);
		// prints instructions for user interface
		System.out.println("Enter your username: ");

		String userName = scn.next();

		System.out.println("Here are our items:\n"
				+ "Lemons: $1\n"
				+ "RTX 3090: $2000\n"
				+ "Apples: $2\n\n"
				+ "Type the name of item you want to purchase.\n"
				+ "You can also type \"cart\" to display your cart's information.\n"
				+ "Type \"remove\" to remove an item from your cart.\""
				+ "Type \"exit\" to close the application.");

		// list of commands user can input
		String userInput = scn.next();
		// exits program
		while(!userInput.equals("exit")) {
			// adds lemon to cart
			if(userInput.equals("Lemons")) {
				userCart.add(lemon);
			}
			// adds RTX 3090 to cart
			else if(userInput.equals("RTX 3090")) {
				userCart.add(rtx3090);
			}
			// adds apples to cart
			else if(userInput.equals("Apples")) {
				userCart.add(apple);
			}
			// moves user to remove menu
			else if(userInput.equals("remove")) {
				removingItems(userCart);
			}
			// displays contents of cart
			else if(userInput.equals("cart")) {
				userCart.print();;
			}
			// makes Post request to save the cart
			else if(userInput.equals("save")){
				saveCart(userName, userCart);
			}
			// makes Get request to load the cart
			else if(userInput.equals("load")){
				Cart tempCart = loadCart(userName);
				// if username does not match then do not load the cart
				if (tempCart != null) {
					userCart = tempCart;
					System.out.println("Cart Loaded");
				}

			}
			userInput = scn.nextLine();
		}
		
	}
	// remove menu
	private static void removingItems(Cart userCart) {
		// initializes items
		Item lemon = new Item("Lemon", 1, 1);
		Item rtx3090 = new Item("RTX 3090", 2000, 1);
		Item apple = new Item("Apple", 2, 1);

		System.out.println("Type the name of the item you want to remove.\n"
				+ "Type \"exit\" to continue adding to your cart.");

		Scanner scn = new Scanner(System.in);
		// removes desired item
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

	// uses post request to save the cart
	private static void saveCart(String userName, Cart cartToSave) throws URISyntaxException, IOException, InterruptedException {

		ObjectMapper mapper = new ObjectMapper();
		// turns items in cart to string
		String cartString = mapper.writeValueAsString(cartToSave.items);
		// connects username to cart items
		HashMap<String, String> values = new HashMap<>() {{
			put("user", userName);
			put("cart", cartString);
		}};
		// converts values into string
		String requestBody = mapper.writeValueAsString(values);

		// instantiates the HTTP Client and formats the Post request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/post"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.setHeader("User-Agent", "Java 17 HttpClient")
				.header("Content-Type", "application/json")
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		// prints response from server
		System.out.println(response.body());

	}
		// accesses server to load cart
	private static Cart loadCart(String name) throws JsonProcessingException {
		// instantiates the get request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/get"))
				.build();

		HttpResponse<String> response = null;
		try {
			response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		// converts in JSON file to returnable cart
		ObjectMapper mapper = new ObjectMapper();
		// retrieves string and converts to hashmap
		HashMap<String,String> map = mapper.readValue(response.body(), HashMap.class);
		// checks if saved data matches the username given
		if (!(map.get("user").equals(name))){

			System.out.println("Saved data does not match entered username.");
			return null;

		}
		// converts string within hashmap to another hashmap that contains items information
		HashMap<String, LinkedHashMap> mockItems = mapper.readValue(map.get("cart"), HashMap.class);

		// creates copies of items saved on server
		HashMap<String, Item> items = new HashMap<String, Item>();
		mockItems.forEach((key, value)
				-> {

			items.put(key, new Item((String) value.get("name"), (Integer) value.get("price"), (Integer) value.get("quantity")));

		});
		// creates cart with items
		Cart userCart = new Cart(items);

		return userCart;
	}
}
