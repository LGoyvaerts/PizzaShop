package ch.ti8m.iptiQ.aws.waf;

import ch.ti8m.iptiQ.aws.waf.model.Bestellung;
import ch.ti8m.iptiQ.aws.waf.model.Pizza;

import java.util.LinkedList;
import java.util.List;

/**
 * DataStore Singleton (class that has exactly one instance that can be shared)
 * Created by gol on 23.05.2017.
 */
public class DataStore {

    private static final DataStore dataStore = new DataStore();

    private DataStore(){
        // private constructor, no instance creation from outside allowed
    }

    public static DataStore getInstance() {
        return dataStore;
    }


    private List<Pizza> pizzas = new LinkedList<>();
    private List<Bestellung> bestellungen = new LinkedList<>();
    private List<String> parameters = new LinkedList<>();

    public List<String> getParameters() {
        return parameters;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }
}
