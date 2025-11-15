package com.example.application;

public interface AccountService {
	void debit(String userId,long amount);
	void credit(String userId,long amount);
	void cancelDebit(String userId,long amount);
}
