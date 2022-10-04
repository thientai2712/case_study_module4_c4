package com.cg.model;


import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product extends BaseEntities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Digits(integer = 12, fraction = 0)
    private BigDecimal price;

    @Column(name = "url_image",nullable = false)
    private String urlImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String description;

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setUrlImage(urlImage)
                .setTitle(title)
                .setPrice(price.toString())
                .setDescription(description)
                .setCategory(category.toCategoryDTO());
    }
}
