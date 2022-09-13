package dao.implementation;

import dao.UserDAO;
import entity.UserEntity;

import java.sql.*;
import java.util.List;

import static util.Connector.getConnection;

public class UserDaoImpl implements UserDAO {

    @Override
    public void insert(UserEntity userEntity) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO user (id, person_id, email) VALUES(?, ?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userEntity.getId());
            ps.setInt(2, userEntity.getPerson_id());
            ps.setString(3, userEntity.getEmail());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity get(int id) {
        UserEntity user = new UserEntity();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<UserEntity> getAll() {
        return null;
    }

    @Override
    public void update(UserEntity userEntity) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE user SET person_id = ?, email = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userEntity.getPerson_id());
            ps.setString(2, userEntity.getEmail());
            ps.setInt(3, userEntity.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userEntity.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity findByName(String name) {
        return null;
    }
}
