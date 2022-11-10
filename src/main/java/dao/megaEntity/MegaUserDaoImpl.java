package dao.megaEntity;

import entity.PersonalInfo;
import entity.megaEntity.MegaUser;
import entity.megaEntity.PersonFactory;
import entity.megaEntity.PersonType;
import util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MegaUserDaoImpl {
    static final Logger logger = Logger.getLogger(String.valueOf(MegaUserDaoImpl.class));
    private static MegaUserDaoImpl instance;

    public MegaUserDaoImpl() {
    }

    public static MegaUserDaoImpl getInstance() {
        logger.info("Mega User Class Instance");
        if (instance == null)
            instance = new MegaUserDaoImpl();
        return instance;
    }

    public static void update(MegaUser user) {
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            String sql = "UPDATE user SET person_id = ?, email = ?, status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, user.getPersonalInfo().getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getStatus());
            ps.setLong(4, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to update mega user with user id - " + user.getId());
            throw new RuntimeException(e);
        }
    }

    public List<MegaUser> getAll() {
        List<MegaUser> list = new ArrayList<>();
        String sql = "select *from user inner join personal_info on user.person_id=personal_info.id ";
        PreparedStatement ps;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MegaUser user = (MegaUser) new PersonFactory().createPerson(PersonType.USER);
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPersonalInfo(
                        new PersonalInfo(
                                rs.getLong("person_id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("first_name"),
                                rs.getString("last_name")));
                user.setStatus(rs.getString("status"));
                list.add(user);
            }
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all mega users");
            throw new RuntimeException(e);
        }
        return list;
    }

    public MegaUser get(long user_id) {
        String sql = "select *from user inner join personal_info on user.person_id=personal_info.id where user.id=?";
        PreparedStatement ps;
        MegaUser user = null;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ps.setLong(1, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = (MegaUser) new PersonFactory().createPerson(PersonType.USER);
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPersonalInfo(
                        new PersonalInfo(
                                rs.getLong("person_id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("first_name"),
                                rs.getString("last_name")));
                user.setStatus(rs.getString("status"));
            }
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get mega user with user id - " + user_id);
            throw new RuntimeException(e);
        }
        return user;
    }
}
