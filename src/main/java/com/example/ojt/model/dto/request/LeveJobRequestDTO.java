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
public class LeveJobRequestDTO {
    @NotEmpty(message = "Please fill leveJobName!")
    private String leveJobName;
}
