package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Dtos {
	public record MoveReq(
			@NotBlank String userId,
			@Positive long amount) {}
}
