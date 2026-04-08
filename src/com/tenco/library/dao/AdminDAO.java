package com.tenco.library.dao;

import com.tenco.library.dto.Admin;
import com.tenco.library.util.DatabaseUtil;

import java.sql.*;

public class AdminDAO {

    // 관리자 인증 (admin_id + password 조회)
    public Admin authenticateAdmin(String adminId, String password) throws SQLException {

        String sql = """
                select * from admins where admin_id = ? AND password = ?
                """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Admin.builder()
                            .id(rs.getInt("id"))
                            .adminId(rs.getString("admin_id"))
                            .name(rs.getString("name"))
                            .build();
                    // 인증 후에는 일반적으로 비밀번호를 리턴 하지 않음
                }
            }
        }
        // 인증 실패
        return null;
    }


}
