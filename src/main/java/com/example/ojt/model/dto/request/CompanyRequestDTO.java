package com.example.ojt.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyRequestDTO {
    @NotEmpty(message = "Please fill nameCompany!")
    private String nameCompany;
    @NotEmpty(message = "Please fill location!")
    private String location;
    @NotEmpty(message = "Please fill phone!")
    private String phone;
    @NotEmpty(message = "Please fill email!")
    private String email;
}
