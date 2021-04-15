package main.java.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.component.Car;

public class CarDao implements Dao<Car> {
    // Singleton
    private static CarDao instance;

    private CarDao() {

    }

    public static CarDao getInstance() {
        if (instance == null)
            instance = new CarDao();
        return instance;
    }

    @Override
    public void save(Car car) throws SQLException {
        Connection conn = H2.getConnection();
        PreparedStatement p = conn
                .prepareStatement("INSERT INTO Car (company, model, year, price, color) VALUES (?, ?, ?, ?, ?)");
        p.setString(1, car.getCompany());
        p.setString(2, car.getModel());
        p.setInt(3, car.getYear());
        p.setInt(4, car.getPrice());
        p.setString(5, car.getColor());
        p.execute();

        conn.close();
    }

    @Override
    public Car get(long id) throws SQLException {
        Connection conn = H2.getConnection();
        PreparedStatement p = conn.prepareStatement("SELECT * FROM Car WHERE id=?");
        p.setLong(1, id);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            Car car = new Car(rs.getString("company"), rs.getString("model"), rs.getInt("year"), rs.getInt("price"),
                    rs.getString("color"));
            car.setId(rs.getLong("id"));
            return car;
        }

        return null;
    }

    @Override
    public void update(long id, Car car) throws SQLException {
        Connection conn = H2.getConnection();
        PreparedStatement p = conn
                .prepareStatement("UPDATE Car SET company=?, model=?, year=?, price=?, color=? WHERE id=?");
        p.setString(1, car.getCompany());
        p.setString(2, car.getModel());
        p.setInt(3, car.getYear());
        p.setInt(4, car.getPrice());
        p.setString(5, car.getColor());

        p.setLong(6, id);

        p.execute();
        conn.close();
    }

    @Override
    public void delete(Car car) throws SQLException {
        Connection conn = H2.getConnection();
        PreparedStatement p = conn.prepareStatement("DELETE FROM Car WHERE id=?");
        p.setLong(1, car.getId());

        p.execute();
        conn.close();
    }
}