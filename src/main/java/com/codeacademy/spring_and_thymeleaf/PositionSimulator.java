package com.codeacademy.spring_and_thymeleaf;

import com.codeacademy.spring_and_thymeleaf.model.Position;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class PositionSimulator {
    private final Random random = new Random();
    private final Timer timer = new Timer();

    public void runSimulator() {
        timer.schedule(new RunSimulator(), 0, 5000);
    }

    private class RunSimulator extends TimerTask {
        public void run() {
            List<Long> deviceIdList = getDeviceIdList();
            Long randomDeviceId = deviceIdList.get(random.nextInt(deviceIdList.size()));
            Position lastPosition = getLastPosition(randomDeviceId);
            Position newPosition = createNewPosition(lastPosition);
            insertNewPosition(newPosition, randomDeviceId);
        }
    }

    private Position createNewPosition(Position position) {
        Position newPosition = new Position();
        newPosition.setDate(LocalDateTime.now());
        double lastLatitude = position.getLatitude() != null ? position.getLatitude() : 0;
        newPosition.setLatitude(lastLatitude + getRandomDouble(-0.01, 0.01));
        double lastLongitude = position.getLongitude() != null ? position.getLongitude() : 0;
        newPosition.setLongitude(lastLongitude + getRandomDouble(-0.01, 0.01));
        int lastSpeed = position.getSpeed() != null ? position.getSpeed() : 0;
        newPosition.setSpeed(Math.max(lastSpeed + getRandomInt(-12, 12), 0));
       return newPosition;
    }

    private void insertNewPosition(Position position, Long deviceId) {
        Connection conn = connectH2();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = (String.format("insert into positions (device_id, date, latitude, longitude, speed)" +
                        "values (%s, '%s', %s, %s, %s)", deviceId, position.getDate(), position.getLatitude(), position.getLongitude(), position.getSpeed()));
                System.out.println(query);
                stmt.executeUpdate(query);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            disconn(conn);
        }
    }


    public Position getLastPosition(Long deviceId) {
        Connection conn = connectH2();
        Position position = new Position();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM positions WHERE device_id = " + deviceId + " ORDER BY DATE DESC LIMIT 1");
                while (rs.next()) {
                    position.setId(rs.getLong("id"));
                    position.setDate(rs.getTimestamp("date").toLocalDateTime());
                    position.setLatitude(rs.getDouble("latitude"));
                    position.setLongitude(rs.getDouble("longitude"));
                    position.setSpeed(rs.getInt("speed"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconn(conn);
        }
        return position;
    }

    public List<Long> getDeviceIdList() {
        Connection conn = connectH2();
        List<Long> deviceIdList = new ArrayList<>();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT device_id FROM devices");
                while (rs.next()) {
                    deviceIdList.add(rs.getLong("device_id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconn(conn);
        }
        return deviceIdList;
    }


    private Connection connectH2() {
        Connection conn = null;
        try {
            String dbURL = "jdbc:h2:mem:h2_db";
            Properties parameters = new Properties();
            parameters.put("user", "admin");
            parameters.put("password", "");
            conn = DriverManager.getConnection(dbURL, parameters);
        } catch (SQLException ex) {
            System.out.println("Nepavyko prisijungti prie DB");
            ex.printStackTrace();
        }
        return conn;
    }

    private void disconn(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public double getRandomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

}
