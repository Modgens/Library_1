package dao.implementation;


import dao.BookDAO;
import entity.Book;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookDaoImpl implements BookDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(BookDaoImpl.class));

    private void setAllInEntity(Book book, ResultSet rs) throws SQLException {
        book.setId(rs.getLong("id"));
        book.setCount(rs.getInt("count"));
        book.setName(rs.getString("title"));
        book.setNameUa(rs.getString("title_ua"));
        book.setImgName(rs.getString("img_name"));
        book.setDescription(rs.getString("description"));
        book.setDescriptionUa(rs.getString("description_ua"));
        book.setPublicationId(rs.getLong("publication_id"));
        book.setDateOfPublication(rs.getInt("year_of_publication"));
        book.setAuthorId(rs.getLong("author_id"));
        book.setGenreId(rs.getLong("genre_id"));
    }
    private void setAllInPS(PreparedStatement ps, Book book) throws SQLException {
        if (book == null) {
            logger.warning("book is null");
            return;
        }
        ps.setString(1, book.getName());
        ps.setString(2, book.getNameUa());
        ps.setLong(3, book.getAuthorId());
        ps.setString(4, book.getDescription());
        ps.setString(5, book.getDescriptionUa());
        ps.setInt(6, book.getDateOfPublication());
        ps.setLong(7, book.getCount());
        ps.setLong(8, book.getPublicationId());
        ps.setString(9, book.getImgName());
        ps.setLong(10, book.getGenreId());
    }

    @Override
    public List<Book> getAll(){
        List<Book> list = new ArrayList<>();
        String sql = "select * from book";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Book book = new Book();
                setAllInEntity(book, rs);//to escape duplication
                list.add(book);
            }
        } catch (SQLException e) {
            logger.warning("failed to get all book");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Book> findByName(String name) {
        if (name == null) {
            logger.warning("name is null");
            return new ArrayList<>();
        }
        List<Book> list = new ArrayList<>();
        Book book = new Book();
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM book WHERE title = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setAllInEntity(book, rs);//to escape duplication
                list.add(book);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get book with name - " + name);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Book get(long id) {
        Book book = new Book();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM book WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(book, rs);//to escape duplication
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get book with id - " + id);
            throw new RuntimeException(e);
        }
        return book;
    }


    @Override
    public void insert(Book book) {
        if (book == null) {
            logger.warning("book is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO book (title, title_ua, author_id, description, description_ua, year_of_publication, count, publication_id, img_name, genre_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            setAllInPS(ps, book);//to escape duplication
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warning("failed to insert book with name - " + book.getName());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Book book) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            if (book == null) {
                logger.warning("book is null");
                return;
            }
            String sql = "UPDATE book SET title=?,title_ua=?, author_id=?, description=?, description_ua=?, year_of_publication=?, count=?, publication_id=?, img_name=?, genre_id=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            setAllInPS(ps, book);//to escape duplication
            ps.setLong(11, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warning("failed to update book with id - " + book.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Book book) {
        if (book == null) {
            logger.warning("book is null");
            return;
        }
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM book WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, book.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete book with id - " + book.getId());
            throw new RuntimeException(e);
        }
    }
}
