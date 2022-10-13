package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.CartItemDTO;
import com.cg.model.dto.CartItemInfoDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cartItem.CartItemService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemRestController {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getCartItem(){


        String userName = appUtil.getPrincipal();

        User user = userService.findUserByEmail(userName).get();

        Optional<Cart> cartOptional = cartService.findByUser(user);

        if (!cartOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Cart cart = cartOptional.get();

        List<CartItem> cartItems = cartItemService.findByCart(cart);

        if (cartItems.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CartItemInfoDTO> cartItemInfoDTOS = new ArrayList<>();

        for (CartItem item : cartItems){
            cartItemInfoDTOS.add(item.toCartItemInfoDTO());
        }


        return new ResponseEntity<>(cartItemInfoDTOS, HttpStatus.OK);
    }

    @PatchMapping("/add/{cartItemId}")
    public ResponseEntity<?> addCartItem(@PathVariable Long cartItemId){

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()){
            throw new DataInputException("CartItem ID invalid!");
        }

        CartItem cartItem = cartItemOptional.get();

        Product product = cartItemOptional.get().getProduct();

        BigDecimal newPrice = product.getPrice();
        long currentQuantity = cartItem.getQuantity();
        long newQuantity = currentQuantity + 1;

        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductPrice(newPrice);
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);

        CartItem newCartItem = cartItemService.save(cartItem);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItem.getCart());

        Optional<Cart> cartOptional = cartService.findById(cartItemOptional.get().getCart().getId());

        Cart cart = cartOptional.get();

        BigDecimal totalAmount = cart.getTotalAmount();

        cart.setTotalAmount(totalAmount.add(newPrice));

        cartService.save(cart);


        return new ResponseEntity<>(newCartItem.toCartItemInfoDTOWithCountQuantity(totalCartItemQuantity), HttpStatus.OK);
    }

    @PatchMapping("/minus/{cartItemId}")
    public ResponseEntity<?> minusCartItem(@PathVariable Long cartItemId){

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()){
            throw new DataInputException("CartItem ID invalid!");
        }

        CartItem cartItem = cartItemOptional.get();

        Product product = cartItemOptional.get().getProduct();

        BigDecimal newPrice = product.getPrice();
        long currentQuantity = cartItem.getQuantity();
        long newQuantity = currentQuantity - 1;


        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductPrice(newPrice);
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);

        CartItem newCartItem = cartItemService.save(cartItem);

        Cart cart = cartItem.getCart();

        BigDecimal nAmount = cartItemService.getSumAmount(cart.getId());

        cart.setTotalAmount(nAmount);

        cartService.save(cart);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItem.getCart());

        return new ResponseEntity<>(newCartItem.toCartItemInfoDTOWithCountQuantity(totalCartItemQuantity),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> doDelete(@PathVariable Long cartItemId){
        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()) {
            throw new DataInputException("CartItem ID invalid!");
        }

        cartItemService.remove(cartItemId);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItemOptional.get().getCart());

        Map<String, Long> results = new HashMap<>();

        results.put("totalCartItemQuantity", totalCartItemQuantity);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
