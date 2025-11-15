package com.example.dao;

import java.util.Optional;

public interface AccountRepository {
	Optional<Long> findBalance(String userId);//Optional 사용시 NPE 방지
	int debit (String userId,long amount);
	int credit(String userId,long amount);
}
