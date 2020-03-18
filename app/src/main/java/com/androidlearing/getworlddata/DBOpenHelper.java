package com.androidlearing.getworlddata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBOpenHelper {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://39.97.109.245:3306/yiqing?characterEncoding=utf-8";
    private static String user = "root";//用户名
    private static String password = "abc456";//密码

    public static Connection getConn(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url,user,password);//获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static List<WorldData>  searchDataByCountry(String condition,String country_name){
        List<WorldData> list = new ArrayList<>();
        Connection connection = getConn();
        String sql = "";
        //System.out.println(condition);
        if(condition.equals("国家")){
            sql = "select * from worlddata where countryname like ?";
        }
        if(condition.equals("时间")){
            sql = "select * from worlddata where lastUpdateTime like ?";
        }
        System.out.println(country_name);
        if(connection !=null){
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps!=null){
                    ps.setString(1,"%"+country_name+"%");
                    ResultSet rs = ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            WorldData worldData = new WorldData();
                            worldData.setId(rs.getInt("id"));
                            worldData.setCountryname(rs.getString("countryname"));
                            worldData.setConfirmed(rs.getString("confirmed"));
                            worldData.setSuspected(rs.getString("suspected"));
                            worldData.setDead(rs.getString("dead"));
                            worldData.setHealed(rs.getString("healed"));
                            worldData.setLastUpdateTime(rs.getString("lastUpdateTime"));
                            list.add(worldData);
                        }
                        connection.close();
                        ps.close();
                        return list;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }

    }
}
