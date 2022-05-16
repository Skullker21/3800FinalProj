import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GetHandler implements HttpHandler {

     @Override

     public void handle(HttpExchange he) throws IOException {
         // parse request
         URI requestedUri = he.getRequestURI();
         String query = requestedUri.getRawQuery();

         parseQuery(query);

         // send response
         File text = new File("data.json");
         Scanner scn = new Scanner(text);
         String response = scn.nextLine();


         he.sendResponseHeaders(200, response.length());

         OutputStream os = he.getResponseBody();

         os.write(response.toString().getBytes());

         os.close();
     }

     public static void parseQuery(String query) throws UnsupportedEncodingException {

     }
}