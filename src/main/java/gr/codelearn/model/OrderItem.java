package gr.codelearn.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem extends BaseEntity{
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private BigDecimal price;
    private Integer quantity;
}
