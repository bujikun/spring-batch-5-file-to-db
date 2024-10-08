package dev.bujiku.batch_demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sales_info")
public class SalesInfo {
    @Id
    private Long id;
    private String product;
    private String seller;
    private Integer sellerId;
    private BigDecimal price;
    private String city;
    private String category;

}
