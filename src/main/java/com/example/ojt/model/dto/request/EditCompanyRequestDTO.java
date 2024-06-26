package com.example.ojt.model.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditCompanyRequestDTO {
    @NotEmpty(message = "Please fill nameCompany!")
    private String nameCompany;
    @NotEmpty(message = "Please fill phone!")
    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b", message = "Enter the Vietnamese phone")
    private String phone;
    @NotEmpty(message = "Please fill email!")
    private String email;
    private MultipartFile logo;
    @NotEmpty(message = "Please fill website!")
    private String website;
    @NotEmpty(message = "Please fill linkFacebook!")
    private String linkFacebook;
    @NotEmpty(message = "Please fill linkLinkedin!")
    private String linkLinkedin;
    @NotEmpty(message = "Please fill linkGithub!")
    private String linkGithub;
    @NotNull(message = "Please fill size!")
    private int size;
    @NotEmpty(message = "Please fill description!")
    private String description;
    @NotEmpty(message = "Please fill policy!")
    private String policy;
    @NotEmpty(message = "Please fill typeCompany")
    private String typeCompany;
}
