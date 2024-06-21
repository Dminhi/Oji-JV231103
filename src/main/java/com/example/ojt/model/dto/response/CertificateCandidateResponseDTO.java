package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.EducationCandidate;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CertificateCandidateResponseDTO {
    private Integer id;
    private String name;
    private String organization;
    private String startAt;
    private String endAt;
    private String info;
    private int status = 1;
    private String candidate;
    public CertificateCandidateResponseDTO (CertificateCandidate certificateCandidate) {
        this.id = certificateCandidate.getId();
        this.name = certificateCandidate.getName();
        this.organization = certificateCandidate.getOrganization();
        this.startAt = certificateCandidate.getStartAt();
        this.endAt = certificateCandidate.getEndAt();
        this.info = certificateCandidate.getInfo();
        this.status = certificateCandidate.getStatus();
        this.candidate = certificateCandidate.getCandidate().getName();
    }
}
