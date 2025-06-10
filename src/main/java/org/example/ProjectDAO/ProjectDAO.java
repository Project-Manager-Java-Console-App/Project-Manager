package org.example.ProjectDAO;

import org.example.dataBase.AbstractFuncProjectTask;
import org.example.model.Project;
import org.example.model.Status;
import org.example.model.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectDAO extends AbstractFuncProjectTask<Project,Integer> {
    @Override
    protected String tableName() {
        return "project";
    }

    @Override
    protected Project fromResultSet(ResultSet rs) throws SQLException {
        Project p = new Project();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("project_name"));
        p.setDescription(rs.getString("description"));
        Date sqlDate = rs.getDate("createdDate");
        if (sqlDate != null) {
            p.setCreatedDate(sqlDate.toLocalDate());
        }
        p.setCreatedByUserId(rs.getInt("created_by"));
        return p;
    }

    @Override
    protected void fillPreparedStatementForInsert(PreparedStatement ps, Project dto) throws SQLException {
        ps.setString(1, dto.getName());
        ps.setObject(2, dto.getCreatedDate());
        ps.setString(3, dto.getDescription());
        ps.setInt(4,dto.getCreatedByUserId());

    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement ps, Project dto, Integer updatedObjectId) throws SQLException {
        ps.setString(1, dto.getName());
        ps.setString(2, dto.getDescription());
        ps.setInt(3,updatedObjectId);
    }



    @Override
    protected Integer extractGeneratedKey(ResultSet rs) throws SQLException {
       return rs.getInt(1);
    }

    @Override
    protected String connectingTableName() {
        return "User_Project_assignment";
    }

    @Override
    protected String getAllByAddedUser() {
        return "Select id from "+ connectingTableName()+" where user_id = ?";
    }

    @Override
    protected Integer extractAddedUsersIdFromRow(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }

    @Override
    protected String addUserQuery(int project_id, int user_id) {
        return "Insert into "+connectingTableName()+"(id,user_id,assigment_date) Values(?,?,?)";
    }

    @Override
    protected String getAddedUsers() {
        return "Select user_id from "+ connectingTableName()+" where id =?";
    }

    @Override
    protected String insertSQL() {
        return "Insert into "+tableName()+"(project_name, createdDate, description, created_by) Values (?,?,?,?)";
    }

    @Override
    protected String updateSQL() {

        return "Update project set project_name=?, description=? where id=?";
    }

    @Override
    protected String findByNameSQL()  {
        return "Select * from project where project_name = ?";
    }

    //baca
    public List<Task> findTasksByProjectId(Integer project_id) throws SQLException {
        String query = "Select * from Task where project_id=?";
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, project_id);
            try (ResultSet rs = ps.executeQuery()) {
                List<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setName(rs.getString("task_name"));
                    task.setDescription(rs.getString("description"));
                    Date sqlDate = rs.getDate("createdDate");
                   if (sqlDate != null) {
                       task.setStartDate(sqlDate.toLocalDate());
                   }
                   task.setStatus(Status.valueOf(rs.getString("status")));
                   task.setProject_id(rs.getInt("project_id"));
                   tasks.add(task);
                }
                return tasks;
            }
        }
    }

    public boolean addTaskToProject(Integer project_id, Integer task_id) throws SQLException {
        String sql = "Update task set project_id=? where id=?";
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, project_id);
            ps.setInt(2, task_id);
            return ps.executeUpdate() > 0;
        }
    }
}
