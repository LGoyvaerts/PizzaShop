package webservice.rest;

import ch.ti8m.iptiQ.aws.waf.PizzaDAO;
import ch.ti8m.iptiQ.aws.waf.model.Bestellung;
import ch.ti8m.iptiQ.aws.waf.model.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.net.Inet4Address;
import java.text.Collator;
import java.util.*;

/**
 * Example JAX-RS rest web service
 */
@RequestScoped
@Path("/example")
public class ExampleEndpoint {
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * List system properties and return them as JSON object
     */
    @GET
    @Path("/properties")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getSystemProperties() {

        Map<String, String> result = new LinkedHashMap<>();

        Properties systemProperties = System.getProperties();
        List<String> keys = new LinkedList<>(systemProperties.stringPropertyNames());
        Collections.sort(keys, Collator.getInstance());

        for (String key : keys) {
            String value = systemProperties.getProperty(key);
            result.put(key, value);
        }

        return result;
    }

    @GET
    @Path("/pizzas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pizza> getPizza() throws Exception {

        PizzaDAO dao = new PizzaDAO();
        List<Pizza> result = new LinkedList<>();

        for (Pizza pizza : dao.getPizzas()) {
            result.add(pizza);
        }

        log.debug("List pizzas: list {}", result);

        return result;
    }

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bestellung> getOrders() throws Exception {

        PizzaDAO dao = new PizzaDAO();
        List<Bestellung> result = new LinkedList<>();

        for (Bestellung bestellung : dao.getOrders()) {

            result.add(bestellung);
        }

        log.debug("List orders: list {}", result);

        return result;
    }

    @GET
    @Path("/orders/receipt")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, String> getReceipt() throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Map<Integer, String> result = new LinkedHashMap<>();

        for (Bestellung bestellung : dao.getOrders()) {

            String pizza = bestellung.getPizza();
            Float total = null;
            String pizzza = null;
            Float pizzaPrice = null;

            for (Pizza x : dao.getPizzas()) {
                if (pizza.equals(x.getName())) {
                    total = Float.parseFloat(bestellung.getAnzahl()) * x.getPrice();
                    pizzza = x.getName();
                    pizzaPrice = x.getPrice();
                }
            }
            result.put(bestellung.getId(), bestellung.getAnzahl() + "x " + pizzza + " (" + pizzaPrice + ") = " + total);
        }

        log.debug("List receipt: list {}", result);

        return result;
    }

    @GET
    @Path("/pizza")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Float> getPizzas() throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Map<String, Float> result = new LinkedHashMap<>();

        for (Pizza pizza : dao.getPizzas()) {
            result.put(pizza.getName(), pizza.getPrice());
        }

        log.debug("List pizzas with getters: {}", result);

        return result;
    }

    @GET
    @Path("/pizza/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza getPizzaById(@PathParam("id") Integer id) throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Pizza result = dao.findById(id);

        log.debug("Find pizza by ID: {}", result);

        return result;
    }

    @POST
    @Path("/pizzas/{name}/{price}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza createPizza(@PathParam("name") String name, @PathParam("price") Float price) throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Pizza result = new Pizza(name, price);

        dao.createPizza(result);

        log.debug("Pizza created: {}", result);

        return result;
    }

    @POST
    @Path("orders/{pizza}/{anzahl}/{name}/{vorname}/{strasse}/{nummer}/{ort}/{telefon}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bestellung createOrder(@PathParam("pizza") String pizza, @PathParam("anzahl") String anzahl, @PathParam("name") String name, @PathParam("vorname") String vorname, @PathParam("strasse") String strasse, @PathParam("nummer") String nummer, @PathParam("ort") String ort, @PathParam("telefon") String telefon) throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Bestellung result = new Bestellung(pizza, anzahl, name, vorname, strasse, nummer, ort, telefon);

        dao.createOrder(result);

        log.debug("Order created: {}", result);

        return result;
    }

    @DELETE
    @Path("/pizzas/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza deletePizza(@PathParam("id") Integer id) throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Pizza result = dao.findById(id);
        dao.deletePizzaById(id);

        log.debug("Pizza deleted: {}", result);

        return result;

    }

    @GET
    @Path("/info/useragent")
    @Produces(MediaType.APPLICATION_JSON)
    public String userAgent(@Context HttpHeaders headers) {


        String agent = headers.getRequestHeader("user-agent").get(0);

        log.debug("User-Agent called: {}", agent);

        return agent;
    }

    @GET
    @Path("/info/ip")
    @Produces(MediaType.APPLICATION_JSON)
    public String ipAdress() throws Exception {


        String result = Inet4Address.getLocalHost().getHostAddress();

        log.debug("IP-Adress called: {}", result);

        return result;
    }


}
