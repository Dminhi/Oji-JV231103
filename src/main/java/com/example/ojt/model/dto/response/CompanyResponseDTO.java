package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.CertificateCandidate;
import com.example.ojt.model.entity.Company;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyResponseDTO {
    private Integer id;
    private String nameCompany;
    private String logo;
    private Set<AddressCompanyResponseDTO> addressCompanyResponseDTOSet;
    private String typeCompany;
    private Integer listPosition;

    public CompanyResponseDTO(Company company) {
        this.id = company.getId();
        this.nameCompany = company.getName();
        this.logo = company.getLogo();
        this.typeCompany = company.getTypeCompany().getName();
        this.addressCompanyResponseDTOSet = company.getAddressCompanySet().stream().map(AddressCompanyResponseDTO::new).collect(Collectors.toSet());
        this.listPosition = (int) company.getJobs().stream().filter(job -> job.getStatus() == 1).count();
    }

}
