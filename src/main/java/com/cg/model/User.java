package com.cg.model;


import com.cg.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String phone;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "url_image",nullable = false)
    private String urlImage;

    @OneToOne
    @JoinColumn(name = "location_region_id", nullable = false)
    private LocationRegion locationRegion;

    public UserDTO toUserDTO(){
        return new UserDTO()
                .setId(id)
                .setUrlImage(urlImage)
                .setFullName(fullName)
                .setEmail(email)
                .setPassword("123")
                .setPhone(phone)
                .setRole(role.toRoleDTO())
                .setLocationRegion(locationRegion.toLocationRegionDTO());
    }
}
