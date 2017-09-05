package ch.ti8m.apprentice.pizzashop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by gol on 23.05.2017.
 */
@XmlRootElement(name = "pizza")
public class Pizza {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String name;
    private float price;
    private int id;

    public Pizza() {
    }

    /**
     * Model to use when creating a {@link Pizza}
     * @param name name of the {@link Pizza}
     * @param price price of the {@link Pizza}
     */
    public Pizza(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "price")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
