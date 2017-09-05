package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Bestellung;
import ch.ti8m.apprentice.pizzashop.model.Pizza;
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

    Logger log = LoggerFactory.getLogger(PizzaDAO.class);

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

    public List<Pizza> getPizzas() throws Exception {

        try (Connection connection = ds.getConnection()) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                List<Pizza> pizzas = new LinkedList<>();

                String sql = "SELECT id, name, price FROM pizza;";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    Pizza pizza = new Pizza();
                    pizza.setId(rs.getInt("id"));
                    pizza.setName(rs.getString("name"));
                    pizza.setPrice(rs.getFloat("price"));
                    pizzas.add(pizza);
                }

                return pizzas;

            }
        }
    }


    public List<Bestellung> getOrders() throws Exception {

        try (Connection connection = ds.getConnection()) {
            log.info("connection: {}", connection);
            // create a statement
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


    public void createPizza(Pizza pizza) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "insert into pizza (name, price) values ('" + pizza.getName() + "', " + pizza.getPrice() + " )";
                System.out.println("SQL: " + sql);
                statement.executeUpdate(sql);

            }
        }
    }


    public void createOrder(Bestellung bestellung) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "insert into ordering (pizza, anzahl, name, vorname, strasse, nummer, ort, tel) values ('" + bestellung.getPizza() + "', " + bestellung.getAnzahl() + ", '" + bestellung.getName() + "' , '" + bestellung.getVorname() + "', '" + bestellung.getStrasse() + "', '" + bestellung.getNummer() + "', '" + bestellung.getOrt() + "', '" + bestellung.getTel() + "')";
                statement.executeUpdate(sql);

            }
        }
    }


    public Pizza findById(int id) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            // create a statement
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

    public Pizza findByName(String name) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            // create a statement
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

    public void update(Pizza pizza) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            // create a statement
            try (Statement statement = connection.createStatement()) {

                String sql = "update pizza set name='" + pizza.getName()
                        + "' where name='" + pizza.getName() + ";";
                statement.executeUpdate(sql);

            }
        }
    }

    public void delete(Pizza pizza) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "delete from pizza where name='" + pizza.getName() + ";";
                ResultSet rs = statement.executeQuery(sql);

            }
        }
    }

    public void deletePizzaById(int index) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "delete from pizza where id=" + index + ";";
                statement.executeUpdate(sql);

            }
        }
    }

    public void deleteOrderById(int index) throws Exception {

        // create a connection
        // hint: try-with-resources (since Java 7)
        try (Connection connection = ds.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                String sql = "delete from ordering where id=" + index + ";";
                statement.executeUpdate(sql);

            }
        }
    }


}
