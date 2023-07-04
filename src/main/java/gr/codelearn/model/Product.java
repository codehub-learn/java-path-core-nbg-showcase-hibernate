package gr.codelearn.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serial;
    private String name;
    private BigDecimal price;
    @ManyToOne
    private Category category;
}
