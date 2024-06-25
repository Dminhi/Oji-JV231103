package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.Company;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyResponseDTO {
    private String nameCompany;
    private String logo;
    private String phone;
    private String email;
    public CompanyResponseDTO (Company company) {
        this.nameCompany = company.getName();
        this.logo = company.getLogo();
        this.phone = company.getPhone();
        this.email = company.getEmailCompany();
    }
}
