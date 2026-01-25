package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.models.ClassBooking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookingRepository implements BookingRepository {
    @Override
    public boolean exists(int memberId, int classId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE member_id = ? AND class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, classId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void save(int memberId, int classId) {
        String sql = "INSERT INTO bookings (member_id, class_id, booking_time) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, classId);
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ClassBooking> findByMember(int memberId) {
        List<ClassBooking> bookings = new ArrayList<>();
        String sql = "SELECT id, member_id, class_id, booking_time FROM bookings WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(new ClassBooking(
                            rs.getInt("id"),
                            rs.getInt("member_id"),
                            rs.getInt("class_id"),
                            rs.getTimestamp("booking_time").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
