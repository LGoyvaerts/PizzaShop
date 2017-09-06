package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Pizza;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gol on 05.09.2017.
 */
@WebServlet(name = "PizzaServlet", urlPatterns = "/pizza")
public class PizzaServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String preis = request.getParameter("preis");
        PizzaDAO dao = new PizzaDAO();

        String temp = request.getParameter("nummer");


        if (!isEmpty(name) && !isEmpty(preis)) {

            try {
                Pizza pizza = new Pizza(name, Float.parseFloat(preis));
                dao.createPizza(pizza);
                PrintRequest.print(request);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("pizzas", dao.getPizzas());

                TemplateUtil.process(response, "PizzaServletTemplatePost.template", dataModel);
            } catch (Exception e) {
                throw new ServletException("Failed to insert Pizza: " + e.getMessage(), e);
            }
        } else if (temp.trim().length() > 0) {
            try {
                Integer nummer = Integer.parseInt(temp);
                dao.deletePizzaById(nummer);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("bestellungen", dao.getOrders());

                TemplateUtil.process(response, "DeletePizza.template", dataModel);
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            try {
                PrintRequest.print(request);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("pizzas", dao.getPizzas());

                TemplateUtil.process(response, "Failure.template", dataModel);
            } catch (Exception e) {
                throw new ServletException(e);
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

            TemplateUtil.process(response, "PizzaServletTemplateGet.template", dataModel);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
