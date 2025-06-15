package org.example.Repository;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Project;
import java.sql.*;

public class MySqlProjectRepository
        implements ProjectRepository {
    private final Connection conn;

    public MySqlProjectRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Project save(Project project) {
        String sql = "INSERT INTO project(project_name,description,created_by,createdDate) VALUES(?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, project.getCreatedByUserId());
            stmt.setDate(4,Date.valueOf(project.getCreatedDate()));
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if(rs.next()) {
                        project.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new ProjectIdNotFound();
        }
        return project;
    }


    @Override
    public boolean delete(Project project) throws SQLException {
        String query = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, project.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new ProjectIdNotFound();
        }
    }

    @Override
    public Project update(Project project, int id) throws SQLException {
        String sql = "UPDATE project SET project_name = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            if(rows == 0) {
                throw new ProjectIdNotFound();
            }
        }catch (SQLException e){
            throw new ProjectIdNotFound();
        }
        return project;
    }

    @Override
    public Project findByName(String name) throws UsernameAlreadyExistsException {
        String query = "SELECT * FROM project WHERE project_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())return null;
                int id = rs.getInt("id");
                String description = rs.getString("description");
                int createdByUserId = rs.getInt("created_by");

                Project project = Project.create(name, description, createdByUserId);
                project.setId(id);
                return project;
            }
        }
        catch (SQLException e) {
            throw new UsernameAlreadyExistsException(name);
        }
    }

    @Override
    public Project findById(Integer id) {
        String query = "SELECT * FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                String description = rs.getString("description");
                int createdByUserId = rs.getInt("created_by");
                String projectName = rs.getString("project_name");
                Project project = Project.create(projectName, description, createdByUserId);
                project.setId(id);
                return project;
            }
        }catch (SQLException e){
            throw new ProjectIdNotFound();
        }
    }
}