package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Role;
import com.cg.model.User;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.role.RoleService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AppUtil appUtil;

    @GetMapping()
    public ResponseEntity<?> showListUser(){

        List<UserDTO> newUser = userService.findAllUserDTOdeleteFalse();

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){

        Optional<User> userOptional =  userService.findById(id);

        if (!userOptional.isPresent()){
            throw new ResourceNotFoundException("Invalid customer ID");
        }

        return new ResponseEntity<>(userOptional.get().toUserDTO(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated  @RequestBody UserDTO userDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Long roleId = userDTO.getRole().getId();
        String email = userDTO.getEmail();
        String phone = userDTO.getPhone();

        Optional<Role> roleOptional = roleService.findById(roleId);

        if (!roleOptional.isPresent()){
           throw new DataInputException("Role ID invalid!");
        }

        Boolean exitsByEmail = userService.existsByEmail(email);

        if (exitsByEmail){
            throw new EmailExistsException("Email already exits!");
        }

        Boolean exitsByPhone = userService.existsByPhone(phone);

        if(exitsByPhone){
            throw  new DataInputException("Phone already exits!");
        }

        userDTO.setId(0L);
        userDTO.getLocationRegion().setId(0L);

        try{
            User newUser = userService.save(userDTO.toUser());
            return new ResponseEntity<>(newUser.toUserDTO() ,HttpStatus.CREATED);
        }catch (Exception e){
            throw  new DataInputException("Please contact manager!!");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> doDelete(@PathVariable Long id){

        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()){
            userService.softDelete(userOptional.get());
        }else {
            throw new DataInputException("User ID is Invalid!");
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> doUpdate(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Long roleId = userDTO.getRole().getId();
        Long id = userDTO.getId();
        String email = userDTO.getEmail();
        String phone = userDTO.getPhone();

        Optional<User> userOptional = userService.findById(userDTO.getId());


        if (!userOptional.isPresent()){
            throw new DataInputException("User ID is Invalid!");
        }

//        Optional<Role> roleOptional = roleService.findById(roleId);
//
//        if (!roleOptional.isPresent()){
//            throw new DataInputException("Role ID invalid!");
//        }

        Optional<UserDTO> userDTOOptional = userService.findUserDTOByEmailAndIdIsNot(email, id);

        if (userDTOOptional.isPresent()){
            throw new EmailExistsException("Email already exits!");
        }
       Optional<UserDTO> userDTOOptional1 = userService.findUserDTOByPhoneAndIdIsNot(phone, id);

        if (userDTOOptional1.isPresent()){
            throw new DataInputException("Phone already exits!!");
        }

        Optional<User> optionalUser = userService.findById(userDTO.getId());

        userDTO.getLocationRegion().setId(optionalUser.get().getId());



        optionalUser.get().setFullName(userDTO.getFullName());
        optionalUser.get().setEmail(userDTO.getEmail());
        optionalUser.get().setPassword("123");
        optionalUser.get().setPhone(userDTO.getPhone());
        optionalUser.get().setRole(userDTO.getRole().toRole());
        optionalUser.get().setLocationRegion(userDTO.getLocationRegion().toLocationRegion());
        optionalUser.get().setUrlImage("user.png");


        User updateUser = userService.save(optionalUser.get());

        return new ResponseEntity<>(updateUser.toUserDTO(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/search/{keySearch}")
    public ResponseEntity<?> doSearch(@PathVariable String keySearch) {
        List<UserDTO> userDTOList = userService.searchUserDTOByPhoneAndFullName(keySearch);

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
}
