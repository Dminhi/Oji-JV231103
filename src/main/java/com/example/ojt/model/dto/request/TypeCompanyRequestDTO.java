package com.example.ojt.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TypeCompanyRequestDTO {
    @NotEmpty(message = "Please fill Name!")
    private String Name;
}
