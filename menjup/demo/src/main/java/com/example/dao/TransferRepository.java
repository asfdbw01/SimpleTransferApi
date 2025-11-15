package com.example.dao;

import java.util.List;

import com.example.domain.TransferView;

public interface TransferRepository {
	long insert(String fromId,String toId,long amount);
	List<TransferView> listByUser(String userId);
}
