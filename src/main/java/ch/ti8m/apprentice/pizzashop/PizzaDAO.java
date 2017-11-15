package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Bestellung;
import ch.ti8m.apprentice.pizzashop.model.Pizza;
import ch.ti8m.apprentice.pizzashop.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


/**
 * DAO (Data Access Object) for Pizza
 * Created by gol on 24.01.2017.
 */
public class PizzaDAO {

    private static Logger log = LoggerFactory.getLogger(PizzaDAO.class);

    @Resource(name = "jdbc/pizzakurier")
    private DataSource ds;

    public PizzaDAO() {
        log.debug("initializing PizzaDao");
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/pizzakurier");
        } catch (NamingException e) {
            throw new RuntimeException("Datasource not found", e);
        }
    }

    /**
     * List all {@link Pizza}s in the database
     *
     * @return list of {@link Pizza}s
     * @throws Exception ex when loading from the database failed
     */
    public List<Pizza> getPizzas() throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                List<Pizza> pizzas = new LinkedList<>();

                String sql = "SELECT id, name, price, imagepath FROM pizza;";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    Pizza pizza = new Pizza();
                    pizza.setId(rs.getInt("id"));
                    pizza.setName(rs.getString("name"));
                    pizza.setPrice(rs.getFloat("price"));
                    pizza.setImagepath(rs.getString("imagepath"));
                    pizzas.add(pizza);
                }

                return pizzas;

            }
        }
    }

    /**
     * List all {@link Bestellung}s in the database
     *
     * @return list of {@link Bestellung}s
     * @throws Exception ex when loading from the database failed
     */
    public List<Bestellung> getOrders() throws Exception {

        try (Connection connection = ds.getConnection()) {
            log.info("connection: {}", connection);

            try (Statement statement = connection.createStatement()) {

                List<Bestellung> bestellungen = new LinkedList<>();

                String sql = "SELECT id, pizza, anzahl, name, vorname, strasse, nummer, ort, tel FROM ordering;";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    Bestellung bestellung = new Bestellung(rs.getInt("id"), rs.getString("pizza"), rs.getString("anzahl"), rs.getString("name"), rs.getString("vorname"), rs.getString("strasse"), rs.getString("nummer"), rs.getString("ort"), rs.getString("tel"));
                    bestellungen.add(bestellung);
                }

                return bestellungen;

            }
        }
    }

    /**
     * Create new {@link Pizza} on the database
     *
     * @param pizza {@link Pizza} object
     * @throws Exception ex when creating pizza failed
     */
    public void createPizza(Pizza pizza) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "insert into pizza (name, price) values ('" + pizza.getName() + "', " + pizza.getPrice() + " )";
                log.debug("SQL: " + sql);
                statement.executeUpdate(sql);
            }
        }
    }


    /**
     * Create new {@link Bestellung} on the database
     *
     * @param bestellung {@link Bestellung} object
     * @throws Exception ex when creating order failed
     */
    public void createOrder(Bestellung bestellung) throws Exception {

        try (Connection connection = ds.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                String sql = "insert into ordering (pizza, anzahl, name, vorname, strasse, nummer, ort, tel) values ('" + bestellung.getPizza() + "', " + bestellung.getAnzahl() + ", '" + bestellung.getName() + "' , '" + bestellung.getVorname() + "', '" + bestellung.getStrasse() + "', '" + bestellung.getNummer() + "', '" + bestellung.getOrt() + "', '" + bestellung.getTel() + "')";
                statement.executeUpdate(sql);
            }
        }
    }

    /**
     * Find {@link Pizza} in the database using its ID
     *
     * @return {@link Pizza} with matching ID
     * @throws Exception ex when loading from the database failed
     */
    public Pizza findById(int id) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "select id, name, price from pizza where id=" + id + ";";
                ResultSet rs = statement.executeQuery(sql);

                Pizza pizza = new Pizza();
                pizza.setId(rs.getInt("id"));
                pizza.setName(rs.getString("name"));
                pizza.setPrice(rs.getFloat("price"));
                return pizza;
            }
        }
    }

    /**
     * Find {@link Pizza} in the database using its name
     *
     * @param name name of the {@link Pizza}
     * @return {@link Pizza} with matching name
     * @throws Exception ex when loading from the database failed
     */
    public Pizza findByName(String name) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "select id, name, price from pizza where name=" + name + ";";
                ResultSet rs = statement.executeQuery(sql);

                Pizza pizza = new Pizza();
                pizza.setId(rs.getInt("id"));
                pizza.setName(rs.getString("name"));
                pizza.setPrice(rs.getFloat("price"));

                return pizza;
            }
        }

    }

    /**
     * Update {@link Pizza} in the database
     *
     * @param pizza {@link Pizza} object
     * @throws Exception ex when updating database failed
     */
    public void update(Pizza pizza) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "update pizza set name='" + pizza.getName()
                        + "' where name='" + pizza.getName() + ";";
                statement.executeUpdate(sql);
            }
        }
    }

    /**
     * Delete {@link Pizza} in the database
     *
     * @param pizza {@link Pizza} object
     * @throws Exception ex when deleting database failed
     */
    public void delete(Pizza pizza) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "delete from pizza where name='" + pizza.getName() + ";";
                ResultSet rs = statement.executeQuery(sql);
            }
        }
    }

    /**
     * Delete {@link Pizza} in the database using its ID
     *
     * @param index ID of the {@link Pizza}
     * @throws Exception ex when deleting {@link Pizza} from database failed
     */
    public void deletePizzaById(int index) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "delete from pizza where id=" + index + ";";
                statement.executeUpdate(sql);

            }
        }
    }

    /**
     * Delete {@link Bestellung} in the database using its ID
     *
     * @param index ID of the {@link Bestellung}
     * @throws Exception ex when deleting {@link Bestellung} from database failed
     */
    public void deleteOrderById(int index) throws Exception {

        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "delete from ordering where id=" + index + ";";
                statement.executeUpdate(sql);

            }
        }
    }


    public void createUser(User user) throws Exception {
        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "INSERT INTO users(email, password) values('" + user.getEmail() + "', '" + user.getPassword() + "');";
                statement.executeUpdate(sql);

            }
        }
    }


    public Boolean isUserExisting(User user) throws Exception {

        Boolean temp = false;
        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "select email from users where email='" + user.getEmail() + "' and password='" + user.getPassword() + "';";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    String email = rs.getString("email");
                    if (!email.equals("")) {
                        temp = true;
                    }
                }
            }
        }
        return temp;
    }
}
