package vn.edu.iuh.sessioncart.countCus;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class ActiveUserListener implements ServletContextListener,
        HttpSessionListener {
    // Tên attribute lưu trong Application Scope
    private static final String ACTIVE_USERS_ATTRIBUTE = "activeUsersCount";
    // Khởi tạo biến đếm = 0 khi server (application) bắt đầu chạy
    private static final AtomicInteger activeSessions = new AtomicInteger(0);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(ACTIVE_USERS_ATTRIBUTE,
                activeSessions);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
// Dọn dẹp nếu cần khi server tắt
        sce.getServletContext().removeAttribute(ACTIVE_USERS_ATTRIBUTE);
    }
    @Override
    public void sessionCreated(HttpSessionEvent se) {
// Mỗi khi có session mới (1 người dùng mới truy cập), tăng biến đếm lên 1
        AtomicInteger activeUsers = (AtomicInteger)
                se.getSession().getServletContext().getAttribute(ACTIVE_USERS_ATTRIBUTE);
        if (activeUsers != null) {
            activeUsers.incrementAndGet();
        }
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
// Khi session timeout hoặc bị hủy (người dùng đăng xuất/thoát), giảm biến đếm đi 1
        AtomicInteger activeUsers = (AtomicInteger)
                se.getSession().getServletContext().getAttribute(ACTIVE_USERS_ATTRIBUTE);
        if (activeUsers != null && activeUsers.get() > 0) {
            activeUsers.decrementAndGet();
        }
    }
}