package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;

import dbutils.DBUtils;
import dto.UserDTO;

public class UserDAO {

	public static UserDTO getUser(String username) {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		UserDTO result = null;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `hash` from `user` where `username`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, username);
				rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					String hash = rs.getString("hash");
					result = new UserDTO(username, hash);
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean updateUser(String username, String hash) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "update `user` set `hash`=? where `username`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, hash);
				preState.setString(2, username);
				effect = preState.executeUpdate();
				if (effect > 0)
					result = true;
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static boolean checkDuplicate(String username) {
		boolean isDuplicate = false;
		Connection c = null;
		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `username` from `user` where `username`=?";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setString(1, username);
				ResultSet rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					c.close();
					isDuplicate = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDuplicate;
	}

	public static int addUser(String username, String hash) {
		int result = 0;
		try {
			boolean isDuplicate = checkDuplicate(username);
			Connection c = DBUtils.ConnectDB();
			if (c != null && !isDuplicate) {
				String sql = "insert into `user`(`username`, `hash`) values(?, ?)";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setString(1, username);
				preState.setString(2, hash);
				result = preState.executeUpdate();
				c.close();
			}
			if (isDuplicate) {
				// Show error
				System.out.println("Username duplicated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean deleteUser(String username) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "delete from `user` where `username`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, username);
				effect = preState.executeUpdate();
				if (effect > 0)
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		/*
		 * UserDTO temp = getUser("admin"); System.out.println("username: " +
		 * temp.getUsername()); System.out.println("hash: " + temp.getHash());
		 * System.out.println("role: " + temp.getRole());
		 */
		//updateUser("admin", "admin", "admin");
//		addUser("user", "user", "user");
//		addUser("hihi", "user", "user");
//		addUser("haha", "user", "user");
//		addUser("lala", "user", "user");
		//deleteUser("user");
	}

//	public static boolean checkLogin(String userName, String password) {
//		boolean login = false;
//		Connection c = null;
//
//		try {
//			c = DBUtils.ConnectDB();
//
//			if (c != null) {
//				PreparedStatement preState;
//
//				String sql = "use boot\n";
//				sql += "select [username], [password] from [User] where username=? and password=?\n";
//				preState = c.prepareStatement(sql);
//				preState.setString(1, userName);
//				preState.setString(2, password);
//
//				ResultSet result = preState.executeQuery();
//				if (result != null && result.next()) {
//					login = true;
//				}
//				c.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return login;
//	}
//
//	public static ArrayList<UserDTO> getUsers(String userName) {
//		ArrayList<UserDTO> list = null;
//		Connection c;
//		PreparedStatement preState;
//
//		try {
//			c = DBUtils.ConnectDB();
//			String sql = "use boot\n";
//			sql += "select [username], [password], [fullname] from [User] where [username] like ?\n";
//			preState = c.prepareStatement(sql);
//			preState.setString(1, "%" + userName + "%");
//			ResultSet rs = preState.executeQuery();
//			if (rs == null)
//				return null;
//			list = new ArrayList<UserDTO>();
//			while (rs.next()) {
//				String username = rs.getString("username");
//				String password = rs.getString("password");
//				String fullName = rs.getString("fullname");
//				list.add(new UserDTO(username, password, fullName));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}
}
