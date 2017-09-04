package ch.ti8m.iptiQ.aws.waf.util;

import ch.ti8m.iptiQ.aws.waf.DataStore;
import ch.ti8m.iptiQ.aws.waf.overviewServlet;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gol on 29.05.2017.
 */
public final class TemplateUtil {

    private TemplateUtil(){

    }

    public static void process(HttpServletResponse response, String templateName, Object dataModel) throws IOException, TemplateException{
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Configuration config = new Configuration();
        config.setClassForTemplateLoading(TemplateUtil.class, "/templates");
        Template template = config.getTemplate(templateName);

        template.process(dataModel, out);
        out.flush();
    }
}