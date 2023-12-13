package dao;

import entity.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DBUtil;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void addUser(User user) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "insert into users(username,email,password,name,phone,address,isadmin,isvalidate) values(?,?,ENCRYPT(?),?,?,?,?,?) ";
        r.update(sql,user.getUsername(),user.getEmail(),user.getPassword(),user.getName(),user.getPhone(),user.getAddress(),user.isIsadmin(),user.isIsvalidate());
    }
    public boolean isUsernameExist(String username) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "select id,username,email,DECRYPT(password),name,phone,address,isadmin,isvalidate from users where username = ? ";
        User u = r.query(sql, new BeanHandler<User>(User.class),username);
        if(u==null) {
            return false;
        }else {
            return true;
        }
    }
    public boolean isEmailExist(String email) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "select id,username,email,DECRYPT(password),name,phone,address,isadmin,isvalidate from TEST.users where email = ? ";
        User u = r.query(sql, new BeanHandler<User>(User.class),email);
        if(u==null) {
            return false;
        }else {
            return true;
        }
    }
    public User selectByUsernamePassword(String username,String password) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "select id,username,email,DECRYPT(password),name,phone,address,isadmin,isvalidate from users where username=? and password=ENCRYPT(?) ";
//        String sql = "select id,username,email,password,name,phone,address,isadmin,isvalidate from USERS where username=? and password=? ";

        return r.query(sql, new BeanHandler<User>(User.class),username,password);
    }
    public User selectByEmailPassword(String email,String password) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "select id,username,email,DECRYPT(password),name,phone,address,isadmin,isvalidate from users where email=? and password=ENCRYPT(?) ";
//        String sql = "select id,username,email,password,name,phone,address,isadmin,isvalidate from users where email=? and password=? ";
        return r.query(sql, new BeanHandler<User>(User.class),email,password);
    }
    public User selectById(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "select id,username,email,DECRYPT(password),name,phone,address,isadmin,isvalidate from users where id=? ";
        return r.query(sql, new BeanHandler<User>(User.class),id);
    }

    public void updateUserAddress(User user) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql ="update users set name = ?,phone=?,address=? where id = ? ";
        r.update(sql,user.getName(),user.getPhone(),user.getAddress(),user.getId());
    }
    public void updatePwd(User user) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql ="update users set password = ENCRYPT(?) where id = ? ";
        r.update(sql,user.getPassword(),user.getId());
    }
    public int selectUserCount() throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "select count(*) from users ";
        return r.query(sql, new ScalarHandler<Number>()).intValue();
    }
    public List selectUserList(int pageNo, int pageSize) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        // *********更改
        String sql = "select id,username,email,DECRYPT(password),name,phone,address,isadmin,isvalidate from users ";

        return r.query(sql, new BeanListHandler<User>(User.class));
    }
    public void delete(int id) throws SQLException {
        QueryRunner r = new QueryRunner(DBUtil.getDataSource());
        String sql = "delete from users where id = ? ";
        r.update(sql,id);
    }
}
