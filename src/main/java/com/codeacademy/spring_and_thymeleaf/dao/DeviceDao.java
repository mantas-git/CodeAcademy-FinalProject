package com.codeacademy.spring_and_thymeleaf.dao;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceDao {
    private final DataSource dataSource;

    public DeviceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Device> getAllTopics() {
        List<Device> devices = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement("select * from devices");

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String vnr = resultSet.getString("vnr");
                String comment = resultSet.getString("comment");
                LocalDate createDate = resultSet.getDate("create_date").toLocalDate();
                Integer userId = resultSet.getInt("user_id");
                devices.add(new Device(id, vnr, comment, createDate, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (pstmt != null)
                    pstmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return devices;
    }

//    public void addNewTopic(Topic newTopic) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        try {
//            con = dataSource.getConnection();
//            pstmt = con.prepareStatement("insert into topic (title, header) values (?,?)");
//            pstmt.setString(1, newTopic.getTitle());
//            pstmt.setString(2, newTopic.getHeader());
//            pstmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (pstmt != null)
//                    pstmt.close();
//                if (con != null)
//                    con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
