package gr.codelearn.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category extends BaseEntity{
    @NotNull
    private String description;
}
