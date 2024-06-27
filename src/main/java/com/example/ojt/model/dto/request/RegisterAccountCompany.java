package com.example.ojt.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterAccountCompany {
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Please fill password!")
    @Size(min = 4, max = 12, message = "Password's length must be between 4 and 12")
    private String password;
    @NotEmpty(message = "Please fill password!")
    @Size(min = 4, max = 12, message = "Password's length must be between 4 and 12")
    private String confirmPassword;
    private String roleName = "ROLE_COMPANY";
    @NotEmpty(message = "Please fill nameCompany!")
    private String nameCompany;
    //    @NotEmpty(message = "Please fill location!")
//    private String location;
    @NotEmpty(message = "Please fill phone!")
    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b", message = "Enter the Vietnamese phone")
    private String phone;
    @NotEmpty(message = "Please fill email!")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should be valid")
    private String emailCompany;

}
