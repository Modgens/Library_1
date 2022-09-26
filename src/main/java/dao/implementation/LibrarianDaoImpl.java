package dao.implementation;

import dao.LibrarianDAO;
import dao.PersonalInfoDAO;
import entity.Librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class LibrarianDaoImpl implements LibrarianDAO {

    @Override
    public boolean hasInfoId(long id) {
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM librarian WHERE person_id = ?";
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
    public Librarian get(long id) {
        Librarian librarian = new Librarian();
        try(Connection con = getConnection()) {
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
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return librarian;
    }

    @Override
    public List<Librarian> getAll() {
        List<Librarian> list = new ArrayList<>();
        String sql = "select * from librarian";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Librarian librarian = new Librarian();
                librarian.setId(rs.getLong("id"));
                librarian.setPersonId(rs.getLong("person_id"));
                list.add(librarian);
            }
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Librarian librarian) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO librarian (person_id) VALUES(?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian.getPersonId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Librarian librarian) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE librarian SET person_id = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian.getPersonId());
            ps.setLong(2, librarian.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Librarian librarian) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM librarian WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
