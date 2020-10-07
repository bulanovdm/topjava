package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealMemory;
import ru.javawebinar.topjava.data.MealRepo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static MealRepo data;

    @Override
    public void init() {
        data = new MealMemory();
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        data.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action") == null ? "default" : req.getParameter("action");
        long id = req.getParameter("id") == null ? 0 : Long.parseLong(req.getParameter("id"));
        switch (action) {
            case "create":
                req.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0));
                req.getRequestDispatcher("/edit.jsp").forward(req, resp);
                break;
            case "update":
                req.setAttribute("meal", data.getById(id));
                req.getRequestDispatcher("/edit.jsp").forward(req, resp);
                break;
            case "delete":
                data.removeById(id);
                resp.sendRedirect("meals");
                break;
            default:
                req.setAttribute("mealList", MealsUtil.filteredByStreams(data.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.EXCESS));
                getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        long id = Long.parseLong(req.getParameter("id"));
        Meal current = createMeal(req);
        if (id > 0) {
            current.setId(id);
            data.edit(current);
        } else {
            data.add(current);
        }
        resp.sendRedirect("meals");
    }

    private Meal createMeal(HttpServletRequest req) {
        LocalDateTime datetime = LocalDateTime.parse(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        return new Meal(datetime, description, calories);
    }
}
