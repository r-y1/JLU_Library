package servlet;

import entity.Goods;
import service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "goods_detail",urlPatterns = "/goods_detail")
public class GoodsDetailServlet extends HttpServlet {

    private GoodsService gService = new GoodsService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入GoodsDetailServlet的doPost");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Goods g = gService.getGoodsById(id);
//        System.out.println("在GoodsDetailServlet中得到您所点击的产品的details，其name："+g.getName());
//        System.out.println("在GoodsDetailServlet中得到您所点击的产品的details，其cover路径为："+g.getCover());
//        System.out.println("在GoodsDetailServlet中得到您所点击的产品的details，其img1路径为："+g.getImage1());
//        System.out.println("在GoodsDetailServlet中得到您所点击的产品的details，其img2路径为："+g.getImage2());
        request.setAttribute("g", g);
        request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
    }
}
