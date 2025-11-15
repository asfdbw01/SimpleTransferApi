package com.example.dao;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.TransferView;

@Repository
public class JdbcTransferRepository implements TransferRepository{
	private final JdbcTemplate jdbc;
	
	public JdbcTransferRepository(JdbcTemplate jdbc) {this.jdbc = jdbc;}
	
	@Override
	public long insert(String fromId, String toId, long amount) {
	    final String sql =
	        "INSERT INTO transfers (from_user_id, to_user_id, amount) VALUES (?,?,?)";

	    var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();

	    jdbc.update(con -> {
	        var ps = con.prepareStatement(sql, new String[] { "transfer_id" });
	        ps.setString(1, fromId);
	        ps.setString(2, toId);
	        ps.setLong(3, amount);
	        return ps;
	    }, keyHolder);

	    Object v = keyHolder.getKeys().get("transfer_id");
	    return ((Number) v).longValue();
	}
	
	@Override
    public List<TransferView> listByUser(String userId) {
		var sql = "select transfer_id, from_user_id, to_user_id, amount,event_time"
				+ " from transfers "
				+ " where from_user_id = ? or to_user_id = ? "
				+ " order by transfer_id desc ";
		return jdbc.query(sql, (rs,i) ->
			new TransferView(
					rs.getLong("transfer_id"),
	                rs.getString("from_user_id"),
	                rs.getString("to_user_id"),
	                rs.getLong("amount"),
	                rs.getTimestamp("event_time").toInstant()
					),userId,userId
			);
	}
}
