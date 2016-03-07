package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Дмитрий on 04.03.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(LOG.isDebugEnabled())
            LOG.debug("redirect to mealList");

        request.setAttribute("meals", UserMealsUtil.getFilteredMealsWithExceeded(new ArrayList<>(UserMealsUtil.getUserMeals().values()), LocalTime.MIN,LocalTime.MAX,2000));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
