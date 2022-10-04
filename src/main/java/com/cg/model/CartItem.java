package com.cg.model;


import com.cg.model.dto.CartItemDTO;
import com.cg.model.dto.CartItemInfoDTO;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
@Accessors(chain = true)
public class CartItem extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private String urlImage;

    @Column(name = "product_price", nullable = false)
    @Digits(integer = 12, fraction = 0)
    private BigDecimal productPrice;

    private Long quantity;

    @Digits(integer = 12, fraction = 0)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CartItemInfoDTO toCartItemInfoDTO(){
        return new CartItemInfoDTO()
                .setId(id)
                .setProductName(productName)
                .setUrlImage(urlImage)
                .setProductPrice(productPrice.toString())
                .setQuantity(quantity.toString())
                .setAmount(amount.toString());

    }
    public CartItemInfoDTO toCartItemInfoDTOWithCountQuantity(long totalCartItemQuantity){
        return new CartItemInfoDTO()
                .setId(id)
                .setProductName(productName)
                .setUrlImage(urlImage)
                .setProductPrice(productPrice.toString())
                .setQuantity(quantity.toString())
                .setAmount(amount.toString())
                .setTotalCartItemQuantity(totalCartItemQuantity);
    }
    public OrderItem toOrderItem(Order order) {
        return new OrderItem()
                .setId(id)
                .setProductName(productName)
                .setProductPrice(productPrice)
                .setQuantity(quantity)
                .setTotal(amount)
                .setOrder(order);
    }
}
