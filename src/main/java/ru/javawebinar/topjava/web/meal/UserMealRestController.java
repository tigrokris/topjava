package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMealService service;
    private int userId = LoggedUser.id();
    public List<UserMeal> getAll() {
        LOG.info("getAll");
        return service.getAll(userId);
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id,userId);
    }

    public UserMeal create(UserMeal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return service.save(meal,userId);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id,userId);
    }

    public void update(UserMeal meal, int id) {
        meal.setId(id);
        LOG.info("update " + meal);
        service.update(meal,userId);
    }

}
