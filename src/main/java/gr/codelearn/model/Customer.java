package gr.codelearn.model;

import jakarta.validation.constraints.*;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity{
    @NotNull(message = "Cannot be null.")
    @Email
    private String email;
    @NotNull
    //@Pattern(regexp = ".")
    @NotBlank // cannot save if: null, "", "    "
    private String firstname;
    @NotNull
    private String lastname;
    //@Transient
    @Min(18)
    @Max(110)
    private Integer age;
    @Size(max = 50)
    private String address;
    @Enumerated(EnumType.STRING)
    private CustomerCategory customerCategory;
}
