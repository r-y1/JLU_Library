package servlet.admin;

import entity.Order;
import entity.OrderItem;
import service.GoodsService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "admin_order_status",urlPatterns = "/admin/order_status")
public class AdminOrderStatusServlet extends HttpServlet {

    private OrderService oService = new OrderService();
    private GoodsService gService = new GoodsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//订单ID
        int status = Integer.parseInt(request.getParameter("status"));
        oService.updateStatus(id, status);
        System.out.println("借阅ID"+id+"状态转变为"+status);
//        改：
//        Order o = oService.getOrderById(id);//先获取订单
//        System.out.println("数量"+o.getName());
//        Map<Integer, OrderItem> orderItemMap = o.getItemMap();//再获取订单项中图书ID和订单项OrderItem

        //从ORDERITEM表中获取当前订单ID的所有对应的图书ID和数量
        List<OrderItem> itemlist = oService.selectAllItemById(id);//根据订单ID获取所欲的订单项

        if(status==3){
//            System.out.println("123");
//            对借阅操作中进行相应商品库存删减
            if(itemlist==null){
                System.out.println("list为空");
            }
            for (OrderItem item : itemlist){//对该订单中的所有订单项遍历
                System.out.println("商品Name"+item.getGoodsName()+",数量："+item.getAmount());
//                System.out.println(",数量："+item.getAmount());
                gService.delStock(item.getGoodsName(),item.getAmount());
            }
        }
        else if(status==4){
            //归还操作，对借阅操作中的商品库存增加
            if(itemlist==null){
                System.out.println("list为空");
            }
            for (OrderItem item : itemlist){//对该订单中的所有订单项遍历
                System.out.println("商品Name"+item.getGoodsName()+",数量："+item.getAmount());
//                System.out.println(",数量："+item.getAmount());
                gService.addStock(item.getGoodsName(),item.getAmount());
            }
        }
//        gService.
        request.getRequestDispatcher("/admin/order_list?status="+status).forward(request, response);
    }
}
