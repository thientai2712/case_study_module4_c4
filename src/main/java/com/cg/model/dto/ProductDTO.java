package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductDTO implements Validator {

    private Long id;

    @NotBlank(message = "Product Name is not required")
    private String title;

    @Max(value = 1000000, message = "Maximum price is 1000000")
    @Min(value = 50, message = "Minimum price is 50")
    @NotBlank(message = "Price is not null")
    private String price;


    private String urlImage;

    private String description;

    @Valid
    private CategoryDTO category;

    public ProductDTO(Long id, String title,String urlImage, BigDecimal price,String description, Category category) {
        this.id = id;
        this.title = title;
        this.price = price.toString();
        this.urlImage = urlImage;
        this.description = description;
        this.category = category.toCategoryDTO();
    }


    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setUrlImage(urlImage)
                .setPrice(new BigDecimal(Long.parseLong(price)))
                .setDescription(description)
                .setCategory(category.toCategory());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductDTO productDTO =(ProductDTO) target;

        String priceStr =productDTO.getPrice();

        if (priceStr == null){
            errors.rejectValue("price", "price.null", "Price is not null");
            return;
        }
        if (priceStr.isEmpty()){
            errors.rejectValue("price","price.isEmpty", "Price is not empty");
            return;
        }
        if (!priceStr.matches("(^$|[0-9]*$)")){
            errors.rejectValue("price", "price.matches", "Price only digit");
            return;
        }
        BigDecimal price = new BigDecimal(Long.parseLong(priceStr));
        BigDecimal min = new BigDecimal(50L);
        BigDecimal max = new BigDecimal(1000000L);

        if (price.compareTo(min) < 0) {
            errors.rejectValue("price", "price.min", "Price min 50");
            return;
        }
        if (price.compareTo(max) > 0) {
            errors.rejectValue("price", "price.min", "Price min 1.000.000");
        }

    }
}
