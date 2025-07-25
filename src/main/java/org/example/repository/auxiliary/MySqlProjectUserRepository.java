package org.example.repository.auxiliary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataBase.DatabaseManager;
import org.example.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MySqlProjectUserRepository implements ProjectUserRepository {
    private final Connection conn = DatabaseManager.getInstance().getConnection();
    private final Logger logger = LogManager.getLogger(MySqlProjectUserRepository.class);

    @Override
    public List<Integer> getUsersIn(Integer projectId) {
        String query = "SELECT user_id FROM user_project_assignment WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            return getIntegers(projectId, stmt);
        } catch (SQLException e) {
            logger.error("Error while getting users in project: {}", e.getMessage());
            return null;
        }
    }

    public static List<Integer> getIntegers(Integer projectId, PreparedStatement stmt) {

        try (ResultSet rs = stmt.executeQuery()) {
            stmt.setInt(1, projectId);
            List<Integer> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                users.add(id);
            }
            return users;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean delete(Integer projectId, Integer userId) {
        String query = "DELETE FROM user_project_assignment WHERE id = ? and user_id =?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("SQL error while deleting user to project: {} ", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean save(Integer projectId, Integer userId) {
        String query = "INSERT INTO user_project_assignment (id, user_id, assigment_date) VALUES (?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            stmt.setObject(3, LocalDate.now());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("SQL error while assigning user to project: {} ", e.getMessage());
            return false;
        }
    }

    @Override
    public List<Project> getAllProjectsCreatedByUser(Integer userId) {
        String query = "SELECT * FROM project WHERE created_by = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Project> ids = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString("project_name");
                    String description = rs.getString("description");
                    Project pr = Project.create(name, description, userId);
                    pr.setId(id);
                    ids.add(pr);
                }
                return ids;
            }
        } catch (SQLException e) {
            logger.error("SQL error while getting projects created by user:  {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<Integer> getAllAddedToUser(Integer userId) {
        String query = "SELECT id FROM user_project_assignment WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            return getIntegers(userId, stmt);
        } catch (SQLException e) {
            logger.error("SQL error while getting projects added to user: {}", e.getMessage());
            return null;
        }
    }
}
