package com.example.kiemtra1.Service;

import com.example.kiemtra1.Model.CartItem;
import com.example.kiemtra1.Model.Order;
import com.example.kiemtra1.Model.Product;
import com.example.kiemtra1.Repo.CartitemRepo;
import com.example.kiemtra1.Repo.MemberAccountRepo;
import com.example.kiemtra1.Repo.OderRepo;
import com.example.kiemtra1.Repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartItemService {
    public Logger logger = LoggerFactory.getLogger(CartItem.class);
    @Autowired
    public CartitemRepo cartitemRepo;
    @Autowired
    public OderRepo oderRepo;
    @Autowired
    public MemberAccountRepo memberAccountRepo;
    @Autowired
    public ProductRepo productRepo;

    public CartItemService(CartitemRepo cartitemRepo) {
        this.cartitemRepo = cartitemRepo;
    }

    // save gio hang
    public CartItem save1(CartItem cartItem, List<Long> productID) {
        try {
            for (Long productId : productID) {
                Product product = productRepo.findById(productId).orElse(null);
                if (product != null) {
                    cartItem.getProducts().add(product);
                }
            }
            logger.info("Products added to cartItem: {}", cartItem.getProducts());
            Order order = new Order();
            order.setCartItems(cartItem);
            order.setOrderAmount(cartItem.getPriceTotal());
            oderRepo.save(order).getCartItems();
            logger.info("Order saved: " + order);
            return cartitemRepo.save(cartItem);
        } catch (HttpMessageNotWritableException e) {
            logger.error("Error while saving cartItem :" + e);
        }
        return null;
    }

    public List<CartItem> findAllCartItem() {
        return cartitemRepo.findAll();
    }

    public CartItem updateCartItem(Long id, CartItem cartItem) {
        return cartitemRepo.findById(id)
                .map(cartItem1 -> {
                    cartItem1.setQuantity(cartItem.getQuantity());
                    logger.info("update CartItem success");
                    return cartitemRepo.save(cartItem1);
                })
                .orElseGet(() -> {
                    cartItem.setId(cartItem.getId());
                    return cartitemRepo.save(cartItem);
                });
    }

    // deleteCartItem
    public void deleteCartItem(Long id) {
        cartitemRepo.deleteCartItemById(id);
    }
}
