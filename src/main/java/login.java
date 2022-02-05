import java.sql.*;
import java.util.Scanner;

public class login {

    public static void main(String[] args) {
        // 初始化一个界面
        User user = initUI();
        // 验证用户名和密码
        boolean isLogin = initLogin(user);
        // 最后输出结果
        if (isLogin) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
    }

    private static boolean initLogin(User user) {
        // JDBC代码
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean isLogin = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/erensnow", "root", "erensnow");
            String sql = "select * from t_user where loginName = ? and loginPwd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getLoginName());
            statement.setString(2, user.getLoginPwd());
            resultSet = statement.executeQuery();
            isLogin = resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return isLogin;
    }

    private static User initUI() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("用户名：");
        String username = scanner.nextLine();
        System.out.println("密码：");
        String password = scanner.nextLine();
        User user = new User();
        user.setLoginName(username);
        user.setLoginPwd(password);
        return user;
    }
}
