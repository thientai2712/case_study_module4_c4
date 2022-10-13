package com.cg.repository;

import com.cg.model.User;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.cg.model.dto.UserDTO (" +
            "u.id, " +
            "u.urlImage, " +
            "u.fullName, " +
            "u.email, " +
            "u.password, " +
            "u.phone, " +
            "u.role, " +
            "u.locationRegion" +
            ") " +
            "FROM User AS u " +
            "WHERE u.deleted = false"
    )
    List<UserDTO> findAllUserDTOdeleteFalse();

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

//    Boolean existsById(String id);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.email) FROM User u WHERE u.email = ?1")
    Optional<UserDTO> findUserDTOByEmail(String email);


    Optional<User> findUserByEmail(String email);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (" +
            "u.id, " +
            "u.urlImage, " +
            "u.fullName, " +
            "u.email, " +
            "u.password, " +
            "u.phone, " +
            "u.role, " +
            "u.locationRegion" +
            ") " +
            "FROM User u WHERE u.email = ?1 AND u.id <> ?2 ")
    Optional<UserDTO> findUserDTOByEmailAndIdIsNot(String email, Long id);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (" +
            "u.id, " +
            "u.urlImage, " +
            "u.fullName, " +
            "u.email, " +
            "u.password, " +
            "u.phone, " +
            "u.role, " +
            "u.locationRegion" +
            ") " +
            "FROM User u WHERE u.phone = ?1 AND u.id <> ?2 ")
    Optional<UserDTO> findUserDTOByPhoneAndIdIsNot(String phone, Long id);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (" +
            "u.id, " +
            "u.urlImage, " +
            "u.fullName, " +
            "u.email, " +
            "u.password, " +
            "u.phone, " +
            "u.role, " +
            "u.locationRegion" +
            ") " +
            "FROM User AS u " +
            "WHERE u.deleted = false " +
            "AND (u.phone LIKE %?1%" +
            "OR u.fullName LIKE %?1%) ")
    List<UserDTO> searchUserDTOByPhoneAndFullName(String keySearch);

}
