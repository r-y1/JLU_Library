package dao;

import entity.Goods;
import entity.Recommend;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.*;
import utils.DBUtil;

import java.sql.SQLException;
import java.util.*;

public class GoodsDao {
    //select g.id,g.name,g.cover,g.price,t.name typename from recommend r,goods g,type t where type=2 and r.goods_id=g.id and g.type_id=t.id
    public List<Map<String,Object>> getGoodsList(int recommendType) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql="select g.id,g.name,g.cover,g.price,t.name typename from recommend r,goods g,type t where type=? and r.goods_id=g.id and g.type_id=t.id";
        return r.query(sql, new MapListHandler(),recommendType);
    }

    public Map<String,Object> getScrollGood()throws SQLException{
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql="select g.id,g.name,g.cover,g.price  from recommend r,goods g where type=1 and r.goods_id=g.id";
        return r.query(sql, new MapHandler());
    }
    public List<Goods> selectGoodsByTypeID(int typeID,int pageNumber,int pageSize) throws SQLException {
        if(typeID==0)
        {
            String sql="select * from goods ";
            QueryRunner r=new QueryRunner(DBUtil.getDataSource());
            return  r.query(sql,new BeanListHandler<Goods>(Goods.class));
        }
        else
        {
            String sql="select * from goods where type_id=? ";
            QueryRunner r=new QueryRunner(DBUtil.getDataSource());
            return  r.query(sql,new BeanListHandler<Goods>(Goods.class),typeID);
        }
    }
    public int getCountOfGoodsByTypeID(int typeID) throws SQLException {
        String sql="";
        QueryRunner r=new QueryRunner(DBUtil.getDataSource());
        if(typeID==0)
        {
            sql="select count(*) from goods";
            return r.query(sql,new ScalarHandler<Number>()).intValue();
        }
        else
        {
            sql="select count(*) from goods where type_id=?";
            return r.query(sql,new ScalarHandler<Number>(),typeID).intValue();
        }
    }
    public List<Goods> selectGoodsbyRecommend(int type,int pageNumber,int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        if(type==0) {
            //当不添加推荐类型限制的时候
            String sql = " select g.id,g.name,g.cover,g.image1,g.image2,g.intro,g.price,g.stock,t.name typename from goods g,type t where g.type_id=t.id order by g.id ";
            // where rownum <= 8 
            return r.query(sql, new BeanListHandler<Goods>(Goods.class));

        }

        String sql = " select g.id,g.name,g.cover,g.image1,g.image2,g.intro,g.price,g.stock,t.name typename from goods g,recommend r,type t where g.id=r.goods_id and g.type_id=t.id and r.type=? order by g.id ";
        return r.query(sql, new BeanListHandler<Goods>(Goods.class), type);
    }
    public int getRecommendCountOfGoodsByTypeID(int type) throws SQLException {
        if(type==0)return getCountOfGoodsByTypeID(0);
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select count(*) from recommend where type=?";
        return r.query(sql, new ScalarHandler<Number>(),type).intValue();
    }
    public Goods getGoodsById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select g.id,g.name,g.cover,g.image1,g.image2,g.price,g.intro,g.stock,t.id typeid,t.name typename from goods g,type t where g.id = ? and g.type_id=t.id ";
        return r.query(sql, new BeanHandler<Goods>(Goods.class),id);
    }
    public int getSearchCount(String keyword) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select count(*) from goods where name like ? ";
        return r.query(sql, new ScalarHandler<Number>(),"%"+keyword+"%").intValue();
    }
    public List<Goods> selectSearchGoods(String keyword, int pageNumber, int pageSize) throws SQLException{
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select * from goods where name like ?  ";
        return r.query(sql, new BeanListHandler<Goods>(Goods.class),"%"+keyword+"%");
    }
    public boolean isScroll(Goods g) throws SQLException {
        return isRecommend(g, 1);
    }
    public boolean isHot(Goods g) throws SQLException {
        return isRecommend(g, 2);
    }
    public boolean isNew(Goods g) throws SQLException {
        return isRecommend(g, 3);
    }
    private boolean isRecommend(Goods g,int type) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select * from recommend where type=? and goods_id=?";
        Recommend recommend = r.query(sql, new BeanHandler<Recommend>(Recommend.class),type,g.getId());
        if(recommend==null) {
            return false;
        }else {
            return true;
        }
    }
    public void addRecommend(int id,int type) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "insert into recommend(type,goods_id) values(?,?)";
        r.update(sql,type,id);
    }
    public void removeRecommend(int id,int type) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "delete from recommend where type=? and goods_id=?";
        r.update(sql,type,id);
    }
    public void insert(Goods g) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "insert into goods(name,cover,image1,image2,price,intro,stock,type_id) values(?,?,?,?,?,?,?,?)";
        r.update(sql,g.getName(),g.getCover(),g.getImage1(),g.getImage2(),g.getPrice(),g.getIntro(),g.getStock(),g.getType().getId());
    }
    public void update(Goods g) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "update goods set name=?,cover=?,image1=?,image2=?,price=?,intro=?,stock=?,type_id=? where id=?";
        r.update(sql,g.getName(),g.getCover(),g.getImage1(),g.getImage2(),g.getPrice(),g.getIntro(),g.getStock(),g.getType().getId(),g.getId());
    }
    public void delete(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "delete from goods where id = ?";
        r.update(sql,id);
    }

    //查询奶茶销量,先获得所有奶茶
    public List<Goods> getGoodsSales() throws SQLException{
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        //销量作为库存项
        String sql = "select goods.name,count(orderitem.goods_id) as stock from orderitem,goods where (orderitem.goods_id = goods.id) group by orderitem.goods_id,goods.name";
        return r.query(sql, new BeanListHandler<Goods>(Goods.class));
    }


//    改：
    public Goods getGoodsByName(String name) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select g.id,g.name,g.cover,g.image1,g.image2,g.price,g.intro,g.stock,t.id typeid,t.name typename from goods g,type t where g.name = ? and g.type_id=t.id ";
        return r.query(sql, new BeanHandler<Goods>(Goods.class),name);
    }

//    借阅时减少更新销量
    public void delStock(String name,int amount)throws  SQLException{//根据货物名称
//        Goods oriGoods = getGoodsById(id);//获取原本的货物
        Goods oriGoods = getGoodsByName(name);//获取原本的货物
        int oriAmount = oriGoods.getStock();
        int afterAmount = oriAmount-amount;//减去后的数量

        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "update goods set stock=? where name = ?";
        r.update(sql,afterAmount,name);
    }

//    借阅时减少更新销量
    public void addStock(String name,int amount)throws  SQLException{//根据货物名称
//        Goods oriGoods = getGoodsById(id);//获取原本的货物
        Goods oriGoods = getGoodsByName(name);//获取原本的货物
        int oriAmount = oriGoods.getStock();
        int afterAmount = oriAmount+amount;//增加后的数量

        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "update goods set stock=? where name = ?";
        r.update(sql,afterAmount,name);
    }
}
