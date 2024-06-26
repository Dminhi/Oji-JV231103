package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.JobCandidates;
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
public class JobCandidateResponseDTO {
    private String cvUrl;
    private Date interviewDay;
    private String jobName;
    private String content;
    public JobCandidateResponseDTO (JobCandidates jobCandidates) {
        this.cvUrl = jobCandidates.getCvUrl();
        this.interviewDay = jobCandidates.getInterviewDay();
        this.jobName = jobCandidates.getJob().getTitle();
        this.content = jobCandidates.getContent();
    }
}
