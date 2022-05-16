import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner;

public class CartInterface {

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		Item lemon = new Item("Lemon", 1, 1);
		Item rtx3090 = new Item("RTX 3090", 2000, 1);
		Item apple = new Item("Apple", 2, 1);

		Cart userCart = new Cart();

		Scanner scn = new Scanner(System.in);

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
			else if(userInput.equals("save")){
				saveCart(userName, userCart);
			}
			else if(userInput.equals("load")){
				loadCart();
			}
			userInput = scn.nextLine();
		}
		
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

	private static void saveCart(String userName, Cart cartToSave) throws URISyntaxException, IOException, InterruptedException {

		ObjectMapper mapper = new ObjectMapper();

		String cartString = mapper.writeValueAsString(cartToSave.items);


		HashMap<String, String> values = new HashMap<>() {{
			put("user", userName);
			put("cart", cartString);
		}};

		String requestBody = mapper.writeValueAsString(values);

		System.out.println(requestBody);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/post"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.setHeader("User-Agent", "Java 17 HttpClient")
				.header("Content-Type", "application/json")
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.statusCode());

		System.out.println(response.body());

	}

	private static void loadCart() throws JsonProcessingException {
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

		String whateverthefrick = String.valueOf(response);



		System.out.println(response.body());
		ObjectMapper mapper = new ObjectMapper();

		HashMap<String,String> map = mapper.readValue(response.body(), HashMap.class);
		System.out.println(map.toString());


		Cart userCart = new Cart(mapper.readValue(map.get("cart"), HashMap.class));

		userCart.print();
	}
}
