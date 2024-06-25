package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.ProjectCandidate;
import com.example.ojt.model.entity.TypeCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TypeCompanyResponse {
    private String name;

    public TypeCompanyResponse(TypeCompany typeCompany) {
        this.name = typeCompany.getName();

    }
}
