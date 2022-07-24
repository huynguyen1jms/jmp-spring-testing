package com.jmp.springmvc.service;

import com.jmp.springmvc.dao.UserAccountRepository;
import com.jmp.springmvc.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    public void refillingAccount(UserAccount userAccount, long money) {
        long currentMoney = userAccount.getMoney();
        userAccount.setMoney(currentMoney + money);
        userAccountRepository.save(userAccount);
    }
}
