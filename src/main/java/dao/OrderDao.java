package dao;

import entity.*;
import org.apache.commons.dbutils.*;
import utils.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.dbutils.handlers.*;

public class OrderDao {
    public void insertOrder(Connection con, Order order) throws SQLException {
        QueryRunner r = new QueryRunner();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String formatdate = sdf.format(order.getDatetime());//获取格式化日期，带有时分秒

        //order.getDatetime()
        //to_date(?,'yyyy-mm-dd hh24:mi:ss')
        String sql = "insert into orders(total,amount,status,paytype,name,phone,address,datetime,user_id) values(?,?,?,?,?,?,?,to_date(?, 'yyyy-mm-dd hh24:mi:ss'),?) ";
        r.update(con,sql,
                order.getTotal(),order.getAmount(),order.getStatus(),
                order.getPaytype(),order.getName(),order.getPhone(),
                order.getAddress(), formatdate, order.getUser().getId() );
    }
    public int getLastInsertId(Connection con) throws SQLException {
        QueryRunner r = new QueryRunner();
       // String sql = "select last_insert_id()";
        String sql = "select max(id) from orders ";

        //BigInteger bi = r.query(con, sql, new ScalarHandler<BigInteger>());
        //return Integer.parseInt(bi.toString());
        return r.query(con, sql, new ScalarHandler<Number>()).intValue();
    }
    public void insertOrderItem(Connection con, OrderItem item) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql ="insert into orderitem(price,amount,goods_id,order_id) values(?,?,?,?) ";
        r.update(con,sql,item.getPrice(),item.getAmount(),item.getGoods().getId(),item.getOrder().getId());
    }
    public List<Order> selectAll(int userid) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select * from orders where user_id=? order by datetime desc ";
        return r.query(sql, new BeanListHandler<Order>(Order.class),userid);
    }
    public List<OrderItem> selectAllItem(int orderid) throws SQLException{
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select i.id,i.price,i.amount,g.name from orderitem i,goods g where order_id=? and i.goods_id=g.id ";
        return r.query(sql, new BeanListHandler<OrderItem>(OrderItem.class),orderid);
    }
    public int getOrderCount(int status) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "";
        if(status==0) {
            sql = "select count(*) from orders ";
            return r.query(sql, new ScalarHandler<Number>()).intValue();
        }else {
            sql = "select count(*) from orders where status=? ";
            return r.query(sql, new ScalarHandler<Number>(),status).intValue();
        }
    }
    public List<Order> selectOrderList(int status, int pageNumber, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        if(status==0) {
            String sql = "select o.id,o.total,o.amount,o.status,o.paytype,o.name,o.phone,o.address,o.datetime,u.username from orders o,users u where o.user_id=u.id order by o.datetime desc ";
            return r.query(sql, new BeanListHandler<Order>(Order.class));
        }else {
            String sql = "select o.id,o.total,o.amount,o.status,o.paytype,o.name,o.phone,o.address,o.datetime,u.username from orders o,users u where o.user_id=u.id and o.status=? order by o.datetime desc";
            //where rownum <=10 
            return r.query(sql, new BeanListHandler<Order>(Order.class),status);
        }
    }
    public void updateStatus(int id,int status) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql ="update orders set status=? where id = ? ";
        r.update(sql,status,id);
    }
//    public Map<Integer, OrderItem> getOrderItemMap(int id){
//        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
//        String sql ="select * from orders where id = ? ";
//    }

    public void deleteOrder(Connection con ,int id) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql ="delete from orders where id = ? ";
        r.update(con,sql,id);
    }
    public void deleteOrderItem(Connection con ,int id) throws SQLException {
        QueryRunner r = new QueryRunner();
        String sql ="delete from orderitem where order_id=? ";
        r.update(con,sql,id);
    }
    //改：
    public Order getOrderById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select o.id,o.total,o.amount,o.status,o.paytype,o.name,o.phone,o.address,o.datetime ,o.user_id from orders o where o.id = ? ";
        return r.query(sql, new BeanHandler<Order>(Order.class),id);
    }
//
//    public Map<Integer,Integer> getItemListById(int id){//根据订单ID获取订单项的货物ID以及对应的数量
//        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
//        String sql = "selcet oi.goods_id,oi.amount from orderitem oi where oi.order_id=?";
//        return r.query(sql,new BeanHandler<>())
//    }
    public List<OrderItem> selectAllItemById(int orderid) throws SQLException{//根据订单ID获取所有订单项
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
//        由于给OrderItem添加了一个新成员变量，goodsId
        String sql = "select i.id,i.price,i.amount,g.name,i.goods_id from orderitem i,goods g where order_id=? and i.goods_id=g.id ";
        return r.query(sql, new BeanListHandler<OrderItem>(OrderItem.class),orderid);
    }
}
