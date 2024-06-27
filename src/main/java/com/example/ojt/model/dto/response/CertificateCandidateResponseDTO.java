package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Candidate;
import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.ProjectCandidate;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CertificateCandidateResponseDTO {
    private String name;
    private String organization;
    private Date startAt;
    private Date endAt;
    private String info;
    private boolean status;
    public CertificateCandidateResponseDTO (CertificateCandidate certificateCandidate) {
        this.name = certificateCandidate.getName();
        this.organization = certificateCandidate.getOrganization();
        this.startAt = certificateCandidate.getStartAt();
        this.endAt = certificateCandidate.getEndAt();
        this.info = certificateCandidate.getInfo();
        this.status = certificateCandidate.isStatus();
    }
}
