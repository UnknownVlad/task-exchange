package task.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task.exchange.model.Course;
import task.exchange.model.User;
import task.exchange.model.Wallet;
import task.exchange.sql.CourseDAO;
import task.exchange.sql.UserDAO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private UserDAO userDAO;
    @GetMapping
    public Map<String, String> getCourse(@RequestBody Map<String, String> form) {
        Course course = courseDAO.findCourse(form.get("currency"));

        User user = userDAO.findUser("secret_key", "'"+form.get("secret_key")+"'");
        if(user == null)
            return Map.of("state: ", "user doesn't exist");

        if(!user.getRole().equals("user") && !user.getRole().equals("admin"))
            return Map.of("state: ", "you doesn't have permission");

        Map<String, String> answer = new HashMap<>();

        answer.put("TON: ", Double.toString(course.getTON()));
        answer.put("BTC: ", Double.toString(course.getBTC()));
        answer.put("RUB: ", Double.toString(course.getRUB()));

        if(form.containsValue("TON"))
            answer.remove("TON: ");
        if(form.containsValue("RUB"))
            answer.remove("RUB: ");
        if(form.containsValue("BTC"))
            answer.remove("BTC: ");

        return answer;
    }
    @PostMapping
    public Map<String, String> changeCourse(@RequestBody Map<String, String> form) {

        User user = userDAO.findUser("secret_key", "'"+form.get("secret_key")+"'");
        
        if(user == null)
            return Map.of("state: ", "user doesn't exist");


        if(user.getRole().equals("user"))
            return Map.of("state: ", "you doesn't have permission");

        Course course = courseDAO.changeCourse(form);



        Map<String, String > answer = new HashMap<>();
        answer.put("TON: ", Double.toString(course.getTON()));
        answer.put("BTC: ", Double.toString(course.getBTC()));
        answer.put("RUB: ", Double.toString(course.getRUB()));

        if(form.containsValue("TON"))
            answer.remove("TON: ");
        if(form.containsValue("RUB"))
            answer.remove("RUB: ");
        if(form.containsValue("BTC"))
            answer.remove("BTC: ");

        return answer;
    }

}
