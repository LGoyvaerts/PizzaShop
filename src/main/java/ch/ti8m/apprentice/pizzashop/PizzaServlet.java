package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Pizza;
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
@WebServlet(name = "PizzaServlet", urlPatterns = "/pizza")
public class PizzaServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    // Database credentials
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "pizzakurier";
    private static final String DB_USER = "lorris";
    private static final String DB_PASS = "L2o0g0o1";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";

    private AtomicInteger requestCounter;

    static String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printRequest(request);
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
                printRequest(request);

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
                printRequest(request);

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
            printRequest(request);
            response.setCharacterEncoding("UTF-8");
            PizzaDAO dao = new PizzaDAO();


            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("pizzas", dao.getPizzas());

            TemplateUtil.process(response, "PizzaServletTemplateGet.template", dataModel);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printRequest(req);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printRequest(req);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printRequest(req);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printRequest(req);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printRequest(req);
    }

    @Override
    public void init() throws ServletException {
        requestCounter = new AtomicInteger();
    }

    private void printRequest(HttpServletRequest httpRequest) {
        StringBuffer sb = new StringBuffer();
        sb.append("--------------------------\n");
        sb.append("requestNumber=" + requestCounter.addAndGet(1) + "\n");
        sb.append("method=" + httpRequest.getMethod() + "\n");
        sb.append("uri=" + httpRequest.getRequestURI() + "\n");
        sb.append("Headers:\n");

        Enumeration headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            sb.append("\t" + headerName + "=" + httpRequest.getHeader(headerName) + "\n");
        }

        sb.append("Parameters:\n");

        Enumeration params = httpRequest.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = (String) params.nextElement();
            sb.append("\t" + paramName + "=" + httpRequest.getParameter(paramName) + "\n");
        }

        sb.append("Raw data:\n");
        sb.append(extractPostRequestBody(httpRequest));
        System.out.println(sb.toString());


    }


}
