package dev.bujiku.batch_demo;

import java.math.BigDecimal;

/**
 * @author Newton Bujiku
 * @since 2024
 */
public record SalesInfoDTO(
        String product,
        String seller,
        Integer sellerId,
        BigDecimal price,
        String city,
        String category
) {
}
