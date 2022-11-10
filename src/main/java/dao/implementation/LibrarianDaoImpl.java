package dao.implementation;

import dao.LibrarianDAO;
import dao.PersonalInfoDAO;
import entity.Librarian;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LibrarianDaoImpl implements LibrarianDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(GenreDaoImpl.class));
    @Override
    public boolean hasInfoId(long id) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM librarian WHERE person_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                logger.info("find one librarian with id - " + id);
                return true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("no one librarian with id - " + id);
        return false;
    }
    @Override
    public Librarian get(long id) {
        Librarian librarian = new Librarian();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM librarian WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            PersonalInfoDAO personalInfoDAO = new PersonalInfoDaoImpl();
            if(rs.next()){
                librarian.setId(rs.getLong("id"));
                librarian.setPersonId(rs.getLong("person_id"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to find librarian with id - " + id);
            throw new RuntimeException(e);
        }
        return librarian;
    }

    @Override
    public List<Librarian> getAll() {
        List<Librarian> list = new ArrayList<>();
        String sql = "select * from librarian";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Librarian librarian = new Librarian();
                librarian.setId(rs.getLong("id"));
                librarian.setPersonId(rs.getLong("person_id"));
                list.add(librarian);
            }
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all librarians from db");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Librarian librarian) {
        if (librarian == null) {
            logger.info("librarian is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO librarian (person_id) VALUES(?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian.getPersonId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to insert librarian with personal id - " + librarian.getPersonId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Librarian librarian) {
        if (librarian == null) {
            logger.info("librarian is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE librarian SET person_id = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian.getPersonId());
            ps.setLong(2, librarian.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update librarian with id - " + librarian.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Librarian librarian) {
        if (librarian == null) {
            logger.info("librarian is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM librarian WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete librarian with id - " + librarian.getId());
            throw new RuntimeException(e);
        }
    }
}
