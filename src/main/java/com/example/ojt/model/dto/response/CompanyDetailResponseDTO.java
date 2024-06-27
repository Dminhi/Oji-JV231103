package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Account;
import com.example.ojt.model.entity.AddressCompany;
import com.example.ojt.model.entity.Company;
import com.example.ojt.model.entity.TypeCompany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class CompanyDetailResponseDTO {
    private String name;
    private String logo;
    private String website;
    private String linkFacebook;
    private String linkLinkedin;
    private String linkGithub;
    private int followers;
    private int size;
    private String description;
    private String phone;
    private String emailCompany;
    private String policy;
    private String typeCompany;
    private Set<AddressCompanyResponseDTO> addressCompanyResponseDTOSet;
    public CompanyDetailResponseDTO(Company company) {
        this.name = company.getName();
        this.logo = company.getLogo();
        this.website = company.getWebsite();
        this.linkFacebook = company.getLinkFacebook();
        this.linkLinkedin = company.getLinkLinkedin();
        this.linkGithub = company.getLinkGithub();
        this.followers = company.getFollowers();
        this.size = company.getSize();
        this.description = company.getDescription();
        this.phone = company.getPhone();
        this.emailCompany = company.getEmailCompany();
        this.policy = company.getPolicy();
        this.typeCompany = company.getTypeCompany().getName();
        this.addressCompanyResponseDTOSet = company.getAddressCompanySet().stream().map(AddressCompanyResponseDTO::new).collect(Collectors.toSet());

    }
}
