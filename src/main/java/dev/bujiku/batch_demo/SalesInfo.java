package dev.bujiku.batch_demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
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
