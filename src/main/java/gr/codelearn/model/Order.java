package gr.codelearn.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Customer customer;
    private Date submitDate;
    private PaymentMethod paymentMethod;
    private BigDecimal totalCost;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
