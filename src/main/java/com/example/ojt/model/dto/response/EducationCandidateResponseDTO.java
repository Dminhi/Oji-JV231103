package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EducationCandidateResponseDTO {
    private String nameEducation;
    private String major;
    private String info;
    public EducationCandidateResponseDTO (EducationCandidate educationCandidate) {
        this.nameEducation = educationCandidate.getNameEducation();
        this.major = educationCandidate.getMajor();
        this.info = educationCandidate.getInfo();
    }
}
