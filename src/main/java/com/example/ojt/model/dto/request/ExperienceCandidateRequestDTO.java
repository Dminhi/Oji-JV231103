package com.example.ojt.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExperienceCandidateRequestDTO {
    @NotEmpty(message = "Please fill company!")
    private String company;
    @NotEmpty(message = "Please fill position!")
    private String position;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Please fill startAt!")
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Please fill endAt!")
    private Date endAt;
    @NotEmpty(message = "Please fill info!")
    private String info;
}
