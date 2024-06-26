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
public class JobCandidateRequestDTO {
    @NotEmpty(message = "Please fill cvUrl!")
    private String cvUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Please fill interviewDay!")
    private Date interviewDay;
    @NotNull(message = "Please fill Job!")
    private Integer jobId;
    @NotEmpty(message = "Please fill Content!")
    private String content;
}
