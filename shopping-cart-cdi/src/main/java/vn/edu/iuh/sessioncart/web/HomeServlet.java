package vn.edu.iuh.sessioncart.web;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.sessioncart.session.UserSession;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Inject
    private UserSession userSession;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập (tùy chọn: nếu muốn bắt buộc phải đăng nhập mới xem được)
        if (!userSession.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Forward ngầm sang file home.jsp trong WEB-INF/views/
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}