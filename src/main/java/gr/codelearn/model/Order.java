package gr.codelearn.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order extends BaseEntity{
    @ManyToOne
    private Customer customer;
    private Date submitDate;
    private PaymentMethod paymentMethod;
    private BigDecimal totalCost;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
