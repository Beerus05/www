package vn.edu.iuh.sessioncart.web;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.sessioncart.model.Product;
import vn.edu.iuh.sessioncart.service.ProductCatalog;
import vn.edu.iuh.sessioncart.service.ShoppingCart;
import vn.edu.iuh.sessioncart.session.UserSession;
import java.io.IOException;
import java.util.Optional;
@WebServlet("/cart/add")
public class CartServlet extends HttpServlet {
    @Inject
    private ProductCatalog productCatalog;
    @Inject
    private UserSession userSession;
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        if (!userSession.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() +
                    "/login");
            return;
        }
        try {
            long productId =
                    Long.parseLong(request.getParameter("productId"));
//productCatalog.findById(productId).ifPresent(userSession.getShoppingCart()::add);
// Bước 1: Tìm sản phẩm theo ID
            Optional<Product> optionalProduct =
                    productCatalog.findById(productId);
// Bước 2: Kiểm tra sản phẩm có tồn tại hay không
            if (optionalProduct.isPresent()) {
// Bước 3: Lấy Product ra khỏi Optional
                Product product = optionalProduct.get();
// Bước 4: Lấy giỏ hàng của session hiện tại
                ShoppingCart shoppingCart =
                        userSession.getShoppingCart();
// Bước 5: Thêm sản phẩm vào giỏ hàng
                shoppingCart.add(product);
            }
        } catch (NumberFormatException ignored) {
// ID không hợp lệ: không thay đổi giỏ hàng.
        }
// PRG: tránh thêm lại sản phẩm khi người dùng refresh trang.
                response.sendRedirect(request.getContextPath() +
                        "/products");
    }
}