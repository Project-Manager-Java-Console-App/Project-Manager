package org.example.Repository;

import org.example.Exceptions.UserIdNotFound;
import org.example.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssigmentHelper {

   public static List<Integer> getIntegers(Integer userId, String query, Connection conn) {
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> ids= new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    ids.add(id);
                }
                return ids;
            }
        }catch (SQLException e){
            throw new UserIdNotFound();
        }
    }

    public static List<Users> getUsers(Integer taskId, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, taskId);
        try (ResultSet rs = stmt.executeQuery()) {
            List<Users> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                Users u = Users.createUser(username);
                users.add(u);
            }
            return users;
        }
    }
}
