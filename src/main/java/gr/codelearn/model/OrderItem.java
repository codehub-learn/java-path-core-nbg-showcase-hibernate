package gr.codelearn.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private BigDecimal price;
    private Integer quantity;
}
