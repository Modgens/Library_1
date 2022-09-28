package dao.implementation;

import dao.PersonalInfoDAO;
import dao.UserDAO;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class UserDaoImpl implements UserDAO {

    @Override
    public boolean hasInfoId(long id) {
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM user WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User get(long id) {
        User user = new User();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            PersonalInfoDAO personalInfoDAO = new PersonalInfoDaoImpl();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPersonId(rs.getLong("person_id"));
                user.setStatus(rs.getString("status"));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "select * from user";
        PreparedStatement ps;
        try (Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPersonId(rs.getLong("person_id"));
                user.setStatus(rs.getString("status"));
                list.add(user);
            }
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(User user) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO user (person_id, email, status) VALUES(?,?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getPersonId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getStatus());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE user SET person_id = ?, email = ?, status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getPersonId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getStatus());
            ps.setLong(4, user.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getStatusFromPersonInfoID(long infoId) {
        String status = "";
        try(Connection con = getConnection()) {
            String sql = "SELECT status FROM user WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, infoId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                status = rs.getString("status");
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

}
