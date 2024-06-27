package com.example.ojt.model.dto.request;

import com.example.ojt.model.entity.Candidate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
public class EduCandidateRequestDTO {
    @NotEmpty(message = "Please fill nameEducation!")
    private String nameEducation;
    @NotEmpty(message = "Please fill major!")
    private String major;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Please fill startAt!")
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Please fill endAt!")
    private Date endAt;
    @NotEmpty(message = "Please fill info!")
    private String info;
}
