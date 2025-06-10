package org.example.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFuncProjectTask<T,K> extends AbstractCRUDOperations<T,K> {

    protected abstract K extractAddedUsersIdFromRow(ResultSet rs) throws SQLException;
    protected abstract String addUserQuery(int id, int user_id);
    protected abstract String getAddedUsers();
    protected abstract String connectingTableName();
    protected abstract String getAllByAddedUser();


    public List<K> getAddedUsersTo(K id) throws SQLException {
        String query = getAddedUsers();
        return getKs(id, query);
    }

    public List<K> getAllByUser(K user_id) throws SQLException {
        String query = getAllByAddedUser();
        return getKs(user_id, query);
    }

    private List<K> getKs(K user_id, String query) throws SQLException {
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, user_id);
            try(ResultSet rs = ps.executeQuery()) {
                List<K> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(extractAddedUsersIdFromRow(rs));
                }
                return list;
            }
        }
    }

    public boolean addUserTo(int id, int user_id) throws SQLException {
        String query = addUserQuery(id,user_id);
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setInt(2, user_id);
            ps.setObject(3, LocalDate.now());
            return ps.executeUpdate() > 0;
        }
    }

    public List<T> findByUserId(K user_id) throws SQLException {
        String query = "SELECT * from "+tableName()+" where created_by = ?";
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1,user_id);
            try (ResultSet rs = ps.executeQuery()) {

                List<T> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(fromResultSet(rs));
                }
                return list;
            }
        }
    }

    public boolean removeUserFrom(K id, K user_id) throws SQLException {
        String query = "DELETE FROM "+connectingTableName()+" where user_id = ? AND id = ?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1,user_id);
            ps.setObject(2,id);
            return ps.executeUpdate() > 0;
        }
    }
}
