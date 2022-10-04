package com.cg.controller.rest;


import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.category.CategoryService;
import com.cg.service.category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> showListCategory(){


        List<Category> categories = categoryService.findAll();

        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for (Category item : categories){

            CategoryDTO categoryDTO = item.toCategoryDTO();
            categoryDTOS.add(categoryDTO);
        }


        return new ResponseEntity<>(categoryDTOS,HttpStatus.OK);
    }

    @GetMapping("/{id}") 
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){

        Optional<Category> categoryOptional = categoryService.findById(id);

        return new ResponseEntity<>(categoryOptional.get().toCategoryDTO(),HttpStatus.ACCEPTED);
    }
}
