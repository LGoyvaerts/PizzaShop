package ch.ti8m.iptiQ.aws.waf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example class
 */
public class Example {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Example() {

        log.info(getMessage());
        try {
            double x = 5 / 0;
            System.out.println(x);
        } catch (Exception ex) {
            log.error("Argh", ex);
        }
    }

    public static void main(String[] args) {
        new Example();
    }

    public String getMessage() {
        return "Hello World";
    }
}
