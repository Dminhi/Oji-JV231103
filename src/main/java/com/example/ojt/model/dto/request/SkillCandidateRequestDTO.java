package com.example.ojt.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SkillCandidateRequestDTO {
    @NotEmpty(message = "Please fill name!")
    private String name;
    @NotNull(message = "Please fill levelJobId!")
    private Integer levelJobId;
}
