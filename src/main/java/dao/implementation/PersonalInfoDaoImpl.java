package dao.implementation;

import dao.PersonalInfoDAO;
import entity.PersonalInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.Connector.getConnection;

public class PersonalInfoDaoImpl implements PersonalInfoDAO {

    private void setAllInEntity(PersonalInfo personalInfo, ResultSet rs) throws SQLException {
        personalInfo.setId(rs.getLong("id"));
        personalInfo.setPassword(rs.getString("password"));
        personalInfo.setFirstName(rs.getString("first_name"));
        personalInfo.setLastName(rs.getString("last_name"));
        personalInfo.setLogin(rs.getString("login"));
    }

    @Override
    public List<PersonalInfo> getAll(){
        List<PersonalInfo> list = new ArrayList<>();
        String sql = "select * from personal_info";
        PreparedStatement ps;
        try(Connection con = getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                PersonalInfo personalInfo = new PersonalInfo();
                setAllInEntity(personalInfo, rs);
                list.add(personalInfo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public PersonalInfo get(long id) {
        PersonalInfo personalInfo = new PersonalInfo();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM personal_info WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(personalInfo, rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return personalInfo;
    }


    @Override
    public void insert(PersonalInfo personalInfo) {
        try(Connection con = getConnection()) {
            String sql = "INSERT INTO personal_info (first_name, last_name, login, password) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, personalInfo.getFirstName());
            ps.setString(2, personalInfo.getLastName());
            ps.setString(3, personalInfo.getLogin());
            ps.setString(4, personalInfo.getPassword());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PersonalInfo personalInfo) {
        try(Connection con = getConnection()) {
            String sql = "UPDATE personal_info SET first_name=?, last_name=?, login=?, password=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, personalInfo.getFirstName());
            ps.setString(2, personalInfo.getLastName());
            ps.setString(3, personalInfo.getLogin());
            ps.setString(4, personalInfo.getPassword());
            ps.setLong(5, personalInfo.getId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(PersonalInfo personalInfo) {
        try(Connection con = getConnection()) {
            String sql = "DELETE FROM personal_info WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, personalInfo.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PersonalInfo findByLoginAndPassword(String login, String password) {
        PersonalInfo personalInfo = new PersonalInfo();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM personal_info WHERE login = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(personalInfo, rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return personalInfo;
    }

    @Override
    public PersonalInfo findByLogin(String login) {
        PersonalInfo personalInfo = new PersonalInfo();
        try(Connection con = getConnection()) {
            String sql = "SELECT * FROM personal_info WHERE login = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                setAllInEntity(personalInfo, rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return personalInfo;
    }

    @Override
    public long getId(String login) {
        long id=0;
        try(Connection con = getConnection()) {
            String sql = "SELECT id FROM personal_info WHERE login = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getLong("id");
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return id;
    }


}
