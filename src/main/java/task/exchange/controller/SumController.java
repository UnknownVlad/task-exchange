package task.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.exchange.model.User;
import task.exchange.model.Wallet;
import task.exchange.sql.UserDAO;
import task.exchange.sql.WalletDAO;

import java.util.Map;

@RestController
@RequestMapping("/sum")
public class SumController {
    @Autowired
    private WalletDAO walletDAO;
    @Autowired
    private UserDAO userDAO;
    @GetMapping
    public Map<String, String > getWallet(@RequestBody Map<String, String> form) {
        User user = userDAO.findUser("secret_key", "'"+form.get("secret_key")+"'");
        if(user == null)
            return Map.of("state: ", "user doesn't exist");

        if(user.getRole().equals("user"))
            return Map.of("state: ", "you doesn't have permission");

        double sum = walletDAO.getSum(form.get("currency"));
        return Map.of(form.get("currency")+": ", Double.toString(sum));
    }
}
