package dao.implementation;

import dao.LibrarianDAO;
import dao.PersonalInfoDAO;
import entity.Admin;
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
    public Librarian get(int id) {
        Librarian librarian = new Librarian();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM librarian WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            PersonalInfoDAO personalInfoDAO = new PersonalInfoDaoImpl();
            if(rs.next()){
                librarian.setId(rs.getInt("id"));
                librarian.setPerson(personalInfoDAO.get(rs.getInt("person_id")));
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
                librarian.setId(rs.getInt("id"));
                librarian.setPerson(new PersonalInfoDaoImpl().get(rs.getInt("person_id")));//get personal info too
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
            ps.setInt(1, librarian.getPerson().getId());
            new PersonalInfoDaoImpl().insert(librarian.getPerson());//insert new personal info in db
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
            ps.setInt(1, librarian.getPerson().getId());
            ps.setInt(2, librarian.getId());
            new PersonalInfoDaoImpl().update(librarian.getPerson());//update personal info in db too
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
            ps.setInt(1, librarian.getId());
            ps.executeUpdate();
            new PersonalInfoDaoImpl().delete(librarian.getPerson());//delete personal info too
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
