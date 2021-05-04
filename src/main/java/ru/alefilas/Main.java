package ru.alefilas;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.alefilas.config.JpaConfig;
import ru.alefilas.menu.MenuRunner;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        MenuRunner runner = context.getBean("menuRunner", MenuRunner.class);
        runner.run();
    }
}
