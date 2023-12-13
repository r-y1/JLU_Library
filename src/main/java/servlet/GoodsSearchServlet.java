package servlet;

import service.GoodsService;
import entity.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "goods_search",urlPatterns = "/goods_search")
public class GoodsSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入GoodsSearchServlet的doPost");
        doGet(request,response);
    }

    private GoodsService gService = new GoodsService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String keyword = request.getParameter("keyword");
        System.out.println("搜索中接收到的keyword："+keyword);
        int pageNumber = 1;
        if(request.getParameter("pageNumber") != null) {
            try {
                pageNumber=Integer.parseInt(request.getParameter("pageNumber") ) ;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(pageNumber<=0)
        {
            pageNumber=1;
        }
        Page p =gService.getSearchGoodsPage(keyword,pageNumber);

        if(p.getTotalPage()==0)
        {
            p.setTotalPage(1);
            p.setPageNumber(1);
        }
        else {
            if(pageNumber>=p.getTotalPage()+1)
            {
                p =gService.getSearchGoodsPage(keyword,pageNumber);
            }
        }
        request.setAttribute("p", p);
        request.setAttribute("keyword", URLEncoder.encode(keyword,"utf-8"));
        request.getRequestDispatcher("/goods_search.jsp").forward(request, response);
    }

}
