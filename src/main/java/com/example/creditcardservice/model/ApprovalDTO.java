package com.example.creditcardservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Approval Request DTO
 * @author javadevopsmc06
 *
 */
@Data
@Builder
@AllArgsConstructor
public class ApprovalDTO {
	private String customerId;
	
	private String requestId;
	
	private String approver;
	
	private String status;
	
	public ApprovalDTO() {
		super();
	}
}
