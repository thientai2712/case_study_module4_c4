package com.cg.controller.rest;


import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.CartItemDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cartItem.CartItemService;
import com.cg.service.product.ProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppUtil appUtil;



    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDTO cartItemDTO){

        Long productId = cartItemDTO.getProductId();

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()){
            throw new DataInputException("Product ID invalid!!");
        }

        Product product = productOptional.get();
        BigDecimal price = product.getPrice();

        String customerName = appUtil.getPrincipal();
        User user = userService.findUserByEmail(customerName).get();

        Optional<Cart> cartOptional = cartService.findByUser(user);

        if (!cartOptional.isPresent()){
            Cart cart = new Cart();
            cart.setTotalAmount(price);
            cart.setUser(user);

            Cart newCart = cartService.save(cart);

            CartItem cartItem = new CartItem();

            cartItem.setId(0L);
            cartItem.setCart(newCart);
            cartItem.setProduct(product);
            cartItem.setProductName(product.getTitle());
            cartItem.setUrlImage(product.getUrlImage());
            cartItem.setQuantity(1L);
            cartItem.setProductPrice(price);
            cartItem.setAmount(price);

            cartItemService.save(cartItem);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        Optional<CartItem> cartItemOptional = cartItemService.findByProduct(product);

        if(!cartItemOptional.isPresent()){

            CartItem cartItem = new CartItem();
            cartItem.setId(0L);
            cartItem.setProduct(product);
            cartItem.setCart(cartOptional.get());
            cartItem.setProductName(product.getTitle());
            cartItem.setProductPrice(price);
            cartItem.setUrlImage(product.getUrlImage());
            cartItem.setQuantity(1L);
            cartItem.setAmount(price);

            cartItemService.save(cartItem);

            Cart cart = cartOptional.get();
            BigDecimal totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount.add(price));

            cartService.save(cart);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        CartItem cartItem = cartItemOptional.get();

        Long quantity = cartItem.getQuantity();
        BigDecimal amount = cartItem.getAmount();


//        Long oldQuantity = cartItem.getQuantity();
//        Long newQuantity = oldQuantity + 1;
//        BigDecimal newPrice = product.getPrice();
//        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductName(product.getTitle());
        cartItem.setQuantity(quantity + 1);
        cartItem.setProductPrice(price);
        cartItem.setAmount(amount.add(price));

        cartItemService.save(cartItem);

        Cart cart = cartOptional.get();
        BigDecimal totalAmount = cart.getTotalAmount();

        cart.setTotalAmount(totalAmount.add(price));

//        BigDecimal newTotalAmount = cartItem.getAmount();
//
//        cart.setTotalAmount(cart.getTotalAmount().add(newTotalAmount));

        cartService.save(cart);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(){

        String username = appUtil.getPrincipal();

        Optional<User> userOptional = userService.findUserByEmail(username);

        if (!userOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng không hợp lệ (MS001)");
        }

        User user = userOptional.get();

        try {
            boolean success = cartService.checkout(user);

            if (success) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            throw new DataInputException("Liên hệ với quản trị hệ thống (MS001)");

        } catch (Exception e) {
            throw new DataInputException("Liên hệ với quản trị hệ thống (MS002)");
        }

    }


}
