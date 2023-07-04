package gr.codelearn.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity{
    private String email;
    private String firstname;
    private String lastname;
    private Integer age;
    private String address;
    @Enumerated(EnumType.STRING)
    private CustomerCategory customerCategory;
}
