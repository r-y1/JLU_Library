package servlet;

import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "user_register",urlPatterns = "/user_rigister")
public class UserRegisterServlet extends HttpServlet {

    private UserService uService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));
        System.out.println("在UserRegisterServlet中创建的user："+user.toString());
//        try {
//            BeanUtils.copyProperties(user, request.getParameterMap());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        if(uService.register(user)) {
            request.setAttribute("msg", "注册成功，请登录！");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else {
            request.setAttribute("msg", "用户名或邮箱重复，请重新填写！");
            request.getRequestDispatcher("user_register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入UserRegisterServlet的doGet");
    }
}
