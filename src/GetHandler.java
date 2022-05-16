import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GetHandler implements HttpHandler {
    //get method
     @Override

     public void handle(HttpExchange he) throws IOException {
         // parse request
         URI requestedUri = he.getRequestURI();
         String query = requestedUri.getRawQuery();

         // send response
         //creates file for JSON reading
         File text = new File("data.json");
         // reads JSON file and puts string into body
         Scanner scn = new Scanner(text);
         String response = scn.nextLine();


         he.sendResponseHeaders(200, response.length());

         OutputStream os = he.getResponseBody();

         os.write(response.toString().getBytes());

         os.close();
     }

}