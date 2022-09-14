package dao.implementation;

import dao.GenreDAO;
import entity.Author;
import entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class GenreDaoImpl implements GenreDAO {
    @Override
    public Genre get(int id) {
        Genre genre = new Genre();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM genre WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                genre.setId(rs.getInt("id"));
                genre.setGenreName(rs.getString("genre_name"));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> list = new ArrayList<>();
        String sql = "select * from genre";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setGenreName(rs.getString("genre_name"));
                list.add(genre);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Genre genre) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO genre (genre_name) VALUES(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, genre.getGenreName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Genre genre) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE genre SET genre_name = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, genre.getGenreName());
            ps.setInt(2, genre.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Genre genre) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM genre WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, genre.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
