
package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Candidate;
import com.example.ojt.model.entity.EducationCandidate;
import com.example.ojt.model.entity.SkillsCandidate;
import lombok.*;


import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CandidateResponseDTO {
    private String name;
    private String birthday;
    private String address;
    private String phone;
    private String gender;
    private String linkLinkedin;
    private String linkGit;
    private String position;
    private String avatar;
    private String aboutme;
    private boolean status;
    private Date createAt = new Date();
    private Set<SkillCandidateResponse> skillCandidateResponseSet;
    private Set<EducationCandidateResponseDTO> educationCandidateResponseDTOS;
    private Set<ExperienceCandidateResponseDTO> experienceCandidateResponseDTOS;

    public CandidateResponseDTO (Candidate candidate) {
        this.name = candidate.getName();
        this.birthday = candidate.getBirthday();
        this.address = candidate.getAddress();
        this.phone = candidate.getPhone();
        this.gender = candidate.getGender();
        this.linkLinkedin = candidate.getLinkLinkedin();
        this.linkGit = candidate.getLinkGit();
        this.position = candidate.getPosition();
        this.avatar = candidate.getAvatar();
        this.aboutme = candidate.getAboutme();
        this.status = candidate.isStatus();
        this.createAt = candidate.getCreatedAt();
        this.skillCandidateResponseSet = candidate.getSkillsCandidates().stream().map(SkillCandidateResponse::new).collect(Collectors.toSet());
        this.educationCandidateResponseDTOS = candidate.getEducationCandidates().stream().map(EducationCandidateResponseDTO::new).collect(Collectors.toSet());
        this.experienceCandidateResponseDTOS = candidate.getExperienceCandidates().stream().map(ExperienceCandidateResponseDTO::new).collect(Collectors.toSet());
    }
}

