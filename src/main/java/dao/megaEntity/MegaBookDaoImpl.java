package dao.megaEntity;

import dao.implementation.UserOrdersDaoImpl;
import entity.Author;
import entity.Genre;
import entity.Publisher;
import entity.megaEntity.MegaBook;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MegaBookDaoImpl {

    static final Logger logger = Logger.getLogger(String.valueOf(MegaBookDaoImpl.class));
    private static MegaBookDaoImpl instance;

    public static MegaBookDaoImpl getInstance() {
        logger.info("Mega Book Class Instance");
        if (instance == null)
            instance = new MegaBookDaoImpl();
        return instance;
    }

    public MegaBook getById(long id) {
        MegaBook book = null;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "select * " +
                    "from book " +
                    "inner join genre on book.genre_id=genre.id " +
                    "inner join author on book.author_id=author.id " +
                    "inner join publisher on book.publication_id=publisher.id " +
                    "where book.id=?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = new MegaBook(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("title_ua"),
                        new Publisher(rs.getLong("publication_id"), rs.getString("publisher_name"), rs.getString("publisher_name_ua")),
                        rs.getInt("year_of_publication"),
                        rs.getInt("count"),
                        rs.getString("description"),
                        rs.getString("description_ua"),
                        rs.getString("img_name"),
                        new Author(rs.getLong("author_id"), rs.getString("author_name"), rs.getString("author_name_ua")),
                        new Genre(rs.getLong("genre_id"), rs.getString("genre_name"), rs.getString("genre_name_ua"))
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get mega book with id - " + id);
            throw new RuntimeException(e);
        }
        return book;
    }

    public List<MegaBook> getAll() {
        List<MegaBook> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "select * " +
                    "from book " +
                    "inner join genre on book.genre_id=genre.id " +
                    "inner join author on book.author_id=author.id " +
                    "inner join publisher on book.publication_id=publisher.id;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new MegaBook(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("title_ua"),
                        new Publisher(rs.getLong("publication_id"), rs.getString("publisher_name"), rs.getString("publisher_name_ua")),
                        rs.getInt("year_of_publication"),
                        rs.getInt("count"),
                        rs.getString("description"),
                        rs.getString("description_ua"),
                        rs.getString("img_name"),
                        new Author(rs.getLong("author_id"), rs.getString("author_name"), rs.getString("author_name_ua")),
                        new Genre(rs.getLong("genre_id"), rs.getString("genre_name"), rs.getString("genre_name_ua"))
                ));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all mega books");
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getAllSize() {
        int count = 0;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "select count(id) from book";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count(id)");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get count of all mega books");
            throw new RuntimeException(e);
        }
        return count;
    }
}
