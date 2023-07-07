package gr.codelearn.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem extends BaseEntity{
    @ManyToOne
    @NotNull
    private Order order;
    @ManyToOne
    @NotNull
    private Product product;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantity;
}
