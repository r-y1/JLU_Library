package service;

import dao.*;
import entity.*;
import utils.*;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderDao oDao = new OrderDao();
    public void addOrder(Order order) {
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            // 添加order
            oDao.insertOrder(con, order);
            int id = oDao.getLastInsertId(con);
            order.setId(id);
            for(OrderItem item : order.getItemMap().values()) {
                // 添加订单项
                oDao.insertOrderItem(con, item);
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if(con!=null)
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        }
    }
    public List<Order> selectAll(int userid){
        List<Order> list=null;
        try {
            list = oDao.selectAll(userid);
            for(Order o :list) {
                List<OrderItem> l = oDao.selectAllItem(o.getId());
                o.setItemList(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Page getOrderPage(int status,int pageNumber) {
        Page p = new Page();
        p.setPageNumber(pageNumber);
        int pageSize = 10;
        int totalCount = 0;
        try {
            totalCount = oDao.getOrderCount(status);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(pageSize, totalCount);
        List list=null;
        try {
            list = oDao.selectOrderList(status, pageNumber, pageSize);
            for(Order o :(List<Order>)list) {
                List<OrderItem> l = oDao.selectAllItem(o.getId());
                o.setItemList(l);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setList(list);
        return p;
    }
    public void updateStatus(int id,int status) {
        try {
            oDao.updateStatus(id, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    //    获取Order的itemMap
//    public Map<Integer, OrderItem> getItemMap(int id){
//        try{
//            return oDao.getOrderItemMap(id);
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    public void delete(int id) {
        Connection con = null;
        try {
            con = DBUtil.getDataSource().getConnection();
            con.setAutoCommit(false);

            oDao.deleteOrderItem(con, id);
            oDao.deleteOrder(con, id);
            con.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if(con!=null)
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        }


    }
//    改：获取订单
    public Order getOrderById(int id) {
        Order o=null;
        try {
            o = oDao.getOrderById(id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return o;
    }
//    public List<OrderItem> getItemListById(int id){//根据订单ID获取订单项
//        try{
//            return oDao.getItemListById(id);
//        } catch(SQLException e){
//            e.printStackTrace();
//        }

    public List<OrderItem> selectAllItemById(int orderid){//根据订单ID获取所有订单项
        List<OrderItem> list = null;
        try{
            list = oDao.selectAllItemById(orderid);
        }catch  (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

}

