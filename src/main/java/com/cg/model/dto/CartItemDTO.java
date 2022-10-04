package com.cg.model.dto;


import com.cg.model.Cart;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartItemDTO {

    private Long id;

    private String productName;

    private String urlImage;

    private String productPrice;

    private Long quantity;

    private String amount;

    private Long cartId;

    private Long productId;
}
