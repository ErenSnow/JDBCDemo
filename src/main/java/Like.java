import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 模糊查询
 */
public class Like {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 获取连接
            connection = JDBCUtil.getConnection();
            // 获取预编译的数据库操作对象
            String sql = "select ename from emp where ename like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "_A%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtil.close(connection, statement, resultSet);
        }
    }
}
