package controller;

import util.ConnectionPool;

import java.sql.Connection;

public class Demo {
    public static void main(String[] args) {
        Connection con = ConnectionPool.getInstance().getConnection();
    }
}
