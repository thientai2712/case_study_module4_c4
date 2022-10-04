package com.cg.model.dto;

import com.cg.model.Order;
import com.cg.model.OrderItem;
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
public class OrderItemDTO {

    private Long id;

    private String productName;

    private String productPrice;

    private Long quantity;

    private String amount;

    private Long productId;

    private Long cartId;

    public OrderItem toOrderItem(Order order) {
        return new OrderItem()
                .setId(id)
                .setProductName(productName)
                .setProductPrice(new BigDecimal(productPrice))
                .setQuantity(quantity)
                .setTotal(new BigDecimal(amount))
                .setOrder(order);
    }
}
