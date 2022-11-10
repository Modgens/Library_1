package dao.transaction;

import util.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LibrarianDeleteTransaction {
    static final Logger logger = Logger.getLogger(String.valueOf(LibrarianDeleteTransaction.class));
    public void deleteById(long librarian_id) {
        Connection con = null;
        try {
            logger.info("start transaction to delete Librarian");
            con = ConnectionPool.getInstance().getConnection();
            con.setAutoCommit(false);
            logger.info("set autocommit - false");
            long personId=0L;

            String sql = "SELECT person_id FROM dblibrary.librarian WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, librarian_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                personId = rs.getLong("person_id");
            }
            rs.close();

            String sql2 = "DELETE FROM dblibrary.librarian WHERE id = ?";
            ps = con.prepareStatement(sql2);
            ps.setLong(1, librarian_id);
            ps.executeUpdate();

            String sql3 = "DELETE FROM dblibrary.personal_info WHERE id = ?";
            ps = con.prepareStatement(sql3);
            ps.setLong(1, personId);
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
