package vn.edu.store.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import vn.edu.store.model.Account;
import vn.edu.store.repository.AccountRepository;

import java.util.List;

@ApplicationScoped
public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}