package dao;

import entity.Admin;

public interface AdminDAO extends DAO<Admin>{
    boolean hasInfoId(long id);
}
