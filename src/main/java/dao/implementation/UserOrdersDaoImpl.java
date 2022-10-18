package dao.implementation;

import dao.UserOrdersDAO;
import entity.UserOrders;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserOrdersDaoImpl implements UserOrdersDAO {

    @Override
    public UserOrders get(long id) {
        UserOrders userOrders = new UserOrders();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM user_orders WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                userOrders.setId(rs.getLong("id"));
                userOrders.setUserId(rs.getLong("user_id"));
                userOrders.setBookId(rs.getLong("book_id"));
                userOrders.setOrderDate(rs.getDate("order_date"));
                userOrders.setDateToReturn(rs.getDate("date_to_return"));
                userOrders.setStatus(rs.getString("status"));
                userOrders.setStatusUa(rs.getString("status_ua"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userOrders;
    }

    @Override
    public List<UserOrders> getAll() {
        List<UserOrders> list = new ArrayList<>();
        String sql = "select * from user_orders";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UserOrders userOrders = new UserOrders();
                userOrders.setId(rs.getLong("id"));
                userOrders.setUserId(rs.getLong("user_id"));
                userOrders.setBookId(rs.getLong("book_id"));
                userOrders.setStatus(rs.getString("status"));
                userOrders.setOrderDate(rs.getDate("order_date"));
                userOrders.setDateToReturn(rs.getDate("date_to_return"));
                userOrders.setStatusUa(rs.getString("status_ua"));
                list.add(userOrders);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(UserOrders userOrders) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO user_orders (id, user_id, book_id, order_date, date_to_return, status, status_ua) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, userOrders.getId());
            ps.setLong(2, userOrders.getUserId());
            ps.setLong(3, userOrders.getBookId());
            ps.setDate(4, userOrders.getOrderDate());
            ps.setDate(5, userOrders.getDateToReturn());
            ps.setString(6, userOrders.getStatus());
            ps.setString(7, userOrders.getStatusUa());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserOrders userOrders) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE user_orders SET user_id = ?, book_id = ?, order_date = ?, date_to_return = ?, status = ?, status_ua = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, userOrders.getUserId());
            ps.setLong(2, userOrders.getBookId());
            ps.setDate(3, userOrders.getOrderDate());
            ps.setDate(4, userOrders.getDateToReturn());
            ps.setString(5, userOrders.getStatus());
            ps.setString(6, userOrders.getStatusUa());
            ps.setLong(7, userOrders.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UserOrders userOrders) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM user_orders WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, userOrders.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int bookCountInUsers(long book_id){
        int count = 0;
        String sql = "select * from user_orders where book_id = " + book_id;
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                count++;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public boolean userAlreadyHasThisBook(long book_id, long user_id){
        String sql = "select * from user_orders where book_id = ? AND user_id = ?";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ps.setLong(1, book_id);
            ps.setLong(2, user_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public int countOrderedOneUser(long user_id){
        int count = 0;
        String sql = "select * from user_orders where user_id = "+user_id;
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                count++;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public List<UserOrders> getAllByUserId(long user_id){
        List<UserOrders> list = new ArrayList<>();
        String sql = "select * from user_orders where user_id = "+user_id;
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UserOrders userOrders = new UserOrders();
                userOrders.setId(rs.getLong("id"));
                userOrders.setUserId(rs.getLong("user_id"));
                userOrders.setBookId(rs.getLong("book_id"));
                userOrders.setStatus(rs.getString("status"));
                userOrders.setStatusUa(rs.getString("status_ua"));
                userOrders.setOrderDate(rs.getDate("order_date"));
                userOrders.setDateToReturn(rs.getDate("date_to_return"));
                list.add(userOrders);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<UserOrders> getAllForReadingRoom(){
        List<UserOrders> list = new ArrayList<>();
        String sql = "select * from user_orders where status = ? or status = ?";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ps.setString(1, "Wait for reading room");
            ps.setString(2, "In reading room");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UserOrders userOrders = new UserOrders();
                userOrders.setId(rs.getLong("id"));
                userOrders.setUserId(rs.getLong("user_id"));
                userOrders.setBookId(rs.getLong("book_id"));
                userOrders.setStatus(rs.getString("status"));
                userOrders.setStatusUa(rs.getString("status_ua"));
                userOrders.setOrderDate(rs.getDate("order_date"));
                userOrders.setDateToReturn(rs.getDate("date_to_return"));
                list.add(userOrders);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<UserOrders> getAllForSub(){
        List<UserOrders> list = new ArrayList<>();
        String sql = "select * from user_orders where status = ? or status = ?";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ps.setString(1, "Wait for order");
            ps.setString(2, "In order");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UserOrders userOrders = new UserOrders();
                userOrders.setId(rs.getLong("id"));
                userOrders.setUserId(rs.getLong("user_id"));
                userOrders.setBookId(rs.getLong("book_id"));
                userOrders.setStatus(rs.getString("status"));
                userOrders.setStatusUa(rs.getString("status_ua"));
                userOrders.setOrderDate(rs.getDate("order_date"));
                userOrders.setDateToReturn(rs.getDate("date_to_return"));
                list.add(userOrders);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
