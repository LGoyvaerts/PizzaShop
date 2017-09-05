package ch.ti8m.apprentice.pizzashop.model;

/**
 * Created by gol on 23.05.2017.
 */
public class Bestellung {

    private int id;
    private String pizza;
    private String anzahl;
    private String name;
    private String vorname;
    private String strasse;
    private String nummer;
    private String ort;
    private String tel;

    /**
     * Model to use when creating a {@link Bestellung}
     * @param pizza name of the pizza
     * @param anzahl amount of {@link Pizza}s to order
     * @param name surname of the customer
     * @param vorname firstname of the customer
     * @param strasse street of the customer
     * @param nummer streetnumber of the customer
     * @param ort livingplace of the customer
     * @param tel phonenumber of the customer
     */
    public Bestellung(String pizza, String anzahl, String name, String vorname, String strasse, String nummer, String ort, String tel) {
        this.pizza = pizza;
        this.anzahl = anzahl;
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.nummer = nummer;
        this.ort = ort;
        this.tel = tel;
    }

    /**
     *
     * @param id id of the {@link Bestellung}
     * @param pizza name of the pizza
     * @param anzahl amount of pizzas to order
     * @param name surname of the customer
     * @param vorname firstname of the customer
     * @param strasse street of the customer
     * @param nummer streetnumber of the customer
     * @param ort livingplace of the customer
     * @param tel phonenumber of the customer
     */
    public Bestellung(int id, String pizza, String anzahl, String name, String vorname, String strasse, String nummer, String ort, String tel) {
        this.id = id;
        this.pizza = pizza;
        this.anzahl = anzahl;
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.nummer = nummer;
        this.ort = ort;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }

    public String getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(String anzahl) {
        this.anzahl = anzahl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
