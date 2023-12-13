package servlet.admin;

import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "admin_user_add",urlPatterns = "/admin/user_add")
public class AdminUserAddServlet extends HttpServlet {
    private UserService uService = new UserService();
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));
//        try {
//            BeanUtils.copyProperties(user, request.getParameterMap());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        System.out.println("在AdminUserAddServlet中添加的用户："+user.toString());
        if(uService.register(user)) {
            request.setAttribute("msg", "客户添加成功！");
            request.getRequestDispatcher("/admin/user_list").forward(request, response);
        }else {
            request.setAttribute("failMsg", "用户名或邮箱重复，请重新填写！");
            request.setAttribute("u",user);
            request.getRequestDispatcher("/admin/user_add.jsp").forward(request, response);
        }
    }
}
