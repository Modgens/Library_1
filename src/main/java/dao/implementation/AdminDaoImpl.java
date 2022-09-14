package dao.implementation;

import dao.AdminDAO;
import dao.PersonalInfoDAO;
import entity.Admin;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class AdminDaoImpl implements AdminDAO {
    @Override
    public Admin get(int id) {
        Admin admin = new Admin();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM admin WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            PersonalInfoDAO personalInfoDAO = new PersonalInfoDaoImpl();
            if(rs.next()){
                admin.setId(rs.getInt("id"));
                admin.setPerson(personalInfoDAO.get(rs.getInt("person_id")));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> list = new ArrayList<>();
        String sql = "select * from admin";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setPerson(new PersonalInfoDaoImpl().get(rs.getInt("person_id")));//get personal info too
                list.add(admin);
            }
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Admin admin) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO admin (id, person_id) VALUES(?, ?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ps.setInt(2, admin.getPerson().getId());
            new PersonalInfoDaoImpl().insert(admin.getPerson());//insert new personal info in db
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Admin admin) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE admin SET person_id = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, admin.getPerson().getId());
            ps.setInt(2, admin.getId());
            new PersonalInfoDaoImpl().update(admin.getPerson());//update personal info in db too
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Admin admin) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM admin WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ps.executeUpdate();
            new PersonalInfoDaoImpl().delete(admin.getPerson());//delete personal info too
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
