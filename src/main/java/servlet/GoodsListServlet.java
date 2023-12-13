package servlet;

import entity.Page;
import entity.Type;
import service.GoodsService;
import service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "goods_List", urlPatterns = "/goods_list")
public class GoodsListServlet extends HttpServlet {
    private GoodsService gService = new GoodsService();
    private TypeService tService = new TypeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入GoodsListServlet的doPost");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = 0;
        if (request.getParameter("typeid") != null) {
            id = Integer.parseInt(request.getParameter("typeid"));
        }


        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            try {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            } catch (Exception e) {
            }
        }
        System.out.println("GoodsListServlet：得到pageNumber" + pageNumber);


        Type t = null;
        if (id != 0) {
            t = tService.selectTypeNameByID(id);
        }
        System.out.println("GoodsListServlet：得到id" + id);
        if(t!=null)
            System.out.println("GoodsListServlet：根据id得到type" + t.getName());

        request.setAttribute("t", t);
        //List<Goods> list=gService.selectGoodsByTypeID(id,1,8);
        //request.setAttribute("goodsList",list);
        if (pageNumber <= 0)
            pageNumber = 1;
        Page p = gService.selectPageByTypeID(id, pageNumber);
        System.out.println("1-GoodsListServlet：根据id得到page:  "+p.toString());

        if (p.getTotalPage() == 0) {
            p.setTotalPage(1);
            p.setPageNumber(1);
        } else {
            if (pageNumber >= p.getTotalPage() + 1) {
                p = gService.selectPageByTypeID(id, p.getTotalPage());
            }
        }
        System.out.println("2-GoodsListServlet：根据id得到page:  "+p.toString());
        request.setAttribute("p", p);
        request.setAttribute("id", String.valueOf(id));
        request.getRequestDispatcher("/goods_list.jsp").forward(request, response);
    }
}
