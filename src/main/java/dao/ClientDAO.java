package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbutils.DBUtils;
import dto.ClientDTO;

public class ClientDAO {
	public static ClientDTO getClient(String mac) {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		ClientDTO result = null;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `name`, `ip`, `isOn`, `currentImageId` from `client` where `mac`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, mac);
				rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					String name = rs.getString("name");
					String ip = rs.getString("ip");
					boolean isOn = rs.getBoolean("isOn");
					int currentImageId = rs.getInt("currentImageId");
					//result = new UserDTO(username, hash, role);
					result = new ClientDTO(mac, name, ip, isOn, currentImageId);
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static boolean updateClient(String mac, String name, String ip, boolean isOn, int currentImageId) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "update `client` set `name`=?, `ip`=?, `isOn`=?, `currentImageId`=? where `mac`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, name);
				preState.setString(2, ip);
				preState.setBoolean(3, isOn);
				preState.setInt(4, currentImageId);
				preState.setString(5, mac);
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

	private static boolean checkDuplicate(String mac) {
		boolean isDuplicate = false;
		Connection c = null;
		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `mac` from `client` where `mac`=?";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setString(1, mac);
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

	public static int addClient(String mac, String name, String ip, boolean isOn, int currentImageId) {
		int result = 0;
		try {
			boolean isDuplicate = checkDuplicate(mac);
			Connection c = DBUtils.ConnectDB();
			if (c != null && !isDuplicate) {
				String sql = "insert into `client`(`mac`, `name`, `ip`, `isOn`, `currentImageId`) values(?, ?, ?, ?, ?)";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setString(1, mac);
				preState.setString(2, name);
				preState.setString(3, ip);
				preState.setBoolean(4, isOn);
				preState.setInt(5, currentImageId);
				result = preState.executeUpdate();
				c.close();
			}
			if (isDuplicate) {
				// Show error
				System.out.println("MAC address duplicated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean deleteClient(String mac) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "delete from `client` where `mac`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, mac);
				effect = preState.executeUpdate();
				if (effect > 0)
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main (String[] args) {
//		ClientDTO temp = getClient("FF-FF-FF-FF-FF-FF");
//		if (temp != null) {
//			System.out.println("mac: " + temp.getMac());
//			System.out.println("name: " + temp.getName());
//			System.out.println("ip: " + temp.getIp());
//			System.out.println("isOn: " + temp.isOn());
//			System.out.println("currentImageId: " + temp.getCurrentImageName());
//		} else {
//			System.out.println("NULL");
//		}
		//addClient("EE-EE-EE-EE-EE-EE", "Test2", "2.3.4.5", true, "Test Image");
//		System.out.println(updateClient("CC-CC-CC", "Test3", "9.3.4.5", false, "Test Image"));
//		System.out.println(deleteClient("EE-EE-EE-EE-EE-EE"));
		 
	}
}
