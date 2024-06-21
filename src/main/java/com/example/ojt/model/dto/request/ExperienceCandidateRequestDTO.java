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
public class ExperienceCandidateRequestDTO {

    private Integer id;
    @NotEmpty(message = "Please fill company!")
    private String company;
    @NotEmpty(message = "Please fill position!")
    private String position;
    @NotEmpty(message = "Please fill startAt!")
    private String startAt;
    @NotEmpty(message = "Please fill endAt!")
    private String endAt;
    @NotEmpty(message = "Please fill Info!")
    private String info;
}
