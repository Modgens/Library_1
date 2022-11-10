package dao.implementation;

import dao.AuthorDAO;
import entity.Author;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AuthorDaoImpl implements AuthorDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(AuthorDaoImpl.class));

    public long idFromName(String name, String nameUa) {
        if (name == null || nameUa == null) {
            logger.warning("there is null in name");
        }
        long id = 0L;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM author WHERE  author_name = ? and author_name_ua = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, nameUa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get id from name - " + name);
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Author get(long id) {
        Author author = new Author();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM author WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                author.setId(rs.getInt("id"));
                author.setAuthorName(rs.getString("author_name"));
                author.setAuthorNameUa(rs.getString("author_name_ua"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get Author with id - " + id);
            throw new RuntimeException(e);
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> list = new ArrayList<>();
        String sql = "select * from author";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setAuthorName(rs.getString("author_name"));
                author.setAuthorNameUa(rs.getString("author_name_ua"));

                list.add(author);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all Authors in db");

            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Author author) {
        if (author == null) {
            logger.warning("author is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO author (id, author_name, author_name_ua) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, author.getId());
            ps.setString(2, author.getAuthorName());
            ps.setString(2, author.getAuthorNameUa());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to insert Author with name - " + author.getAuthorName());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author author) {
        if (author == null) {
            logger.warning("author is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE author SET author_name = ?, author_name_ua = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, author.getAuthorName());
            ps.setString(2, author.getAuthorNameUa());
            ps.setLong(3, author.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update Author with id - " + author.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Author author) {
        if (author == null) {
            logger.warning("author is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM author WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, author.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete Author with id - " + author.getId());
            throw new RuntimeException(e);
        }
    }
}
