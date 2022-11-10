package dao.transaction;

import dao.megaEntity.MegaBookDaoImpl;
import entity.PersonalInfo;
import util.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CreateLibrarianTransaction {
    static final Logger logger = Logger.getLogger(String.valueOf(CreateLibrarianTransaction.class));
    private static CreateLibrarianTransaction instance;

    private CreateLibrarianTransaction(){}
    public static CreateLibrarianTransaction getInstance(){
        logger.info("CreateLibrarianTransaction instance");
        if(instance==null)
            instance=new CreateLibrarianTransaction();
        return instance;
    }

    public void create(PersonalInfo personalInfo){
        Connection con = null;
        try {
            logger.info("start transaction to create librarian");
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            logger.info("set autocommit - false");

            String sql = "INSERT INTO personal_info (first_name, last_name, login, password) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, personalInfo.getFirstName());
            ps.setString(2, personalInfo.getLastName());
            ps.setString(3, personalInfo.getLogin());
            ps.setString(4, personalInfo.getPassword());
            ps.executeUpdate();

            String sql2 = "SELECT id FROM personal_info WHERE login = ?";
            long id = 0L;
            ps = con.prepareStatement(sql2);
            ps.setString(1, personalInfo.getLogin());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getLong("id");
            }
            ps.close();

            String sql3 = "INSERT INTO librarian (person_id) VALUES(?);";
            ps = con.prepareStatement(sql3);
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
            con.commit();
            logger.info("con commit");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
                logger.info("rollback is success");
            } catch (SQLException e2) {
                logger.warning("rollback is failed");
                e2.printStackTrace();
            }
        }
    }
}
