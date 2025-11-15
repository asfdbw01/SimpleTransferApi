package com.example.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.dao.AccountRepository;
import com.example.dao.TransferRepository;
import com.example.domain.TransferView;

@Service
public class TransferServiceImpl implements TransferService{
	private final AccountRepository accountRepo;
	private final TransferRepository transferRepo;
	
	public TransferServiceImpl(AccountRepository accountRepo,TransferRepository transferRepo) {
		this.accountRepo = accountRepo;
		this.transferRepo = transferRepo;
	}
	
	@Override
	@Transactional
	public long create(String fromId,String toId,long amount) {
		if(fromId == null || fromId.isBlank() || toId==null || toId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER_ID_BLANK");
		}
		
		if(fromId.equals(toId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SAME_USER");
		}
		
		if(amount <= 0) {
			throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_AMOUNT");
		}
		
		int n = accountRepo.debit(fromId, amount);
		if(n==0) {//잔액 부족
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "INSUFFICIENT_BALANCE");
		}
		accountRepo.credit(toId, amount);
		return transferRepo.insert(fromId, toId, amount);
	}
	
	@Override
	public List<TransferView> list(String userId){
		if(userId==null || userId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER_ID_BLANK");
		}
		return transferRepo.listByUser(userId);
	}
}
