package com.example.ojt.model.dto.request;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CertificateCandidateRequestDTO {

    private Integer id;
    @NotEmpty(message = "Please fill Name!")
    private String name;
    @NotEmpty(message = "Please fill organization!")
    private String organization;
    @NotEmpty(message = "Please fill startAt!")
    private String startAt;
    @NotEmpty(message = "Please fill endAt!")
    private String endAt;
    @NotEmpty(message = "Please fill endAt!")
    private String info;
    private int status = 1;

}
