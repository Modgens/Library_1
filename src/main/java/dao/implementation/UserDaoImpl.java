package dao.implementation;

import dao.PersonalInfoDAO;
import dao.UserDAO;
import entity.PersonalInfo;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class UserDaoImpl implements UserDAO {

    @Override
    public User get(int id) {
        User user = new User();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            PersonalInfoDAO personalInfoDAO = new PersonalInfoDaoImpl();
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPerson(personalInfoDAO.get(rs.getInt("person_id")));
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
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPerson(new PersonalInfoDaoImpl().get(rs.getInt("person_id")));//get personal info too
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
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO user (id, person_id, email) VALUES(?, ?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getPerson().getId());
            ps.setString(3, user.getEmail());

            new PersonalInfoDaoImpl().insert(user.getPerson());//insert new personal info in db

            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE user SET person_id = ?, email = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getPerson().getId());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());
            new PersonalInfoDaoImpl().update(user.getPerson());//update personal info in db too
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            new PersonalInfoDaoImpl().delete(user.getPerson());//delete personal info too
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        User user = new User();
        try(Connection con = getConnection()) {
            ResultSet rs;
            PreparedStatement ps;
            PersonalInfo personalInfo = new PersonalInfo();
            String sql1 = "SELECT * FROM user WHERE person_id = ?";
            String sql2 = "SELECT * FROM personal_info WHERE login = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if(rs.next()){
                personalInfo.setLogin(rs.getString("login"));
                personalInfo.setPassword(rs.getString("password"));
                personalInfo.setId(rs.getInt("id"));
                personalInfo.setFirstName(rs.getString("first_name"));
                personalInfo.setLastName(rs.getString("last_name"));
            }
            ps = con.prepareStatement(sql1);
            ps.setInt(1, personalInfo.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPerson(personalInfo);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
