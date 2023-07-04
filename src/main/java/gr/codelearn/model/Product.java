package gr.codelearn.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity{
    @Column(nullable = false)
    private String serial;
    private String name;
    private BigDecimal price;
    @ManyToOne(optional = false)
    @Column(nullable = false)
    private Category category;
}
