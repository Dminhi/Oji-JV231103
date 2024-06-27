package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Candidate;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CandidateListResponseDTO {
    private String name;
    private String avatar;
    private Set<SkillCandidateResponse> skillSet;
    private String address;

    public CandidateListResponseDTO (Candidate candidate) {
        this.name = candidate.getName();
        this.address = candidate.getAddress();
        this.avatar = candidate.getAvatar();
        this.skillSet = candidate.getSkillsCandidates().stream().map(SkillCandidateResponse::new).collect(Collectors.toSet());
    }
}
