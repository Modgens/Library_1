package dao.implementation;

import dao.PublisherDAO;
import entity.Author;
import entity.Publisher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class PublisherDaoImpl implements PublisherDAO {
    public long idFromName(String name) {
        long id=0L;
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM publisher WHERE  publisher_name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    @Override
    public Publisher get(long id) {
        Publisher publisher = new Publisher();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM publisher WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                publisher.setId(rs.getInt("id"));
                publisher.setPublisherName(rs.getString("publisher_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return publisher;
    }

    @Override
    public List<Publisher> getAll() {
        List<Publisher> list = new ArrayList<>();
        String sql = "select * from publisher";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Publisher publisher = new Publisher();
                publisher.setId(rs.getLong("id"));
                publisher.setPublisherName(rs.getString("publisher_name"));
                list.add(publisher);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Publisher publisher) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO publisher (id, publisher_name) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, publisher.getId());
            ps.setString(2, publisher.getPublisherName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Publisher publisher) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE publisher SET publisher_name = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, publisher.getPublisherName());
            ps.setLong(2, publisher.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Publisher publisher) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM publisher WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, publisher.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
