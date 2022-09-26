package dao.implementation;


import dao.BookDAO;
import entity.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static util.Connector.getConnection;


public class
BookDaoImpl implements BookDAO {

    private void setAllInEntity(Book book, ResultSet rs) throws SQLException {
        book.setId(rs.getLong("id"));
        book.setCount(rs.getInt("count"));
        book.setName(rs.getString("title"));
        book.setImgName(rs.getString("img_name"));
        book.setDescription(rs.getString("description"));
        book.setPublication(rs.getString("publication"));
        book.setDateOfPublication(rs.getString("year_of_publication"));
        book.setAuthorId(rs.getLong("author_id"));
        book.setGenreId(rs.getLong("genre_id"));
    }
    private void setAllInPS(PreparedStatement ps, Book book) throws SQLException {
        ps.setString(1, book.getName());
        ps.setLong(2, book.getAuthorId());
        ps.setString(3, book.getDescription());
        ps.setString(4, book.getDateOfPublication());
        ps.setLong(5, book.getCount());
        ps.setString(6, book.getPublication());
        ps.setString(7, book.getImgName());
        ps.setLong(8, book.getGenreId());
    }

    @Override
    public List<Book> getAll(){

        List<Book> list = new ArrayList<>();
        String sql = "select * from book";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Book book = new Book();
                setAllInEntity(book, rs);//to escape duplication
                list.add(book);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Book> findByName(String name) {
        List<Book> list = new ArrayList<>();
        Book book = new Book();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM book WHERE title = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(book, rs);//to escape duplication
                list.add(book);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Book get(long id) {
        Book book = new Book();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM book WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(book, rs);//to escape duplication
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return book;
    }


    @Override
    public void insert(Book book) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO book (title, author_id, description, year_of_publication, count, publication, img_name, genre_id) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps= con.prepareStatement(sql);
            setAllInPS(ps,book);//to escape duplication
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Book book) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE book SET title=?, author_id=?, description=?, year_of_publication=?, count=?, publication=?, img_name=?, genre_id=? WHERE id=?";
            PreparedStatement ps= con.prepareStatement(sql);
            setAllInPS(ps,book);//to escape duplication
            ps.setLong(9, book.getId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Book book) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, book.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
