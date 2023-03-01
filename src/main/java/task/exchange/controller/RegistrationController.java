package task.exchange.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task.exchange.model.User;
import task.exchange.model.Wallet;
import task.exchange.sql.UserDAO;
import task.exchange.sql.WalletDAO;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private WalletDAO walletDAO;


    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> form) throws NoSuchAlgorithmException {

        if (userDAO.findUser("username", "'"+ form.get("username")+"'") != null
        || userDAO.findUser("email", "'"+ form.get("email")+"'") != null){
            return Map.of("state: ", "registration is faled. This user already exist");
        }

        User user = new User(
                form.get("username"),
                form.get("email"),
                generateKey(),
                "user"
        );
        userDAO.saveUser(user);
        walletDAO.saveWallet(new Wallet(user.getSecret_key()));
        return Map.of("secret_key: ", user.getSecret_key());
    }

    private String generateKey(){
        return UUID.randomUUID().toString();
    }
}
