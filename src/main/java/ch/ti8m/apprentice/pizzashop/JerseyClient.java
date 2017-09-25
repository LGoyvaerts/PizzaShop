package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Bestellung;
import ch.ti8m.apprentice.pizzashop.model.Pizza;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

import java.util.List;
import java.util.Random;


public class JerseyClient {

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            JerseyClient client = new JerseyClient();
            String pizza = "NewPizza" + random.nextInt(1000) + 1;
            float price = random.nextFloat() * 25.0f + 15.0f;

            client.createPizza(pizza, price);
            System.out.println("-------------------------------------------------------");
            client.getPizzas();
            System.out.println("-------------------------------------------------------");
            client.createOrder(pizza, "1", "Mustermann", "Max", "Musterstrasse", "1111", "Musterort", "01122334455");
            System.out.println("-------------------------------------------------------");
            client.getOrders();
            // client.testVoid();
        }
    }

    private Client createClient() {
        Client client = Client.create();
        client.addFilter(new LoggingFilter(System.out));
        return client;
    }

    private void getPizzas() {

        List<Pizza> list = createClient().resource("http://10.10.36.64:8080/pizzashop/api/rest/pizzas")
                .get(new GenericType<List<Pizza>>() {
                });

        for (Pizza p : list) {
            System.out.println("Pizza: " + p.getName() + " \t| " + p.getPrice() + " Fr.");
        }
    }

    private void getOrders() {

        List<Bestellung> list = createClient().resource("http://10.10.36.64:8080/pizzashop/api/rest/orders")
                .get(new GenericType<List<Bestellung>>() {
                });

        for (Bestellung b : list) {
            System.out.println("Order " + b.getId() + ": " + b.getAnzahl() + "x " + b.getPizza() + ", " + b.getVorname() + " " + b.getName() + ", " + b.getStrasse() + " " + b.getNummer() + ", " + b.getOrt() + " | " + b.getTel());
        }
    }

    private void testVoid() {

        try {

            Client client = createClient();

            WebResource webResource = client
                    .resource("http://l10.10.36.64:8080/pizzashop/api/rest/pizzas");

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);

            System.out.println("Output from Server .... \n");
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    private void createPizza(String name, float price) {

        Client client;
        WebResource webResource;
        String baseuri = "http://10.10.36.64:8080/pizzashop/api/rest/pizzas/" + name + "/" + price;
        ClientResponse response;
        String output = null;

        try {

            client = createClient();
            String input = "{\"name\":\"price\"}";
            webResource = client.resource(baseuri);

            // implement POST data

            response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOrder(String pizza, String anzahl, String name, String vorname, String strasse, String nummer, String ort, String telefon) {

        Client client;
        WebResource webResource;
        String baseuri = "http://10.10.36.64:8080/pizzashop/api/rest/orders/" + pizza + "/" + anzahl + "/" + name + "/" + vorname + "/" + strasse + "/" + nummer + "/" + ort + "/" + telefon;
        ClientResponse response;
        String output = null;

        try {

            client = createClient();
            String input = "{\"pizza\":\"anzahl\",\"name\",\"vorname\",\"strasse\",\"nummer\",\"ort\",\"telefon\"}";
            webResource = client.resource(baseuri);

            // implement POST data

            response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
