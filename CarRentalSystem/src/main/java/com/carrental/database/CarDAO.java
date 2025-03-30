package com.carrental.database;

import com.carrental.DatabaseConnection;
import com.carrental.models.Car;

import java.sql.*;
import java.util.*;

public class CarDAO {

	public static List<Car> getAllCars() {
		List<Car> cars = new ArrayList<>();
		String query = "SELECT * FROM CR_Inventory ORDER BY car_id DESC";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Car car = new Car(rs.getInt("car_id"), rs.getString("make"), rs.getString("model"), rs.getInt("year"),
						rs.getDouble("price_per_day"), rs.getBoolean("availability"), rs.getString("image_path"));
				cars.add(car);
			}
		} catch (SQLException e) {
			System.err.println("Database error in getAllCars(): " + e.getMessage());
		}

		return cars;
	}

	public static boolean insertCar(Car car) {
		String query = "INSERT INTO CR_Inventory (make, model, year, price_per_day, availability, image_path) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, car.getMake());
			stmt.setString(2, car.getModel());
			stmt.setInt(3, car.getYear());
			stmt.setDouble(4, car.getPricePerDay());
			stmt.setBoolean(5, car.isAvailability());
			stmt.setString(6, car.getImagePath());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Database error in insertCar(): " + e.getMessage());
			return false;
		}
	}

	public static boolean updateCar(Car car) {
		String query = "UPDATE CR_Inventory SET make = ?, model = ?, year = ?, price_per_day = ?, availability = ?, image_path = ? WHERE car_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, car.getMake());
			stmt.setString(2, car.getModel());
			stmt.setInt(3, car.getYear());
			stmt.setDouble(4, car.getPricePerDay());
			stmt.setBoolean(5, car.isAvailability());
			stmt.setString(6, car.getImagePath());
			stmt.setInt(7, car.getCarId());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Database error in updateCar(): " + e.getMessage());
			return false;
		}
	}

	public static boolean deleteCar(int carId) {
		String query = "DELETE FROM CR_Inventory WHERE car_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, carId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Database error in deleteCar(): " + e.getMessage());
			return false;
		}
	}

	// ======= REPORT METHODS =======

	public static Map<String, Integer> getCarUsageReport() {
		return executeReportQueryInteger(
				"SELECT c.model, COUNT(*) AS rental_count FROM CR_Booking b JOIN CR_Inventory c ON b.car_id = c.car_id WHERE b.status IN ('Completed', 'Confirmed') GROUP BY c.model ORDER BY rental_count DESC",
				"getCarUsageReport()");
	}

	public static Map<String, String> getCarUsageReportAsString() {
		return executeReportQueryString(
				"SELECT c.model, COUNT(*) AS rental_count FROM CR_Booking b JOIN CR_Inventory c ON b.car_id = c.car_id WHERE b.status IN ('Completed', 'Confirmed') GROUP BY c.model ORDER BY rental_count DESC",
				"getCarUsageReportAsString()");
	}

	public static Map<String, Integer> getMonthlyRentalsReport() {
		return executeReportQueryInteger(
				"SELECT DATE_FORMAT(start_date, '%Y-%m') AS month, COUNT(*) AS total_rentals FROM CR_Booking WHERE status IN ('Completed', 'Confirmed') GROUP BY month ORDER BY month",
				"getMonthlyRentalsReport()");
	}

	public static Map<String, String> getMonthlyRentalsReportAsString() {
		return executeReportQueryString(
				"SELECT DATE_FORMAT(start_date, '%Y-%m') AS month, COUNT(*) AS total_rentals FROM CR_Booking WHERE status IN ('Completed', 'Confirmed') GROUP BY month ORDER BY month",
				"getMonthlyRentalsReportAsString()");
	}

	public static Map<String, Double> getRevenueReport() {
		return executeRevenueReportQueryDouble(
				"SELECT DATE_FORMAT(payment_date, '%Y-%m') AS month, SUM(amount) AS total_revenue FROM CR_Payment GROUP BY month ORDER BY month",
				"getRevenueReport()");
	}

	public static Map<String, String> getRevenueReportAsString() {
		return executeRevenueReportQueryString(
				"SELECT DATE_FORMAT(payment_date, '%Y-%m') AS month, SUM(amount) AS total_revenue FROM CR_Payment GROUP BY month ORDER BY month",
				"getRevenueReportAsString()");
	}

	public static Map<String, Integer> getCustomerBookingReport() {
		return executeReportQueryInteger("SELECT status, COUNT(*) AS total_bookings FROM CR_Booking GROUP BY status",
				"getCustomerBookingReport()");
	}

	public static Map<String, String> getCustomerBookingReportAsString() {
		return executeReportQueryString("SELECT status, COUNT(*) AS total_bookings FROM CR_Booking GROUP BY status",
				"getCustomerBookingReportAsString()");
	}

	public static Map<String, Integer> getCarInventoryReport() {
		return executeReportQueryInteger(
				"SELECT availability, COUNT(*) AS total_cars FROM CR_Inventory GROUP BY availability",
				"getCarInventoryReport()");
	}

	public static Map<String, String> getCarInventoryReportAsString() {
		return executeReportQueryString(
				"SELECT availability, COUNT(*) AS total_cars FROM CR_Inventory GROUP BY availability",
				"getCarInventoryReportAsString()");
	}

	// ======= HELPER METHODS =======

	private static Map<String, Integer> executeReportQueryInteger(String query, String methodName) {
		Map<String, Integer> reportData = new LinkedHashMap<>();
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				reportData.put(rs.getString(1), rs.getInt(2));
			}
		} catch (SQLException e) {
			System.err.println("Database error in " + methodName + ": " + e.getMessage());
		}
		return reportData;
	}

	private static Map<String, String> executeReportQueryString(String query, String methodName) {
		Map<String, String> reportData = new LinkedHashMap<>();
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				reportData.put(rs.getString(1), String.valueOf(rs.getInt(2)));
			}
		} catch (SQLException e) {
			System.err.println("Database error in " + methodName + ": " + e.getMessage());
		}
		return reportData;
	}

	private static Map<String, Double> executeRevenueReportQueryDouble(String query, String methodName) {
		Map<String, Double> reportData = new LinkedHashMap<>();
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				reportData.put(rs.getString(1), rs.getDouble(2));
			}
		} catch (SQLException e) {
			System.err.println("Database error in " + methodName + ": " + e.getMessage());
		}
		return reportData;
	}

	private static Map<String, String> executeRevenueReportQueryString(String query, String methodName) {
		Map<String, String> reportData = new LinkedHashMap<>();
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				reportData.put(rs.getString(1), String.valueOf(rs.getDouble(2)));
			}
		} catch (SQLException e) {
			System.err.println("Database error in " + methodName + ": " + e.getMessage());
		}
		return reportData;
	}

	public Map<String, Integer> getUserCounts() throws SQLException {
		String query = "SELECT role, COUNT(*) as count FROM CR_User GROUP BY role";
		return executeReportQueryInteger(query, "getUserCounts()");
	}

	public Map<String, Integer> getRideStats() throws SQLException {
		Map<String, Integer> stats = new HashMap<>();

		String ridesQuery = "SELECT COUNT(*) AS total_rides FROM CR_Booking WHERE status IN ('Completed', 'Confirmed')";
		String paymentsQuery = "SELECT SUM(amount) AS total_amount FROM CR_Payment WHERE payment_status = 'Completed'";

		try (Connection conn = DatabaseConnection.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(ridesQuery); ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					stats.put("total_rides", rs.getInt("total_rides"));
				}
			}

			try (PreparedStatement stmt = conn.prepareStatement(paymentsQuery); ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int totalProfit = (int) rs.getDouble("total_amount");
					int estimatedTax = (int) (totalProfit * 0.20); // 20% tax assumption
					stats.put("total_profit", totalProfit);
					stats.put("total_tax", estimatedTax);
				}
			}
		}

		return stats;
	}

	public Map<String, Integer> getCarStatusCounts() throws SQLException {
		Map<String, Integer> stats = new HashMap<>();

		try (Connection conn = DatabaseConnection.getConnection()) {
			// Cars Rented
			String rentedQuery = "SELECT COUNT(DISTINCT car_id) AS count FROM CR_Booking WHERE status IN ('Confirmed', 'Completed') AND end_date >= CURDATE()";
			try (PreparedStatement stmt = conn.prepareStatement(rentedQuery); ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					stats.put("rented", rs.getInt("count"));
				}
			}

			// Cars in Maintenance
			String maintenanceQuery = "SELECT COUNT(DISTINCT car_id) AS count FROM CR_Maintenance WHERE maintenance_date >= CURDATE()";
			try (PreparedStatement stmt = conn.prepareStatement(maintenanceQuery); ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					stats.put("maintenance", rs.getInt("count"));
				}
			}

			// Available Cars
			String availableQuery = "SELECT COUNT(*) AS count FROM CR_Inventory WHERE availability = true";
			try (PreparedStatement stmt = conn.prepareStatement(availableQuery); ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					stats.put("available", rs.getInt("count"));
				}
			}
		}

		return stats;
	}

}
