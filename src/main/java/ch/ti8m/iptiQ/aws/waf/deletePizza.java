package ch.ti8m.iptiQ.aws.waf;

import ch.ti8m.iptiQ.aws.waf.util.TemplateUtil;

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
@WebServlet(name = "deletePizza", urlPatterns = "/delete")
public class deletePizza extends HttpServlet {

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printRequest(request);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            printRequest(request);
            response.setCharacterEncoding("UTF-8");
            PizzaDAO dao = new PizzaDAO();
            dao.deletePizzaById(1);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("pizzas", dao.getPizzas());

            TemplateUtil.process(response, "DeletePizza.template", dataModel);

//            char[] lastByte = DataStore.getInstance().getParameters().get(0).toCharArray();
//            int index= lastByte[lastByte.length];
//
//            DataStore.getInstance().getPizzas().remove(index);




        } catch (Exception e) {
            e.printStackTrace();
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
