package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Bestellung;
import ch.ti8m.apprentice.pizzashop.util.PrintRequest;
import ch.ti8m.apprentice.pizzashop.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gol on 05.09.2017.
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PizzaDAO dao = new PizzaDAO();
        String pizza = request.getParameter("pizzaList");
        String anzahl = request.getParameter("anzahl");
        String name = request.getParameter("name");
        String vorname = request.getParameter("vorname");
        String strasse = request.getParameter("strasse");
        String nummer = request.getParameter("nummer");
        String ort = request.getParameter("ort");
        String telefon = request.getParameter("telefon");
        response.setCharacterEncoding("UTF-8");
        Bestellung bestellung = new Bestellung(pizza, anzahl, name, vorname, strasse, nummer, ort, telefon);

        if (!isEmpty(pizza) && !isEmpty(anzahl) && !isEmpty(name) && !isEmpty(vorname) && !isEmpty(strasse) && !isEmpty(nummer) && !isEmpty(ort) && !isEmpty(telefon)) {

            try {
                dao.createOrder(bestellung);
                PrintRequest.print(request);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("bestellungen", dao.getOrders());

                TemplateUtil.process(response, "OrderServletTemplatePost.template", dataModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                PrintRequest.print(request);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("bestellungen", dao.getOrders());

                TemplateUtil.process(response, "Failure.template", dataModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintRequest.print(request);
            response.setCharacterEncoding("UTF-8");
            PizzaDAO dao = new PizzaDAO();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("pizzas", dao.getPizzas());

            TemplateUtil.process(response, "OrderServletTemplateGet.template", dataModel);
        } catch (Exception e) {
            e.getMessage();
        }

    }


}
