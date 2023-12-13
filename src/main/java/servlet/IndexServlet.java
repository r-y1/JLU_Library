package servlet;

import service.GoodsService;
import service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "IndexServlet",urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
	TypeService tsService=new TypeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入IndexServlet的DoPost");
    }

    private GoodsService gService=new GoodsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取图书相关信息，ID、name、cover(封面)、price
        Map<String,Object> ScrollGood=gService.getScrollGood();
        request.setAttribute("scroll",ScrollGood);

        // 推荐图书（推荐板块3）
        List<Map<String,Object>>newList=gService.getGoodsList(3);
        request.setAttribute("newList",newList);

        // 推荐图书（推荐板块2）
        List<Map<String,Object>>hotList=gService.getGoodsList(2);
        request.setAttribute("hotList",hotList);
        request.getSession().setAttribute("typeList",tsService.GetAllType());
        //response.sendRedirect("index.jsp");
        request.getRequestDispatcher("home_page.jsp").forward(request,response);
    }
}
