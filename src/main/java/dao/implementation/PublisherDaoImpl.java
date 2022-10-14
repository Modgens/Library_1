package dao.implementation;

import dao.PublisherDAO;
import entity.Author;
import entity.Publisher;
import util.ConnectionPool;

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
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM publisher WHERE  publisher_name = ? or publisher_name_ua = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public long idFromName(String name, String nameUa) {
        long id=0L;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM publisher WHERE  publisher_name = ? and publisher_name_ua = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, nameUa);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    @Override
    public Publisher get(long id) {
        Publisher publisher = new Publisher();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM publisher WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                publisher.setId(rs.getInt("id"));
                publisher.setPublisherName(rs.getString("publisher_name"));
                publisher.setPublisherNameUa(rs.getString("publisher_name_ua"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return publisher;
    }

    @Override
    public List<Publisher> getAll() {
        List<Publisher> list = new ArrayList<>();
        String sql = "select * from publisher";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Publisher publisher = new Publisher();
                publisher.setId(rs.getLong("id"));
                publisher.setPublisherName(rs.getString("publisher_name"));
                publisher.setPublisherNameUa(rs.getString("publisher_name_ua"));
                list.add(publisher);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Publisher publisher) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO publisher (id, publisher_name, publisher_name_ua) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, publisher.getId());
            ps.setString(2, publisher.getPublisherName());
            ps.setString(3, publisher.getPublisherNameUa());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Publisher publisher) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE publisher SET publisher_name = ?, publisher_name_ua = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, publisher.getPublisherName());
            ps.setString(2, publisher.getPublisherNameUa());
            ps.setLong(3, publisher.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Publisher publisher) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM publisher WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, publisher.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
