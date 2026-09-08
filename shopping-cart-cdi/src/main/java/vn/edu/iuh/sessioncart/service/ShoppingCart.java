package vn.edu.iuh.sessioncart.service;
import vn.edu.iuh.sessioncart.model.CartItem;
import vn.edu.iuh.sessioncart.model.Product;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/** Business object thuộc về đúng một người dùng/session. */
public class ShoppingCart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<CartItem> items = new ArrayList<>();
    public void add(Product product) {
        items.stream()
                .filter(item -> item.getProduct().getId() ==
                        product.getId())
                .findFirst()
                .ifPresentOrElse(CartItem::increaseQuantity, () ->
                        items.add(new CartItem(product)));
    }
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    public int getItemCount() {
        return
                items.stream().mapToInt(CartItem::getQuantity).sum();
    }
    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}