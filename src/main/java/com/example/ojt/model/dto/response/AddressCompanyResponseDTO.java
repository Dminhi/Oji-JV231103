package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.AddressCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressCompanyResponseDTO {
    private Integer id;
    private String address;
    private String mapUrl;
    private boolean status;
    private String company;
    private String location;
    public AddressCompanyResponseDTO (AddressCompany addressCompany) {
        this.id = addressCompany.getId();
        this.address = addressCompany.getAddress();
        this.mapUrl = addressCompany.getMapUrl();
        this.company = addressCompany.getCompany().getName();
        this.location = addressCompany.getLocation().getNameCity();
        this.status = addressCompany.isStatus();
    }
}
