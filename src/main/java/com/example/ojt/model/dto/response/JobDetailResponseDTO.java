package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Job;
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
public class JobDetailResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private String requirements;
    private int salaryFrom;
    private int salaryTo;
    private Date interview;
    private Set<TypesJobsResponse> typesJobsResponses;
    private String address;
    private String companyName;
    public JobDetailResponseDTO(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.requirements = job.getRequirements();
        this.salaryFrom = job.getSalaryFrom();
        this.salaryTo = job.getSalaryTo();
        this.typesJobsResponses = job.getTypesJobsSet().stream().map(TypesJobsResponse::new).collect(Collectors.toSet());
        this.address = job.getAddressCompany().getAddress();
        this.companyName = job.getCompany().getName();
    }
}
