package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.category.CategoryService;
import com.cg.service.product.ProductService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AppUtil appUtil;

    @GetMapping()
    public ResponseEntity<?> listProduct(){

        List<ProductDTO> productDTOS = productService.findAllProductDTOdeleteFalse();

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<?> homePage(){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){

        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()){
            throw new ResourceNotFoundException("Invalid product ID");
        }

        return new ResponseEntity<>(productOptional.get().toProductDTO(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        productDTO.setId(0L);

        Optional<Category> category = categoryService.findById(productDTO.toProduct().getCategory().getId());

        if (!category.isPresent()) {
            throw new DataInputException("Category ID invalid!!!");
        }

        Product newProduct = productService.save(productDTO.toProduct());

        return new ResponseEntity<>(newProduct.toProductDTO(),HttpStatus.CREATED);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> doDelete(@PathVariable Long id) {

        Optional<Product> optionalProduct = productService.findById(id);
        Map<String, String> result = new HashMap<>();
        String success;
        if (optionalProduct.isPresent()) {
            productService.softDelete(optionalProduct.get());
            success = "Delete product success";
            result.put("success", success);
        } else {
            throw new DataInputException("Delete is failed");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> doUpdate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        new ProductDTO().validate(productDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }


        Optional<Category> category = categoryService.findById(productDTO.toProduct().getCategory().getId());

        if (!category.isPresent()) {
            throw new DataInputException("Category ID invalid!!!");
        }


        Product newProduct = productService.save(productDTO.toProduct());


        return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/search/{keySearch}")
    public ResponseEntity<?> doSearch(@PathVariable String keySearch) {
        List<ProductDTO> productDTOList = productService.searchProductDTOByTileAndCategory(keySearch);

        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }
}
