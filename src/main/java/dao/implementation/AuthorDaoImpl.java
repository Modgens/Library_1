package dao.implementation;

import dao.AuthorDAO;
import entity.Author;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class AuthorDaoImpl implements AuthorDAO {

    @Override
    public Author get(long id) {
        Author author = new Author();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM author WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                author.setId(rs.getInt("id"));
                author.setAuthorName(rs.getString("author_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> list = new ArrayList<>();
        String sql = "select * from author";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setAuthorName(rs.getString("author_name"));
                list.add(author);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Author author) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO author (id, author_name) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, author.getId());
            ps.setString(2, author.getAuthorName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author author) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE author SET author_name = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, author.getAuthorName());
            ps.setLong(2, author.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Author author) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM author WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, author.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
