package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbutils.DBUtils;
import dto.InitrdDTO;

public class InitrdDAO {
	public static InitrdDTO getInitrd(int id) {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		InitrdDTO result = null;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `imageId` from `initrd` where `id`=?";
				preState = c.prepareStatement(sql);
				preState.setInt(1, id);
				rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					int imageId = rs.getInt("imageId");
					result = new InitrdDTO(id, imageId);
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static boolean checkDuplicate(int id) {
		boolean isDuplicate = false;
		Connection c = null;
		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `id` from `initrd` where `id`=?";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setInt(1, id);
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

	public static int addInitrd(int id, int imageId) {
		int result = 0;
		try {
			boolean isDuplicate = checkDuplicate(id);
			Connection c = DBUtils.ConnectDB();
			if (c != null && !isDuplicate) {
				String sql = "insert into `initrd`(`id`, `imageId`) values(?, ?)";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setInt(1, id);
				preState.setInt(2, imageId);
				result = preState.executeUpdate();
				c.close();
			}
			if (isDuplicate) {
				// Show error
				System.out.println("Initrd id duplicated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//int id: auto increment
	public static int addInitrd(int imageId) {
		int result = 0;
		try {
			Connection c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "insert into `initrd`(`imageId`) values(?)";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setInt(1, imageId);
				result = preState.executeUpdate();
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean deleteInitrd(int id) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "delete from `initrd` where `id`=?";
				preState = c.prepareStatement(sql);
				preState.setInt(1, id);
				effect = preState.executeUpdate();
				if (effect > 0)
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean updateInitrd(int id, int imageId) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "update `initrd` set `imageId`=? where `id`=?";
				preState = c.prepareStatement(sql);
				preState.setInt(1, imageId);
				preState.setInt(2, id);
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

	public static void main(String[] args) {
//		addInitrd(null, 0);
//		addInitrd(1, 1);
//		addInitrd(2, 4);
//		addInitrd(3, 1);
//		addInitrd(4, 0);
//		addInitrd(2);
//		addInitrd(10, 5);
//		addInitrd(7);

//		InitrdDTO temp = getInitrd(10);
//		System.out.println(temp.getId());
//		System.out.println(temp.getKernelId());
//		updateInitrd(10, 2);
//		deleteInitrd(4);
		
	}
}
