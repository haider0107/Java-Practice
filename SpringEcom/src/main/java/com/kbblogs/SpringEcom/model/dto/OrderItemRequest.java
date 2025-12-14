package com.kbblogs.SpringEcom.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {
}
