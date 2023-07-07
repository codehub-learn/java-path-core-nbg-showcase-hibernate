package gr.codelearn.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends BaseEntity{
    @Column(nullable = false)
    @NotNull
    private String serial;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;
}
