package com.example.ojt.model.dto.request;

import com.example.ojt.model.entity.Candidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EduCandidateRequestDTO {
    private Integer id;
    @NotEmpty(message = "Please fill nameEducation!")
    private String nameEducation;
    @NotEmpty(message = "Please fill major!")
    private String major;
    @NotEmpty(message = "Please fill startAt!")
    private String startAt;
    @NotEmpty(message = "Please fill endAt!")
    private String endAt;
    @NotEmpty(message = "Please fill info!")
    private String info;
}
