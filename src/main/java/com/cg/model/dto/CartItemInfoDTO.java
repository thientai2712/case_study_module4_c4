package com.cg.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartItemInfoDTO {

    private Long id;

    private String productName;

    private String urlImage;

    private String productPrice;

    private String quantity;

    private String amount;

    private long totalCartItemQuantity;
}
