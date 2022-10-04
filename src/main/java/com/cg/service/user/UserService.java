package com.cg.service.user;

import com.cg.exception.EmailExistsException;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends IGeneralService<User>, UserDetailsService {

    List<UserDTO> findAllUserDTOdeleteFalse();

    UserDetails loadUserByEmail(String email) throws EmailExistsException;

    User getByEmail(String email);

    Optional<UserDTO> findUserDTOByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);


    Boolean existsById(Long id);

    Optional<User> findUserByEmail(String email);

    Optional<UserDTO> findUserDTOByEmailAndIdIsNot(String email, Long id);

    Optional<UserDTO> findUserDTOByPhoneAndIdIsNot(String phone, Long id);
}
