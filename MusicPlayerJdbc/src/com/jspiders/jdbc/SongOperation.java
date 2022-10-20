package com.jspiders.jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class SongOperation {
	static Connection connection;
	static Statement statement;
	static PreparedStatement preaPreparedStatement;
	static ResultSet resultSet;
	static int resultInt;
	static FileReader fileReader;
	static Properties properties = new Properties();
	Scanner sc = new Scanner(System.in);
	static String query;
	static String filePath = "D:\\Aniket\\J2EE\\Project\\MusicPlayerJdbc\\resources\\db_info.properties";
	ArrayList<Song> list = new ArrayList<Song>();

	public static void openConnection() {

		try {
			fileReader = new FileReader(filePath);

			properties.load(fileReader);

			Class.forName(properties.getProperty("driverPath"));

			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void displayAllSongs() {
		System.out.println("***List of All Songs***");
		openConnection();
		query = "select * from musicplayer";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3)
						+ "  " + resultSet.getString(4) + "  " + resultSet.getString(5) + "  " + resultSet.getString(6)
						+ "  " + resultSet.getString(7) + "  ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	public void addSong() {
		openConnection();
		query = "insert into musicplayer values(?,?,?,?,?,?,?)";
		try {
			preaPreparedStatement = connection.prepareStatement(query);
			System.out.println("Enter the Song Id ");
			preaPreparedStatement.setInt(1, sc.nextInt());

			System.out.println("Enter the Song Name ");
			preaPreparedStatement.setString(2, sc.next());

			System.out.println("Enter the Singer Name ");
			preaPreparedStatement.setString(3, sc.next());

			System.out.println("Enter the Movie Name ");
			preaPreparedStatement.setString(4, sc.next());

			System.out.println("Enter the Composer Name ");
			preaPreparedStatement.setString(5, sc.next());

			System.out.println("Enter the Lyrist Name ");
			preaPreparedStatement.setString(6, sc.next());

			System.out.println("Enter the Duration of the Song");
			preaPreparedStatement.setDouble(7, sc.nextDouble());

			resultInt = preaPreparedStatement.executeUpdate();

			System.out.println(resultInt + " Song Added Succefully");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		displayAllSongs();
		closeConnection();
	}

	public void removeSong() {
		displayAllSongs();
		openConnection();
		System.out.println("Enter the song Id to Remove");
		int choose = sc.nextInt();
		query = "delete from musicplayer where id = " + choose;
		try {
			statement = connection.createStatement();
			resultInt = statement.executeUpdate(query);
			System.out.println("Song Has been Removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		displayAllSongs();
		closeConnection();
	}

	public void updateSong() {
		displayAllSongs();
		openConnection();
		System.out.println("Select Song Id to Update that Song ");
		int choose = sc.nextInt();
		query = "delete from musicplayer where id = " + choose;
		try {
			statement = connection.createStatement();
			resultInt = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
		openConnection();
		query = "insert into musicplayer values(?,?,?,?,?,?,?)";
		try {
			preaPreparedStatement = connection.prepareStatement(query);
			System.out.println("Enter the Song Id ");
			preaPreparedStatement.setInt(1, sc.nextInt());

			System.out.println("Enter the Song Name ");
			preaPreparedStatement.setString(2, sc.next());

			System.out.println("Enter the Singer Name ");
			preaPreparedStatement.setString(3, sc.next());

			System.out.println("Enter the Movie Name ");
			preaPreparedStatement.setString(4, sc.next());

			System.out.println("Enter the Composer Name ");
			preaPreparedStatement.setString(5, sc.next());

			System.out.println("Enter the Lyrist Name ");
			preaPreparedStatement.setString(6, sc.next());

			System.out.println("Enter the Duration of the Song");
			preaPreparedStatement.setDouble(7, sc.nextDouble());

			resultInt = preaPreparedStatement.executeUpdate();

			System.out.println(resultInt + " Song Updated Succefully");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
		displayAllSongs();
	}

	public void chooseToPlay() {
		displayAllSongs();
		openConnection();
		System.out.println("Choose Song Id to Play Song");
		int choose = sc.nextInt();
		query = "select Songname from musicplayer where id =" + choose;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " Song is Playing Now....");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection();
	}

	public void playAllSongs() {
		openConnection();
		System.out.println("Playing All Songs..");
		query = "select songname from musicplayer";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " Song is Playing Now....");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	public void playRandomSong() {
		openConnection();
		double num = Math.random();
		int random = (int) (num * 10 + 1);
		query = "select songname from musicplayer where id =" + random;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " Song is playing");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

}
