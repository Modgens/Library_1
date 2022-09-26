package dao;

import entity.Librarian;

public interface LibrarianDAO extends DAO<Librarian> {
    boolean hasInfoId(long id);
}
