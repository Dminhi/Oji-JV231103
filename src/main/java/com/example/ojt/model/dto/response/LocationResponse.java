package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.Location;
import com.example.ojt.model.entity.TypeCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationResponse {
    private String nameCity;

    public LocationResponse(Location location) {
        this.nameCity = location.getNameCity();

    }
}
