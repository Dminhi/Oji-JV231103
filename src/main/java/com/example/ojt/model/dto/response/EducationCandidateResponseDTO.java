package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EducationCandidateResponseDTO {
    private String nameEducation;
    private String major;
    private String startAt;
    private String endAt;
    private String info;
    private int status;
    public EducationCandidateResponseDTO (EducationCandidate educationCandidate) {
        this.nameEducation = educationCandidate.getNameEducation();
        this.major = educationCandidate.getMajor();
        this.startAt = educationCandidate.getStartAt();
        this.endAt = educationCandidate.getEndAt();
        this.info = educationCandidate.getInfo();
        this.status = educationCandidate.getStatus();
    }
}
