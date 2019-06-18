package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    private ProfileRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext applicationContext = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        controller = applicationContext.getBean(ProfileRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        if (!userId.isEmpty()) {
            int userIdNumber = Integer.valueOf(userId);
            User user = controller.get(userIdNumber);
            if (user != null) {
                SecurityUtil.setAuthUserId(userIdNumber);
                response.sendRedirect("meals?user=" + user.getName());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = controller.getAll();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}