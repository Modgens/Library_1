package dao.implementation;


import dao.BookDAO;
import entity.BookEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static util.Connector.getConnection;


public class BookDaoImpl implements BookDAO {

    private void setAllInEntity(BookEntity book, ResultSet rs) throws SQLException {
        book.setId(rs.getInt("id"));
        book.setCount(rs.getInt("count"));
        book.setName(rs.getString("title"));
        book.setGenre_id(rs.getInt("genre_id"));
        book.setImgName(rs.getString("img_name"));
        book.setAuthor_id(rs.getInt("author_id"));
        book.setDescription(rs.getString("description"));
        book.setPublication(rs.getString("publication"));
        book.setDateOfPublication(rs.getString("year_of_publication"));
    }

    @Override
    public List<BookEntity> getAll(){

        List<BookEntity> list = new ArrayList<>();
        String sql = "select * from book";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                BookEntity book = new BookEntity();
                setAllInEntity(book, rs);
                list.add(book);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<BookEntity> findByName(String name) {
        List<BookEntity> list = new ArrayList<>();
        BookEntity book = new BookEntity();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM book WHERE title = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(book, rs);
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
    public BookEntity get(int id) {
        BookEntity book = new BookEntity();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM book WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(book, rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return book;
    }


    @Override
    public void insert(BookEntity bookEntity) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO book (title, author_id, description, year_of_publication, count, publication, img_name, genre_id) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1, bookEntity.getName());
            ps.setInt(2, bookEntity.getAuthor_id());
            ps.setString(3, bookEntity.getDescription());
            ps.setString(4, bookEntity.getDateOfPublication());
            ps.setInt(5, bookEntity.getCount());
            ps.setString(6,bookEntity.getPublication());
            ps.setString(7, bookEntity.getImgName());
            ps.setInt(8, bookEntity.getGenre_id());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(BookEntity bookEntity) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE book SET title=?, author_id=?, description=?, year_of_publication=?, count=?, publication=?, img_name=?, genre_id=? WHERE id=?";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1, bookEntity.getName());
            ps.setInt(2, bookEntity.getAuthor_id());
            ps.setString(3, bookEntity.getDescription());
            ps.setString(4, bookEntity.getDateOfPublication());
            ps.setInt(5, bookEntity.getCount());
            ps.setString(6,bookEntity.getPublication());
            ps.setString(7, bookEntity.getImgName());
            ps.setInt(8, bookEntity.getGenre_id());
            ps.setInt(9, bookEntity.getId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(BookEntity bookEntity) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookEntity.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
