import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ResourceBundle;

public class main {

    public static void main(String[] args) {

        // 使用资源绑定器绑定属性配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            try {
                // 1、注册驱动
                // 第一种写法
//                DriverManager.registerDriver(new Driver());
                // 第二种写法，常用。
                // 因为参数是字符串，可以写到配置文件中
                // 以下方法不需要接受返回值，因为我们只想用它的类加载动作
                Class.forName(driver);

                // 2、获取数据库连接
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("数据库连接对象" + connection);

                // 3、获取数据库操作对象（statement专门执行sql语句的）
                statement = connection.createStatement();

                // 4、执行sql语句，JDBC中的sql语句不需要写分号
                String sql = "select empno,ename,sal from emp";
                resultSet = statement.executeQuery(sql);
                // 5、处理查询结果集
                while (resultSet.next()) {
                    int empno = resultSet.getInt("empno");
                    String ename = resultSet.getString("ename");
                    double sal = resultSet.getDouble("sal");
                    System.out.println(empno + " " + ename + " " + (sal + 100) + " ");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 6、释放资源，遵循从小到大依次释放，分别try catch
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (statement != null) {
                    try {
                        statement.cancel();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
