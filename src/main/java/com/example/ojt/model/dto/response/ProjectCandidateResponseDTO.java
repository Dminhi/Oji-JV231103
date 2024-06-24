package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.ProjectCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectCandidateResponseDTO {
    private String name;
    private String link;
    private String startAt;
    private String endAt;
    private String info;
    private int status;
    public ProjectCandidateResponseDTO (ProjectCandidate projectCandidate) {
        this.name = projectCandidate.getName();
        this.link = projectCandidate.getLink();
        this.startAt = projectCandidate.getStartAt();
        this.endAt = projectCandidate.getEndAt();
        this.info = projectCandidate.getInfo();
        this.status = projectCandidate.getStatus();
    }
}
