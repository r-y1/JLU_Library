package servlet.admin;

import entity.Type;
import service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "admin_type_editshow",urlPatterns = "/admin/type_editshow")
public class AdminTypeEditShowServlet extends HttpServlet {

    private TypeService tService = new TypeService();
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取要修改的type
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("id："+id);

        Type tp = tService.select(id);
        System.out.println("得到name："+tp.getName());
        System.out.println("得到Encodename："+tp.getEncodeName());
        request.setAttribute("tp",tp);
        // 获取typelist
//        List<Type> list= tService.GetAllType();
//        request.setAttribute("list", list);

        request.getRequestDispatcher("/admin/type_edit.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
