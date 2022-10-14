package dao.transaction;

import entity.PersonalInfo;
import entity.User;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.Connector.getConnection;

public class CreateUserTransaction {
    public void create(PersonalInfo personalInfo, String email, String status){
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);

            String sql = "INSERT INTO personal_info (first_name, last_name, login, password) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, personalInfo.getFirstName());
            ps.setString(2, personalInfo.getLastName());
            ps.setString(3, personalInfo.getLogin());
            ps.setString(4, personalInfo.getPassword());
            ps.executeUpdate();

            String sql2 = "SELECT id FROM personal_info WHERE login = ?";
            long id = 0L;
            ps = con.prepareStatement(sql2);
            ps.setString(1, personalInfo.getLogin());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getLong("id");
            }
            ps.close();

            String sql3 = "INSERT INTO user (person_id, email, status) VALUES(?, ?, ?);";
            ps = con.prepareStatement(sql3);
            ps.setLong(1, id);
            ps.setString(2, email);
            ps.setString(3, status);
            ps.executeUpdate();
            ps.close();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}