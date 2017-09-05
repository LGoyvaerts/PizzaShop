package webservice.rest;

import ch.ti8m.apprentice.pizzashop.PizzaDAO;
import ch.ti8m.apprentice.pizzashop.model.Bestellung;
import ch.ti8m.apprentice.pizzashop.model.Pizza;
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
 * Pizza JAX-RS rest web service
 */
@RequestScoped
@Path("/example")
public class PizzaEndpoint {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * List {@link Pizza}s
     * @return list of {@link Pizza}s in the database
     * @throws Exception ex when loading from database
     */
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

    /**
     * List {@link Bestellung}s
     * @return list of {@link Bestellung}s in the database
     * @throws Exception ex when loading from database
     */
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

    /**
     * Print out receipt over all {@link Bestellung}s
     * @return receipt over all {@link Bestellung}s from the database
     * @throws Exception ex when loading from the database
     */
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

    /**
     * List {@link Pizza}s using getters
     * @return list of {@link Pizza}s in database
     * @throws Exception ex when loading from database
     */
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

    /**
     * List {@link Pizza} using ID
     * @param id ID of the {@link Pizza}
     * @return {@link Pizza} with matching ID in database
     * @throws Exception ex when loading from database
     */
    @GET
    @Path("/pizza/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza getPizzaById(@PathParam("id") Integer id) throws Exception {

        PizzaDAO dao = new PizzaDAO();
        Pizza result = dao.findById(id);

        log.debug("Find pizza by ID: {}", result);

        return result;
    }

    /**
     * Create {@link Pizza} using name and price
     * @param name name of the {@link Pizza}
     * @param price price of the {@link Pizza}
     * @return created {@link Pizza} in database
     * @throws Exception ex when creating {@link Pizza}za from database
     */
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

    /**
     * Create order using {@link Pizza}, anzahl, surname, firstname, street, streetnumber, livingplace and phonenumber
     * @param pizza name of the {@link Pizza} (reference)
     * @param anzahl amount of {@link Pizza}s
     * @param name name of the {@link Pizza}
     * @param vorname firstname of the customer
     * @param strasse street of the customer
     * @param nummer streetnumber of the customer
     * @param ort livingplace of the customer
     * @param telefon phonenumber of the customer
     * @return created {@link Bestellung} in database
     * @throws Exception ex when creating {@link Bestellung}
     */
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

    /**
     * Delete {@link Pizza} using its ID
     * @param id ID of the {@link Pizza}
     * @return deleted {@link Pizza} in database
     * @throws Exception ex when deleting {@link Pizza} from database
     */
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

    /**
     * Get Info about the useragent
     * @param headers {@link HttpHeaders} of the call
     * @return information about the useragent
     */
    @GET
    @Path("/info/useragent")
    @Produces(MediaType.APPLICATION_JSON)
    public String userAgent(@Context HttpHeaders headers) {


        String agent = headers.getRequestHeader("user-agent").get(0);

        log.debug("User-Agent called: {}", agent);

        return agent;
    }

    /**
     * Get IPv4-Adress of the Client
     * @return IPv4-Adress of the Client
     * @throws Exception ex when reading information
     */
    @GET
    @Path("/info/ip")
    @Produces(MediaType.APPLICATION_JSON)
    public String ipAdress() throws Exception {


        String result = Inet4Address.getLocalHost().getHostAddress();

        log.debug("IP-Adress called: {}", result);

        return result;
    }


}
