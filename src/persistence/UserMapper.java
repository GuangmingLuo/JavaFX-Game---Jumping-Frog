package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import storage.Database;

public enum UserMapper {

	//private static UserMapper uniqueInstance = null;
	UNIQUEINSTANCE;
	
	private UserMapper() {
		// private Constructor
	}
	

	/**
	 * Get a User object by its id
	 * 
	 * @param id The id of the Person to be found
	 * @return User object or null if it was not found
	 */
	public User getUserByName(String name) {
		String select = "SELECT name, password, maxGrade, minTime FROM User where name = ?";
		User user = null;
		try {
			PreparedStatement prepstat = Database.UNIQUEINSTANCE.getConnection().prepareStatement(select);
			prepstat.setString(1, name);
			user = queryUser(prepstat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/*
	 * Private helper method to query a Person object
	 */
	private User queryUser(PreparedStatement prepstat) {
		User user = null;
		ResultSet rs = null;
		try {
			rs = prepstat.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String password = rs.getString("Password");
				int maxGrade= rs.getInt("maxGrade");
				String minTime= rs.getString("minTime");
				
				user = new User(name, password, maxGrade, minTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				prepstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return user;
	}
	
	
	/*
	 * Set maxGrade for a certain user
	 */
	public void setUserMaxGrade(User user, int maxGrade){
		if(user != null){
			String sql = "UPDATE user SET maxGrade = ? WHERE name = ?";
			try (PreparedStatement pstmt = Database.UNIQUEINSTANCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				pstmt.setInt(1, maxGrade);
				pstmt.setString(2, user.getName());
				
				 // executeUpdate() should be called to change something in the database
				pstmt.executeUpdate();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Set minTime for a certain user
	 */
	public void setUserMinTime(User user, String minTime){
		if(user != null){
			String sql = "UPDATE user SET minTime = ? WHERE name = ?";
			try (PreparedStatement pstmt = Database.UNIQUEINSTANCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				pstmt.setString(1, minTime);
				pstmt.setString(2, user.getName());
				
				 // executeUpdate() should be called to change something in the database
				pstmt.executeUpdate();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Store a person in the database; new Java 7 try-with-resources used
	 * 
	 * @param person The Person object that needs to be stored
	 * @return true if create a User successfully
	 */
	public boolean createUser(User user) {

		if(user != null){
		String sql = "INSERT INTO user (name, password) VALUES (?,?)";
		try (PreparedStatement pstmt = Database.UNIQUEINSTANCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			
			 // executeUpdate() should be called to change something in the database
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs != null) {
					if (rs.next()) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	    return false;
		
	}
	
	/**
	 * Delete a person from the database
	 * 
	 * @param name of the user
	 * @return true of false
	 */
	public boolean deleteUser(String name) {
		String sql = "DELETE FROM user WHERE name = ?";
		try {
			PreparedStatement prepstat = Database.UNIQUEINSTANCE.getConnection().prepareStatement(sql);
			prepstat.setString(1, name);
			prepstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Get all user names
	 * 
	 * @return A Collection of all user names
	 */
	public List<String> getUserNames() {
		List<String> names = new LinkedList<String>();
		try {
			Statement stmt = Database.UNIQUEINSTANCE.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT name FROM user ORDER BY name");
			while (rset.next()) {
				names.add(rset.getString(1)); // scroll trough the data and fill
												// the collection
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	
	/**
	 * Query all users from the database by grade ranking
	 * @return A Collection of User objects
	 */
	public ObservableList<User> getAllUsersByGradeRanking()
    {
        ObservableList<User> listInGrade = this.getAllUsers();
        Comparator<User> CompareGarde = new Comparator<User>()
        {
            public int compare(User first,User second)
            {
                return second.getMaxGrade()-first.getMaxGrade();
            }
        };
        Collections.sort(listInGrade,CompareGarde);
        return listInGrade;
    }
	
	/**
	 * Query all users from the database 
	 * @return A Collection of User objects
	 */
	public ObservableList<User> getAllUsers() {
		ObservableList<User> list = FXCollections.observableArrayList();
		String sql = "SELECT * FROM user";
		try {
			PreparedStatement prepstat = Database.UNIQUEINSTANCE.getConnection().prepareStatement(sql);
			return queryUsers(prepstat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		list.add(null);
		return list;
	}
	
	/*
	 * Private helper method to query a list of User objects
	 */
	private ObservableList<User> queryUsers(PreparedStatement prepstat) {
		ObservableList<User> users = FXCollections.observableArrayList();
		ResultSet rs = null;
		try {
			rs = prepstat.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String password = rs.getString("Password");
				int maxGrade= rs.getInt("maxGrade");
				String minTime= rs.getString("minTime");

				users.add(new User(name, password, maxGrade, minTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				prepstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return users;
	}
	
	
}
