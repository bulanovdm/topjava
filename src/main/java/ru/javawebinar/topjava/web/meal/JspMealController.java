package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("create")
    public String create(Model model) {
        var meal = new Meal(DateTimeUtil.timeNow(), "New meal", 0);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("update")
    public String update(@RequestParam int id, Model model) {
        var meal = get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("delete")
    public String deleteOne(@RequestParam int id) {
        delete(id);
        return "redirect:/meals";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("filtered")
    public String getFiltered(Model model,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping
    public String edit(
            @RequestParam(required = false) Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam String description,
            @RequestParam int calories) {
        var meal = new Meal(dateTime, description, calories);
        if (ObjectUtils.isEmpty(id)) {
            create(meal);
        } else {
            update(meal, id);
        }
        return "redirect:/meals";
    }
}
