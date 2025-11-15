package com.example.application;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.dao.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	private final AccountRepository repo;
	
	public AccountServiceImpl(AccountRepository repo) {
		this.repo = repo;
	}
	
	@Override @Transactional
    public void debit(String userId, long amount) {
        int n = repo.debit(userId, amount);
        if (n == 0) throw new ResponseStatusException(//http의 상태코드 지정해줌
                HttpStatus.UNPROCESSABLE_ENTITY, "INSUFFICIENT_BALANCE");
    }
	
	@Override @Transactional
	public void credit(String userId,long amount) {
		repo.credit(userId, amount);
	}
	
	@Override @Transactional
	public void cancelDebit(String userId,long amount) {
		repo.credit(userId, amount);
	}
}
