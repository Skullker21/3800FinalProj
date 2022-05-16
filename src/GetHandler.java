import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
         String response = "Cart Retrieved";

         he.sendResponseHeaders(200, response.length());

         OutputStream os = he.getResponseBody();

         os.write(response.toString().getBytes());

         os.close();
     }

     public static void parseQuery(String query) throws UnsupportedEncodingException {

     }
}