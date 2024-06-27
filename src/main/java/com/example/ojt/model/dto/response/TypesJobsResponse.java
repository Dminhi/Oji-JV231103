package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.TypesJobs;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TypesJobsResponse {
    private String typeJob;
    public TypesJobsResponse (TypesJobs typesJobs) {
        this.typeJob = typesJobs.getTypeJob().getName();
    }
}
