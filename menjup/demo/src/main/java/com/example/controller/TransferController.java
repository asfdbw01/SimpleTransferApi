package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.TransferService;
import com.example.domain.TransferView;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
	private final TransferService svc;
	
	public TransferController(TransferService svc) {
		this.svc = svc;
	}
	
	//간단하게 요청/응답만 받기에 여기 DTO 남김
	public record CreateReq(@NotBlank String fromId,@NotBlank String toId,@Positive long amount) {}
	public record CreateRes(long transferId) {}
	
	@PostMapping
	public ResponseEntity<CreateRes> create(@RequestBody CreateReq r){
		long id = svc.create(r.fromId, r.toId, r.amount);
		return ResponseEntity.status(201).body(new CreateRes(id));
	}
	
	@GetMapping
	public ResponseEntity<List<TransferView>> list(@RequestParam("userId") String userId){
		return ResponseEntity.ok(svc.list(userId));
	}
}
