package ch.ti8m.apprentice.pizzashop.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gol on 29.05.2017.
 */
public final class PrintRequest {

    private PrintRequest() {

    }

    private static String extractPostRequestBody(HttpServletRequest request) throws Exception{
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

    /**
     * Prints out useful information about the actual {@link HttpServletRequest} on the Server
     *
     * @param httpRequest is needed to execute
     * @throws Exception ex when printing out request failed
     */
    public static void print(HttpServletRequest httpRequest) throws Exception {
        AtomicInteger requestCounter = new AtomicInteger();
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