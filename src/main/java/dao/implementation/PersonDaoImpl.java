package dao.implementation;

import dao.PersonDAO;
import entity.BookEntity;
import entity.PersonEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class PersonDaoImpl implements PersonDAO {

    private void setAllInEntity(PersonEntity person, ResultSet rs) throws SQLException {
        person.setId(rs.getInt("id"));
        person.setPassword(rs.getString("password"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setLogin(rs.getString("login"));
    }

    @Override
    public List<PersonEntity> getAll(){

        List<PersonEntity> list = new ArrayList<>();
        String sql = "select * from person";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                PersonEntity person = new PersonEntity();
                setAllInEntity(person, rs);
                list.add(person);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public PersonEntity get(int id) {
        PersonEntity person = new PersonEntity();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM person WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(person, rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return person;
    }


    @Override
    public void insert(PersonEntity person) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO person (first_name, last_name, login, password) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getLogin());
            ps.setString(4, person.getPassword());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PersonEntity person) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE person SET first_name=?, last_name=?, login=?, password=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getLogin());
            ps.setString(4, person.getPassword());
            ps.setInt(5, person.getId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(PersonEntity person) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM person WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, person.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PersonEntity findByLoginAndPassword(String login, String password) {
        PersonEntity person = new PersonEntity();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM person WHERE login = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(person, rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
