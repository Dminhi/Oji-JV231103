package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.model.entity.ExperienceCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExperienceCandidateResponseDTO {
    private String company;
    private String position;
    private Date startAt;
    private Date endAt;
    private String info;
    private boolean status;
    public ExperienceCandidateResponseDTO (ExperienceCandidate experienceCandidate) {
        this.company = experienceCandidate.getCompany();
        this.position = experienceCandidate.getPosition();
        this.startAt = experienceCandidate.getStartAt();
        this.endAt = experienceCandidate.getEndAt();
        this.info = experienceCandidate.getInfo();
        this.status = experienceCandidate.isStatus();
    }
}
