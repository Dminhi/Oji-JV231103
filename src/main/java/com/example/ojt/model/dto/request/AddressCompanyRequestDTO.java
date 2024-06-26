package com.example.ojt.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressCompanyRequestDTO {
    @NotEmpty(message = "Please fill address!")
    private String address;
    @NotEmpty(message = "Please fill mapUrl!")
    private String mapUrl;
    @NotEmpty(message = "Please fill location!")
    private String location;
}
