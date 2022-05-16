import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PostHandler implements HttpHandler {

     @Override

     public void handle(HttpExchange he) throws IOException {
         // parse request
         InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
         BufferedReader br = new BufferedReader(isr);

         String query = br.readLine(); //query is JSON

         parseQuery(query);

         System.out.println(query);

         // send response
         String response = "Shopping Cart Saved!";

         he.sendResponseHeaders(200, response.length());

         OutputStream os = he.getResponseBody();
         os.write(response.toString().getBytes());
         os.close();
     }
     public static void parseQuery(String query) throws IOException {
         FileWriter file = new FileWriter("data.json");
         file.write(query);
         file.close();
     }
}