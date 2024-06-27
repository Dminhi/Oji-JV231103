package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.SkillsCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SkillCandidateResponse {
    private String name;
    private String candidateName;
    private String levelJobName;
    public SkillCandidateResponse (SkillsCandidate skillsCandidate) {
        this.name = skillsCandidate.getName();
        this.candidateName = skillsCandidate.getCandidate().getName();
        this.levelJobName = skillsCandidate.getLevelJob().getName();

    }
}
