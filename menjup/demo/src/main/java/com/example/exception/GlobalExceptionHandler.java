package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	//비즈니스 에러를  ResponseStatusException으로 던졌을 때
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handle(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode())
                .body(ApiError.of(e.getReason(), e.getMessage()));
    }
	
	//Vaild 실패
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handle(MethodArgumentNotValidException e){
		String msg = e.getBindingResult().getFieldErrors().stream()
				.findFirst().map(f -> f.getField()+" "+f.getDefaultMessage())
				.orElse("validation error");
		return ResponseEntity.badRequest()
				.body(ApiError.of("VALIDATION_ERROR", msg));
	}
	
	//기타 예외
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handle(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiError.of("INTERNAL_ERROR", e.getMessage()));
	}
}
