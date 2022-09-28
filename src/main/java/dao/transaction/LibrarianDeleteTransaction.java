package dao.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.Connector.getConnection;

public class LibrarianDeleteTransaction {

    public void deleteById(long librarian_id) {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

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
            ps.close();

            con.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
