package org.example.dataBase;

import java.sql.*;

public abstract class AbstractCRUDOperations <T,K> {

    protected abstract String tableName();
    protected abstract T fromResultSet(ResultSet rs) throws SQLException;
    protected abstract void fillPreparedStatementForInsert(PreparedStatement ps, T dto) throws SQLException;
    protected abstract void fillPreparedStatementForUpdate(PreparedStatement ps, T dto, K updatedObjectId) throws SQLException;
    protected abstract K extractGeneratedKey(ResultSet rs) throws SQLException;
    protected abstract String insertSQL();
    protected abstract String updateSQL();
    protected abstract String findByNameSQL();

    protected Connection getConnection() throws SQLException {
        return DatabaseUtils.getConnection();
    }

    //baca
    public K save(T object) throws SQLException {
        String query = insertSQL();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ) {
           fillPreparedStatementForInsert(ps, object);
            int rowcount = ps.executeUpdate();
            if (rowcount == 0) {
                throw new SQLException("Failed to insert into Project");
            }
            try(ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()){
                    return extractGeneratedKey(keys);
                }else {
                    throw new SQLException("Failed to insert into Project");
                }
            }
        }
    }

    //baca
    public boolean update(T object, K updatedObjectId) throws SQLException {
        String query = updateSQL();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query))
        {
           fillPreparedStatementForUpdate(ps,object,updatedObjectId);
            return ps.executeUpdate() > 0;
        }
    }

    //baca
    public boolean delete(K id) throws SQLException {
        String query = "DELETE FROM "+tableName()+" WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setObject(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public T findByName(String name) throws SQLException {
        String query = findByNameSQL();
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1,name);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())return null;
                return fromResultSet(rs);
            }
        }
    }
}




/*Project func
                System.out.println("Enter 1 for creating a project");
                System.out.println("Enter 2 for updating a project");
                System.out.println("Enter 3 for finding a project by name");
                System.out.println("Enter 4 for assigning a task to a project");
                System.out.println("Enter 5 for assigning a user to a project");
                System.out.println("Enter 6 for removing a user from a project");
                System.out.println("Enter 7 for displaying all tasks associated with a project");
                System.out.println("Enter 8 for displaying all users associated with a project");
                System.out.println("Enter 9 for deleting a project");
                //Task func
                System.out.println("Enter 10 for creating a new task in current project");
                System.out.println("Enter 11 for updating a task");
                System.out.println("Enter 12 for finding a task by name");
                System.out.println("Enter 13 for removing a user from a task");
                System.out.println("Enter 14 for change task Status");
                System.out.println("Enter 15 for assigning a user to a task");
                System.out.println("Enter 16 for displaying all users id associated with a task");
                System.out.println("Enter 17 for deleting a task");

                //User func
                System.out.println("Enter 18 for updating a username");
                System.out.println("Enter 19 for finding a user by name");
                System.out.println("Enter 20 for displaying all projects created by user");
                System.out.println("Enter 21 for displaying all projects in which the user is assigned");
                System.out.println("Enter 22 for displaying all tasks created by user");
                System.out.println("Enter 23 for displaying all tasks in which the user is assigned");
                System.out.println("Enter 24 for displaying a user profile");
                System.out.println("Enter 25 for deleting a your user profile");
                System.out.println("Enter 26 for Log out");
                System.out.println("Enter 27 for exit");
                */