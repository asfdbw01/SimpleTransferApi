package com.example.domain;

import java.time.Instant;

public record TransferView(
		long transferId,
		String fromUserId,
		String toUserId,
		long amount,
		Instant eventTime) {}
