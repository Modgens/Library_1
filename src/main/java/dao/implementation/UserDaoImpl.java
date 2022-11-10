package dao.implementation;

import dao.PersonalInfoDAO;
import dao.UserDAO;
import entity.User;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(UserDaoImpl.class));
    @Override
    public boolean hasInfoId(long id) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM user WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to find user with personal info id - " + id);
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User get(long id) {
        User user = new User();
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
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
        } catch (SQLException e) {
            logger.warning("failed to get user with user id - " + id);
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "select * from user";
        PreparedStatement ps;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
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
        } catch (SQLException e) {
            logger.warning("failed to get all users");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(User user) {
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO user (person_id, email, status) VALUES(?,?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getPersonId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getStatus());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to insert user with personal info id - " + user.getPersonId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE user SET person_id = ?, email = ?, status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getPersonId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getStatus());
            ps.setLong(4, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update user with id - " + user.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete user with id - " + user.getId());
            throw new RuntimeException(e);
        }
    }

    public String getStatusFromPersonInfoID(long infoId) {
        String status = "";
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT status FROM user WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, infoId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                status = rs.getString("status");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get user status with personal info id - " + infoId);
            throw new RuntimeException(e);
        }
        return status;
    }

    public long getIdFromPersonInfoId(long infoId) {
        long id = 0L;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT id FROM user WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, infoId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getLong("id");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get user id with personal info id - " + infoId);
            throw new RuntimeException(e);
        }
        return id;
    }
}
