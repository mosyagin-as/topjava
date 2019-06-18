package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.*;

public class SpringMain {
    public static void main(String[] args) {
        Map<Integer, List<Integer>> ggg = new HashMap<>();

        ggg.merge(1, Collections.singletonList(100), (oldVal, newVal) -> {
            oldVal.addAll(newVal);
            return oldVal;
        });

        ggg.merge(1, Collections.singletonList(111), (oldVal, newVal) -> {
            oldVal.addAll(newVal);
            return oldVal;
        });


        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(null, 1, LocalDateTime.now(), "Hello", 1000));
        }
    }
}
