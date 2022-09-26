package dao.implementation;

import dao.AdminDAO;
import dao.PersonalInfoDAO;
import entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class AdminDaoImpl implements AdminDAO {
    @Override
    public Admin get(long id) {
        Admin admin = new Admin();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM admin WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            PersonalInfoDAO personalInfoDAO = new PersonalInfoDaoImpl();
            if(rs.next()){
                admin.setId(rs.getInt("id"));
                admin.setPersonId(rs.getInt("person_id"));
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
                admin.setId(rs.getLong("id"));
                admin.setPersonId(rs.getLong("person_id"));
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
            ps.setLong(1, admin.getId());
            ps.setLong(2, admin.getPersonId());
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
            ps.setLong(1, admin.getId());
            ps.setLong(2, admin.getPersonId());
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
            ps.setLong(1, admin.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasInfoId(long id) {
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM admin WHERE person_id = ?";
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
}
