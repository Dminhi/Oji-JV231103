package com.example.ojt.model.dto.request;

import com.example.ojt.model.entity.TypeJob;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class JobRequestDTO {
    @NotEmpty(message = "Please fill title!")
    private String title;
    @NotEmpty(message = "Please fill description!")
    private String description;
    @NotEmpty(message = "Please fill requirements!")
    private String requirements;
    @NotEmpty(message = "Please fill salaryFrom!")
    private String salaryFrom;
    @NotEmpty(message = "Please fill salaryTo!")
    private String salaryTo;
    @NotNull(message = "Please fill address!")
    private Integer addressId;
    private Set<TypeJobRequestDTO> typeJobSet;
    private Set<LeveJobRequestDTO> levelJob;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date expireAt;
}
