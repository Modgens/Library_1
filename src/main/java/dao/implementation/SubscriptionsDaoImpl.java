package dao.implementation;

import dao.SubscriptionsDAO;
import entity.Subscriptions;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SubscriptionsDaoImpl implements SubscriptionsDAO {
    static final Logger logger = Logger.getLogger(String.valueOf(SubscriptionsDaoImpl.class));
    @Override
    public Subscriptions get(long id) {
        Subscriptions subscriptions = new Subscriptions();
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM subscriptions WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                subscriptions.setId(rs.getInt("id"));
                subscriptions.setUserId(rs.getLong("user_id"));
                subscriptions.setStartDate(rs.getDate("start_date"));
                subscriptions.setEndDate(rs.getDate("end_date"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get subscription with id - " + id);
            throw new RuntimeException(e);
        }
        return subscriptions;
    }

    @Override
    public List<Subscriptions> getAll() {
        List<Subscriptions> list = new ArrayList<>();
        String sql = "select * from subscriptions";
        PreparedStatement ps;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Subscriptions subscriptions = new Subscriptions();
                subscriptions.setId(rs.getInt("id"));
                subscriptions.setUserId(rs.getLong("user_id"));
                subscriptions.setStartDate(rs.getDate("start_date"));
                subscriptions.setEndDate(rs.getDate("end_date"));
                list.add(subscriptions);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all subscriptions ");
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void insert(Subscriptions subscriptions) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "INSERT INTO subscriptions (id, user_id, start_date, end_date) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, subscriptions.getId());
            ps.setLong(2, subscriptions.getUserId());
            ps.setDate(3, subscriptions.getStartDate());
            ps.setDate(4, subscriptions.getEndDate());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to insert subscription with user id - " + subscriptions.getUserId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Subscriptions subscriptions) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE subscriptions SET user_id = ?, start_date = ?, end_date = ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, subscriptions.getUserId());
            ps.setDate(2, subscriptions.getStartDate());
            ps.setDate(3, subscriptions.getEndDate());
            ps.setLong(4, subscriptions.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update subscription with id - " + subscriptions.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Subscriptions subscriptions) {
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "DELETE FROM subscriptions WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, subscriptions.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to delete subscription with id - " + subscriptions.getId());
            throw new RuntimeException(e);
        }
    }

    public Subscriptions getFromUserDao(long user_id) {
        Subscriptions subscriptions = null;
        try(Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "SELECT * FROM subscriptions WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                subscriptions = new Subscriptions();
                subscriptions.setId(rs.getInt("id"));
                subscriptions.setUserId(rs.getLong("user_id"));
                subscriptions.setStartDate(rs.getDate("start_date"));
                subscriptions.setEndDate(rs.getDate("end_date"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get subscription with user id - " + user_id);
            throw new RuntimeException(e);
        }
        return subscriptions;
    }
}
