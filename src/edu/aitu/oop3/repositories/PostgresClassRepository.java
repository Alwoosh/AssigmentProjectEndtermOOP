package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.models.FitnessClass;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostgresClassRepository implements ClassRepository {
    @Override
    public FitnessClass findById(int id) {
        String sql = "SELECT id, title, instructor_name, max_capacity, schedule_time FROM classes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new FitnessClass(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("instructor_name"),
                            rs.getInt("max_capacity"),
                            rs.getTimestamp("schedule_time").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCurrentBookingCount(int classId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, classId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void save(FitnessClass fitnessClass) {
        String sql = "INSERT INTO classes (title, instructor_name, max_capacity, schedule_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fitnessClass.getTitle());
            pstmt.setString(2, fitnessClass.getInstructorName());
            pstmt.setInt(3, fitnessClass.getMaxCapacity());
            pstmt.setTimestamp(4, Timestamp.valueOf(fitnessClass.getScheduleTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM classes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FitnessClass> findAll() {
        List<FitnessClass> classes = new ArrayList<>();
        String sql = "SELECT id, title, instructor_name, max_capacity, schedule_time FROM classes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                classes.add(new FitnessClass(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("instructor_name"),
                        rs.getInt("max_capacity"),
                        rs.getTimestamp("schedule_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
