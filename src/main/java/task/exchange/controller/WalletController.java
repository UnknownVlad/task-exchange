package task.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task.exchange.model.User;
import task.exchange.model.Wallet;
import task.exchange.sql.UserDAO;
import task.exchange.sql.WalletDAO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletDAO walletDAO;
    @Autowired
    private UserDAO userDAO;


    @GetMapping
    public Map<String, String> getWallet(@RequestBody Map<String, String> form) {
        Wallet wallet = walletDAO.findWallet(form.get("secret_key"));

        if(wallet == null)
            return Map.of("state: ", "this wallet doesn't exist");

        User user = userDAO.findUser("secret_key", "'"+form.get("secret_key")+"'");
        if(!user.getRole().equals("user"))
            return Map.of("state: ", "you doesn't have permission");

        return Map.of(
                "BTC_wallet: ", Double.toString(wallet.getBTC()),
                "TON_wallet: ", Double.toString(wallet.getTON()),
                "RUB_wallet", Double.toString(wallet.getRUB())
                );
    }

    @PostMapping
    public Map<String, String> addMoneyWallet(@RequestBody Map<String, String> form){

        Wallet wallet = walletDAO.findWallet(form.get("secret_key"));
        if(wallet == null)
            return Map.of("state: ", "this wallet doesn't exist");

        User user = userDAO.findUser("secret_key", "'"+form.get("secret_key")+"'");
        if(!user.getRole().equals("user"))
            return Map.of("state: ", "you doesn't have permission");
        
        Map<String, String> answer = new HashMap<>();
        wallet = walletDAO.addCurrencieWallet(form);
        if(form.containsKey("BTC_wallet")){
            answer.put("BTC_wallet: ", Double.toString(wallet.getBTC()));
        }
        if(form.containsKey("TON_wallet")){
            answer.put("TON_wallet: ", Double.toString(wallet.getTON()));
        }
        if(form.containsKey("RUB_wallet")){
            answer.put("RUB_wallet: ", Double.toString(wallet.getRUB()));
        }
        return answer;
    }
}
