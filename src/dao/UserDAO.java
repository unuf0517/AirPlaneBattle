package dao;

import model.AirplanebattleDO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.DBUtil.driver;
import static util.DBUtil.pwd;
import static util.DBUtil.root;
import static util.DBUtil.url;

public class UserDAO {
    public static AirplanebattleDO login(String acc, String pw){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            Class.forName(driver);
            conn= DriverManager.getConnection(url,root,pwd);
            String sql="select * from airplanebattle where account=? and password=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, acc);
            ps.setString(2, pw);
            rs=ps.executeQuery();
            if(rs.next()){
                return new AirplanebattleDO(rs.getLong("id"),rs.getString("nickname"),rs.getString("account"),rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            try{
                if(ps!=null){
                    ps.close();
                }
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        return null;

    }


    /**
     * @description: 注册方法
     * @param na 昵称
     * @param ac 账号
     * @param pw 密码
     * @return: int 1表示账号已存在，2表示注册成功，3表示注册失败
     */
    public static int enroll(String na,String ac,String pw){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(
                    url,
                    root,
                    pwd);
            String sql="select * from airplanebattle where account=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,ac);
            rs=ps.executeQuery();
            if (rs.next()) {
                return 1;
            }
            String sqlen="insert into airplanebattle(nickname,account,password) values(?,?,?)";
            ps=conn.prepareStatement(sqlen);
            ps.setString(1,na);
            ps.setString(2,ac);
            ps.setString(3,pw);
            int a=ps.executeUpdate();
            return a>0? 2:3;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.释放资源  自下而上
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 3;
    }

}
