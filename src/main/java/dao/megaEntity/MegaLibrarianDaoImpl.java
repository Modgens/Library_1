package dao.megaEntity;

import entity.PersonalInfo;
import entity.megaEntity.MegaLibrarian;
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

public class MegaLibrarianDaoImpl {
    static final Logger logger = Logger.getLogger(String.valueOf(MegaLibrarianDaoImpl.class));
    private static MegaLibrarianDaoImpl instance;

    public MegaLibrarianDaoImpl() {
    }

    public static MegaLibrarianDaoImpl getInstance() {
        logger.info("Mega Librarian Class Instance");
        if (instance == null)
            instance = new MegaLibrarianDaoImpl();
        return instance;
    }

    public List<MegaLibrarian> getAll() {
        List<MegaLibrarian> list = new ArrayList<>();
        String sql = "select *from librarian inner join personal_info on librarian.person_id=personal_info.id ";
        PreparedStatement ps;
        try (Connection con = ConnectionPool.getInstance().getConnection()) {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MegaLibrarian librarian = (MegaLibrarian) new PersonFactory().createPerson(PersonType.LIBRARIAN);
                librarian.setId(rs.getLong("id"));
                librarian.setPersonalInfo(
                        new PersonalInfo(
                                rs.getLong("person_id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("first_name"),
                                rs.getString("last_name")));
                list.add(librarian);
            }
            ps.close();
        } catch (SQLException e) {
            logger.warning("failed to get all mega librarians");
            throw new RuntimeException(e);
        }
        return list;
    }
}
