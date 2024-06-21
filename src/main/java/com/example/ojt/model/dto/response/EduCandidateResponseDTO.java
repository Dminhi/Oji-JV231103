package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Candidate;
import com.example.ojt.model.entity.EducationCandidate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EduCandidateResponseDTO {
    private Integer id;
    private String nameEducation;
    private String major;
    private String startAt;
    private String endAt;
    private String info;
    private int status = 1;
    private String candidate;
    public EduCandidateResponseDTO (EducationCandidate educationCandidate) {
        this.id = educationCandidate.getId();
        this.nameEducation = educationCandidate.getNameEducation();
        this.major = educationCandidate.getMajor();
        this.startAt = educationCandidate.getStartAt();
        this.endAt = educationCandidate.getEndAt();
        this.info = educationCandidate.getInfo();
        this.status = educationCandidate.getStatus();
        this.candidate = educationCandidate.getCandidate().getName();
    }
}
