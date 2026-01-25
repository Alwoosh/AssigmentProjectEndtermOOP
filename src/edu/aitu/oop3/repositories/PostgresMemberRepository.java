package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.models.Member;

import java.sql.*;
import java.time.LocalDate;

public class PostgresMemberRepository implements MemberRepository {
    @Override
    public Member findById(int id) {
        String sql = "SELECT id, name, email, expiry_date FROM members WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getDate("expiry_date").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, expiry_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setDate(3, Date.valueOf(member.getExpiryDate()));
            pstmt.setInt(4, member.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
