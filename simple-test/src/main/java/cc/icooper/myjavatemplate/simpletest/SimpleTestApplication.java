package cc.icooper.myjavatemplate.simpletest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cc.icooper.myjavatemplate.simpletest", "cc.icooper.majavatemplate.core"})
public class SimpleTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTestApplication.class, args);
    }

}
