package dao.implementation;

import dao.UserOrdersDAO;
import entity.UserOrders;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class UserOrdersDaoImpl implements UserOrdersDAO {
    @Override
    public UserOrders get(long id) {
        UserOrders userOrders = new UserOrders();
        try(Connection con = getConnection()) {
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
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userOrders;
    }

    @Override
    public List<UserOrders> getAll() {
        List<UserOrders> list = new ArrayList<>();
        String sql = "select * from user_orders";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UserOrders userOrders = new UserOrders();
                userOrders.setId(rs.getLong("id"));
                userOrders.setUserId(rs.getLong("user_id"));
                userOrders.setBookId(rs.getLong("book_id"));
                userOrders.setOrderDate(rs.getDate("order_date"));
                userOrders.setDateToReturn(rs.getDate("date_to_return"));
                list.add(userOrders);
            }
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(UserOrders userOrders) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO user_orders (id, user_id, book_id, order_date, date_to_return) VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, userOrders.getId());
            ps.setLong(2, userOrders.getUserId());
            ps.setLong(3, userOrders.getBookId());
            ps.setDate(4, userOrders.getOrderDate());
            ps.setDate(5, userOrders.getDateToReturn());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserOrders userOrders) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE user_orders SET user_id = ?, book_id = ?, order_date = ?, date_to_return = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, userOrders.getUserId());
            ps.setLong(2, userOrders.getBookId());
            ps.setDate(3, userOrders.getOrderDate());
            ps.setDate(4, userOrders.getDateToReturn());
            ps.setLong(5, userOrders.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UserOrders userOrders) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM user_orders WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, userOrders.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
