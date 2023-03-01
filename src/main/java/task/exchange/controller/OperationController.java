package task.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.exchange.model.Course;
import task.exchange.model.User;
import task.exchange.sql.CourseDAO;
import task.exchange.sql.OperationsDao;
import task.exchange.sql.UserDAO;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationsDao operationsDao;
    @Autowired
    private UserDAO userDAO;
    @GetMapping
    public Map<String, String > getCourse(@RequestBody Map<String, String> form) {

        User user = userDAO.findUser("secret_key", form.get("secret_key"));
        if(user == null)
            return Map.of("state: ", "user doesn't exist");

        if(!user.getRole().equals("admin"))
            return Map.of("state: ", "you doesn't have permission");

        int count = operationsDao.countOperations(
                Date.valueOf(form.get("date_from").replace('.','-')),
                Date.valueOf(form.get("date_to").replace('.','-'))
                );

        return Map.of("transaction_count: ", Integer.toString(count));
    }

}
