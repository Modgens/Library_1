package dao.implementation;

import dao.AdminDAO;
import entity.Admin;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class AdminDaoImpl implements AdminDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(AdminDaoImpl.class));
    @Override
    public Admin get(long id) {
        Admin admin = new Admin();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            logger.info("try to find admin with id - " + id);
            String sql = "SELECT * FROM admin WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                admin.setId(rs.getInt("id"));
                admin.setPersonId(rs.getInt("person_id"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to find admin with id - " + id);
            throw new RuntimeException(e);
        }
        return admin;
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> list = new ArrayList<>();
        String sql = "select * from admin";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            logger.info("try to get all admin in db");
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Admin admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setPersonId(rs.getLong("person_id"));
                list.add(admin);
            }
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all admin");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Admin admin) {
        if (admin == null) {
            logger.warning("admin is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO admin (id, person_id) VALUES(?, ?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, admin.getId());
            ps.setLong(2, admin.getPersonId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to insert admin with personalInfo id - " + admin.getPersonId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Admin admin) {
        if (admin == null) {
            logger.warning("admin is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE admin SET person_id = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, admin.getId());
            ps.setLong(2, admin.getPersonId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update admin with id - " + admin.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Admin admin) {
        if (admin == null) {
            logger.warning("admin is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM admin WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, admin.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete admin with id - " + admin.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasInfoId(long id) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM admin WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to find admin by personalInfo id - " + id);
            throw new RuntimeException(e);
        }
        return false;
    }
}
