package task.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.exchange.model.User;
import task.exchange.model.Wallet;
import task.exchange.sql.UserDAO;
import task.exchange.sql.WalletDAO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    @Autowired
    private WalletDAO walletDAO;
    @Autowired
    private UserDAO userDAO;
    @PostMapping
    public Map<String, String> takeOfMoneyWallet(@RequestBody Map<String, String> form){

        Wallet wallet = walletDAO.findWallet(form.get("secret_key"));
        if(wallet == null){
            return Map.of("state: ", "this wallet doesn't exist");
        }

        User user = userDAO.findUser("secret_key", "'"+form.get("secret_key")+"'");
        if(!user.getRole().equals("user"))
            return Map.of("state: ", "you doesn't have permission");

        if(!walletDAO.haveCurrencie(form))
            return Map.of("state: ", "you don't heve enough money");
        Map<String, String> answer = new HashMap<>();
        wallet = walletDAO.sendCurrencieWallet(form);
        if(form.containsValue("BTC")){
            answer.put("BTC_wallet: ", Double.toString(wallet.getBTC()));
        }
        if(form.containsValue("TON")){
            answer.put("TON_wallet: ", Double.toString(wallet.getTON()));
        }
        if(form.containsValue("RUB")){
            answer.put("RUB_wallet: ", Double.toString(wallet.getRUB()));
        }
        return answer;
    }
}
