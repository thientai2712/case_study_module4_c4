package com.cg.service.cart;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.repository.CartItemRepository;
import com.cg.repository.CartRepository;
import com.cg.repository.OrderItemRepository;
import com.cg.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public void softDelete(Cart cart) {

    }

    @Override
    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public boolean checkout(User user) {
        boolean success = false;

        try {
            Optional<Cart> cartOptional = cartRepository.findByUser(user);

            if (!cartOptional.isPresent()) {
                throw new DataInputException("Invalid userID!");
            }

            Cart cart = cartOptional.get();

            Order order = cart.toOrder();
            Order newBill = orderRepository.save(order);

            List<CartItem> cartItems = cartItemRepository.findByCart(cart);

            for (CartItem item : cartItems) {
                OrderItem orderItem = item.toOrderItem(newBill);

                orderItemRepository.save(orderItem);

                cartItemRepository.deleteById(item.getId());
            }

            cartRepository.deleteById(cart.getId());

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}
