package com.example.application;

import java.util.List;

import com.example.domain.TransferView;

public interface TransferService {
	long create(String fromId, String toId, long amount); // 출금,입금,내역 INSERT
    List<TransferView> list(String userId);
}
