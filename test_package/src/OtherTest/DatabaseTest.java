package test_package.src.OtherTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args){
        String JDBC_URL = "jdbc:mysql://localhost:3306/test";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "zqz123456";

        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)){
            
            //JDBC查询
            //1.通过Connection提供的createStatement()方法创建一个Statement对象，用于执行一个查询
            //2.执行Statement对象提供的executeQuery("SELECT * FROM student")并传入SQL语句，执行查询并返回结果集，用ResultSet来引用
            //反复调用ResultSet的next()方法读取每一行结果
            // try(Statement stmt = conn.createStatement()){
            //     try(ResultSet rs = stmt.executeQuery("SELECT * FROM student")){//statement和ResultSet都是需要关闭的资源，使用try(resource)
            //         while(rs.next()){
            //             long id = rs.getLong(1);//索引从1开始！！！
            //             long class_id = rs.getLong(2);
            //             String name = rs.getString(3);
            //             String gender = rs.getString(4);
            //             int score = rs.getInt(5);
            //             System.out.println("id: " + id + ", class_id: " + class_id + ", name：" + name + ", gender: " + gender + ", score: " + score);
            //         }
            //     }
            // }


            //上面的方法会存在SQL注入问题，即可以通过特殊的输入来达到非正常访问的目的
            //因此使用prepareStatement来替代Statement
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO student (class_id,name,gender,score) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {//获取主键
                ps.setObject(1, 1);// 这里的索引也从1开始！！
                ps.setObject(2, "jjc");
                ps.setObject(3, "M");
                ps.setObject(4, 99);
                
                //这里有点错误，如果是新增一条记录，应该使用：
                int n =ps.executeUpdate();//n会返回操作的行数
                try(ResultSet rs = ps.getGeneratedKeys()){//获取主键
                    if(rs.next()){
                        long id = rs.getLong(1);//如果一次插入多条记录，那么rs对象就会有多行返回值。
                        //如果插入时有多列自增，那么ResultSet对象每一行都会对应多个自增值。
                    }
                }



                try (ResultSet rs = ps.executeQuery()) {// statement和ResultSet都是需要关闭的资源，使用try(resource)
                    while (rs.next()) {
                        long id = rs.getLong("id");// 使用String类型的列名比索引更容易读，且不易出错
                        long class_id = rs.getLong("class_id");
                        String name = rs.getString("name");
                        String gender = rs.getString("gender");
                        int score = rs.getInt("score");
                        System.out.println("id: " + id + ", class_id: " + class_id + ", name：" + name + ", gender: "
                                + gender + ", score: " + score);
                    }
                }
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
