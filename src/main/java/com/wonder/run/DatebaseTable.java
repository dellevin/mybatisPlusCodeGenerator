package com.wonder.run;

import java.sql.*;
import java.util.*;


public class DatebaseTable {
    public List<String> tableName(String datasourceName,String url,String port,String username ,String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"", username, password);
        DatabaseMetaData metadata = conn.getMetaData();
        //System.out.println("获取指定的数据库的所有表的类型");
        ResultSet rs1 = metadata.getTables(datasourceName, null, null, null);
        List<String> listTables = new ArrayList<>();
        //int count = 1;
        while (rs1.next()) {
            System.out.println();
            listTables.add(rs1.getString(3));
            //count++;
        }
//        System.out.println(count);
//        for(String value: setTables){
//            System.out.println(value);
//        }
        return  listTables;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "123456");
        DatabaseMetaData metadata = conn.getMetaData();
        /**
         * 获取指定的数据库的所有表的类型,getTables()的第一个参数就是数据库名
         * 因为与MySQL连接时没有指定,这里加上,剩下的参数就可以为null了
         * 第二个参数是模式名称的模式,但是输出也是什么都没有。谁知道告诉我一声
         */
        //System.out.println("获取指定的数据库的所有表的类型");
        ResultSet rs1 = metadata.getTables("test_sql", null, null, null);
        //Map<String, String> map = new HashMap<String, String>();
        Set<String> setTables = new HashSet<String>();
        int count = 0;
        while (rs1.next()) {
            System.out.println();
            //map.put(rs1.getString(1)+"/"+count , rs1.getString(3));
            //System.out.println("数据库名:" + rs1.getString(1));
            //System.out.println("表名: " + rs1.getString(3));
            setTables.add(rs1.getString(3));
            count++;
        }
        System.out.println(count);
        for(String value: setTables){
            System.out.println(value);
        }
//        map.forEach((key, value) -> {
//            System.out.println(key + ":" + value);
//        });
        rs1.close();
        //showTables(conn);
    }

    private static void showTables(Connection conn) throws SQLException {
        ResultSet rs;// 获取数据库表
        System.out.println(">>>>>>>>>>>>>>>>>>>数据库表");
        DatabaseMetaData meta = conn.getMetaData();
        rs = meta.getTables(null, null, null,
                new String[]{"TABLE"});
        int count = rs.getMetaData().getColumnCount();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= count; i++) {
            System.out.print(String.format("%1$20s", rsmd.getColumnName(i)));
        }
        System.out.println();
        int j = 20 * count;
        while (j > 0) {
            System.out.print("-");
            j--;
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= count; i++) {
                System.out.print(String.format("%20s", rs.getString(i)));
            }
            System.out.println();
        }
    }
}