package com.cg.model.dto;


import com.cg.model.LocationRegion;
import com.cg.model.Role;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    @Column(nullable = false)
    private Long id;

    @Size(min = 5, max = 30, message = "The length of Full Name must be between 5 and 30 characters")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "The email is required")
    @Column(unique = true)
    private String email;

    private String password;

    @Pattern(regexp = "^[0][1-9][0-9]{8,9}$", message = "Phone is not valid")
    private String phone;

    @Valid
    private RoleDTO role;

    @Column(nullable = false)
    private String urlImage;

    @Valid
    private LocationRegionDTO locationRegion;

    public User toUser(){
        return new User()
                .setId(id)
                .setUrlImage(urlImage)
                .setFullName(fullName)
                .setEmail(email)
                .setPassword("123")
                .setPhone(phone)
                .setRole(role.toRole())
                .setLocationRegion(locationRegion.toLocationRegion());

    }

    public UserDTO(Long id, String urlImage , String fullName, String email, String password, String phone, Role role, LocationRegion locationRegion) {
        this.id = id;
        this.urlImage = urlImage;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role.toRoleDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public UserDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
