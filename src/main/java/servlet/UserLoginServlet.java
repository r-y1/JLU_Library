package servlet;

import entity.User;
import service.TypeService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "user_login",urlPatterns = "/user_login")
public class UserLoginServlet extends HttpServlet {
    private UserService uService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        改：设置解码方式，便于中文用户名的解码
        request.setCharacterEncoding("UTF-8");

        String ue = request.getParameter("ue");
        String password = request.getParameter("password");
        User user = uService.login(ue, password);
        System.out.println("得到表单数据In:UserLoginServlet");
        System.out.println("username:"+ue+"   pwd:"+password);
        if(user==null) {
//            System.out.println("UserLoginServlet中user为null");
            request.setAttribute("failMsg", "用户名、邮箱或者密码错误，请重新登录！");
            request.getSession().invalidate();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }else {
        	if(user.isIsadmin()) {
//                System.out.println("UserLoginServlet中用户名、邮箱或者密码错误");
        		request.setAttribute("failMsg", "用户名、邮箱或者密码错误，请重新登录！");
        		request.getSession().invalidate();
                request.getRequestDispatcher("/index.jsp").forward(request, response);
        	}else {
                System.out.println("UserLoginServlet中用户名密码正确");
        		request.getSession().setAttribute("user", user);
                TypeService tsService=new TypeService();
//                改：增加了图书类别
                request.getSession().setAttribute("typeList",tsService.GetAllType());
//                request.setAttribute("user", user);
//                System.out.println("user.name"+user.getName());
                request.getRequestDispatcher("/user_center.jsp").forward(request, response);
//                request.getRequestDispatcher("/t.jsp").forward(request, response);
        	}
            
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
