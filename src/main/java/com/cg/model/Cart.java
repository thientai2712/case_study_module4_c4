package com.cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
@Accessors(chain = true)
public class Cart extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = 12, fraction = 0)
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems;

    public Order toOrder(){
        return new Order()
                .setId(0L)
                .setGrandTotal(totalAmount)
                .setUser(user);
    }
}
