package ch.ti8m.apprentice.pizzashop;

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
 * Created by het on 27.03.2017.
 */
@WebServlet(name = "OverviewServlet", urlPatterns = "/")
public class OverviewServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintRequest.print(request);
            response.setCharacterEncoding("UTF-8");
            PizzaDAO dao = new PizzaDAO();
            String temp = request.getParameter("nummer");


            Integer index = Integer.parseInt(temp);
            log.debug("number is: {}", index);
            dao.deleteOrderById(index);

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("bestellungen", dao.getOrders());

            TemplateUtil.process(response, "DeleteOrder.template", dataModel);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception while POST", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintRequest.print(request);
            response.setCharacterEncoding("UTF-8");
            PizzaDAO dao = new PizzaDAO();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("bestellungen", dao.getOrders());
            log.info("test info");

            TemplateUtil.process(response, "OverviewServletTemplateGet.template", dataModel);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            log.error("Exception wile GET", e);
        }
    }


}
