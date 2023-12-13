package servlet;

import dao.GoodsDao;
import entity.Goods;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GoodsStockChartsServlet",urlPatterns = "/charts")
public class GoodsStockChartsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GoodsDao goodsDao = new GoodsDao();
        List<Goods> goodsList;
        try {
            goodsList = goodsDao.getGoodsSales();
            request.setAttribute("goodsList", goodsList);
            request.getRequestDispatcher("/charts.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
