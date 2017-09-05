package ch.ti8m.apprentice.pizzashop;

import ch.ti8m.apprentice.pizzashop.model.Bestellung;
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
 * Created by het on 27.03.2017.
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(getClass());

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
                printRequest(request);

                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("bestellungen", dao.getOrders());

                TemplateUtil.process(response, "OrderServletTemplatePost.template", dataModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                printRequest(request);

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
            printRequest(request);
            response.setCharacterEncoding("UTF-8");
            PizzaDAO dao = new PizzaDAO();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("pizzas", dao.getPizzas());

            TemplateUtil.process(response, "OrderServletTemplateGet.template", dataModel);
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
