package servlet;

import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "user_changeaddress",urlPatterns = "/user_changeaddress")
public class UserChangeAddressServlet extends HttpServlet {

    private UserService uService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        User loginUser = (User) request.getSession().getAttribute("user");
//        try {
//            BeanUtils.copyProperties(loginUser, request.getParameterMap());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String add = request.getParameter("address");
        System.out.println("在UserChangeAdd中读取到name:"+name+"  phone:"+phone+"   add:"+add);
        System.out.println("1在UserChangeAdd中读取到user.add"+loginUser.getAddress());
        loginUser.setName(name);
        loginUser.setPhone(phone);
        loginUser.setAddress(add);
        uService.updateUserAddress(loginUser);
        System.out.println("2在UserChangeAdd中读取到user.add"+loginUser.getAddress());
        request.setAttribute("msg", "收件信息更新成功！");
        request.getRequestDispatcher("/user_center.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
