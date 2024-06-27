package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.JobCandidates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobCandidateListResponseDTO {

    private Integer id;
    private String content;
    private int salaryFrom;
    private int salaryTo;
    private Set<TypesJobsResponse> typesJobsResponses;
    private String address;
    private String companyName;
    public JobCandidateListResponseDTO(JobCandidates jobCandidates) {
        this.id = jobCandidates.getId();
        this.content = jobCandidates.getContent();
        this.salaryFrom = jobCandidates.getJob().getSalaryFrom();
        this.salaryTo = jobCandidates.getJob().getSalaryTo();
        this.typesJobsResponses = jobCandidates.getJob().getTypesJobsSet().stream().map(TypesJobsResponse::new).collect(Collectors.toSet());
        this.address = jobCandidates.getJob().getAddressCompany().getAddress();
        this.companyName = jobCandidates.getJob().getCompany().getName();
    }
}
