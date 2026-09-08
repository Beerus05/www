package vn.edu.iuh.sessioncart.service;
import jakarta.enterprise.context.ApplicationScoped;
/** Business service minh họa. Trong hệ thống thật, dữ liệu đến
 từ DB và mật khẩu phải được hash. */
@ApplicationScoped
public class AuthenticationService {
    public boolean authenticate(String username, String password) {
        return "student".equals(username) &&
                "123456".equals(password);
    }
}