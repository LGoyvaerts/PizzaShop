package ch.ti8m.iptiQ.aws.waf;

import ch.ti8m.iptiQ.aws.waf.model.Pizza;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gol on 24.05.2017.
 */
public class HelloWorld {

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorld.class, "/templates");

        Template template = config.getTemplate("HelloWorld.template");

            DataStore.getInstance().getPizzas().add(new Pizza("Margerita", 12));
        DataStore.getInstance().getPizzas().add(new Pizza("Funghi", 13));
        DataStore.getInstance().getPizzas().add(new Pizza("Hawaii", 14));


        List<Pizza> pizzas = DataStore.getInstance().getPizzas();

        Map<String, Object> dataModel = new HashMap<>();

        dataModel.put("pizzas", DataStore.getInstance().getPizzas());

        Writer out = new OutputStreamWriter(System.out);
        template.process(dataModel, out);
        out.flush();

    }
}
