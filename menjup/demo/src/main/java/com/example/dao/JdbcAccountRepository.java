package com.example.dao;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcAccountRepository implements AccountRepository{
	private final JdbcTemplate jdbc;
	
	public JdbcAccountRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
    public Optional<Long> findBalance(String userId) {
        return jdbc.query(
            "SELECT balance FROM accounts WHERE user_id = ?",
            rs -> rs.next() ? Optional.of(rs.getLong(1)) : Optional.empty(),
            userId
        );
    }
	
	@Override
	public int debit(String userId,long amount) {
		return jdbc.update(
				"update accounts"
				+ " set balance = balance - ? "
				+ " where user_id = ? and balance >= ? ",
				amount,userId,amount);
	}
	
	@Override 
	public int credit(String userId,long amount) {
		return jdbc.update(
				"update accounts"
				+ " set balance = balance + ? "
				+ " where user_id = ? ",
				amount,userId);
	}
}
