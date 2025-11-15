package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.AccountService;
import com.example.dto.Dtos.MoveReq;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountService svc;
	
	public AccountController (AccountService svc) {
		this.svc = svc;
	}
	
	//ResponseEntity<T> HTTP 응답을 ‘상태코드/헤더/바디’로 직접 제어할 수 있는 래퍼.T는 응답 바디 타입.
	@PostMapping("/debit")
    public ResponseEntity<Void> debit(@RequestBody @Valid MoveReq r) {
        svc.debit(r.userId(), r.amount());
        return ResponseEntity.noContent().build();
    }
	
	@PostMapping("/credit")
	public ResponseEntity<Void> credit(@RequestBody @Valid MoveReq r){
		svc.credit(r.userId(), r.amount());
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/cancel-debit")
	public ResponseEntity<Void> cancel(@RequestBody @Valid MoveReq r){
		svc.cancelDebit(r.userId(), r.amount());
		return ResponseEntity.noContent().build();
	}

}
