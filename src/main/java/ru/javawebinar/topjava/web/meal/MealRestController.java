package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(String startDate, String endDate, String startTime, String endTime) {
        log.info("getFiltered");
        List<Meal> mealList;
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            LocalDate startDt = LocalDate.parse(startDate);
            LocalDate endDt = LocalDate.parse(endDate);
            mealList = service.getFiltered(authUserId(), startDt, endDt);
        }
        else {
            mealList = service.getAll(authUserId());
        }

        LocalTime startTm = startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime endTm = endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
//        List<Meal> mealList = service.getFiltered(authUserId(), startDate, endDate);
        return MealsUtil.getFilteredWithExcess(mealList, SecurityUtil.authUserCaloriesPerDay(), startTm, endTm);
    }


    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(authUserId());
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        meal.setUserId(authUserId());
        service.update(authUserId(), meal);
    }
}