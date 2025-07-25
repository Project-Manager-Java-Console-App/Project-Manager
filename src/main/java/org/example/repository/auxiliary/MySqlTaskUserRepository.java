package org.example.repository.auxiliary;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataBase.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.repository.auxiliary.MySqlProjectUserRepository.getIntegers;


public class MySqlTaskUserRepository implements TaskUserRepository {
    private final Connection conn = DatabaseManager.getInstance().getConnection();
    private final Logger logger = LogManager.getLogger(MySqlTaskUserRepository.class);


    @Override
    public List<Integer> getUsersIn(Integer taskId) {
        String query = "SELECT user_id FROM user_task_assigment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            return getIntegers(taskId, stmt);
        } catch (SQLException e) {
            logger.error("Failed to get added users to task {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(Integer taskId, Integer userId) {
        String query = "DELETE FROM user_task_assigment WHERE id = ? and user_id =?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("SQL error while deleting user to task: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean save(Integer taskId, Integer userId) {
        String query = "Insert into user_task_assigment (id, user_id,assigment_date) VALUES (?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            stmt.setInt(2, userId);
            stmt.setObject(3, LocalDate.now());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("SQL error while assigning user to task: {}", e.getMessage());
        }
        return false;
    }

    @Override
    public List<Integer> getAllAddedToUser(Integer userId) {
        String query = "SELECT id FROM user_task_assigment WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> ids = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    ids.add(id);
                }
                return ids;
            }
        } catch (SQLException e) {
            logger.error("Sql error while getting tasks added by user: {}", e.getMessage());
            return null;
        }
    }
}
