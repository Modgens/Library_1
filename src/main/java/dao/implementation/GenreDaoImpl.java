package dao.implementation;

import dao.GenreDAO;
import entity.Genre;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GenreDaoImpl implements GenreDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(GenreDaoImpl.class));
    @Override
    public Genre get(long id) {
        Genre genre = new Genre();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM genre WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                genre.setId(rs.getLong("id"));
                genre.setGenreName(rs.getString("genre_name"));
                genre.setGenreNameUa(rs.getString("genre_name_ua"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get genre by id - " + id);
            throw new RuntimeException(e);
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> list = new ArrayList<>();
        String sql = "select * from genre";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Genre genre = new Genre();
                genre.setId(rs.getLong("id"));
                genre.setGenreName(rs.getString("genre_name"));
                genre.setGenreNameUa(rs.getString("genre_name_ua"));
                list.add(genre);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get list of genres");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Genre genre) {
        if (genre == null) {
            logger.warning("genre is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO genre (genre_name, genre_name_ua) VALUES(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, genre.getGenreName());
            ps.setString(2, genre.getGenreNameUa());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to insert genre with name - " + genre.getGenreName());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Genre genre) {
        if (genre == null) {
            logger.warning("genre is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE genre SET genre_name = ?, genre_name_ua = ?  WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, genre.getGenreName());
            ps.setString(2, genre.getGenreNameUa());
            ps.setLong(3, genre.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update genre with id - " + genre.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Genre genre) {
        if (genre == null) {
            logger.warning("genre is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM genre WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, genre.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete genre with id - " + genre.getId());
            throw new RuntimeException(e);
        }
    }
}
