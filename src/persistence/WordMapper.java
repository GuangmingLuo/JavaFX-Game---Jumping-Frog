package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import storage.Database;

public enum WordMapper {
	
	//private static WordMapper uniqueInstance = null;
			UNIQUEINSTANCE;
	private WordMapper() {
		// private Constructor
	}
	
	public String getWordRandomly(int my_level){
		String name = "level" + my_level;
		String select = "SELECT word FROM "+ name+" where id = ?";
		ResultSet rs = null;
		PreparedStatement prepstat = null;
		String word = null;		
		name.toString();
		int id = randInt(1,50);
		try {
			prepstat = Database.UNIQUEINSTANCE.getConnection().prepareStatement(select);
			//prepstat.setString(1, name);
			prepstat.setInt(1, id);
			rs = prepstat.executeQuery();
			if(rs.next()){
				word = rs.getString("word");
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		return word;
	}
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}
