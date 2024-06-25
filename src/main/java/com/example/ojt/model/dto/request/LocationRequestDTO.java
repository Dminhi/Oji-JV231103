package com.example.ojt.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LocationRequestDTO {
    private Integer id;
    @NotEmpty(message = "Please fill Location!")
    private String Location;
}
