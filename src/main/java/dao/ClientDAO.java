package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import dbutils.DBUtils;
import dto.ClientDTO;
import dto.ImageDTO;

public class ClientDAO {

	public static int getNumOfClientWithImageId(int imageId) {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		int result = 0;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `imageId` from `client` where `imageId`=?";
				preState = c.prepareStatement(sql);
				preState.setInt(1, imageId);
				rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					result += 1;
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean removeReference(int imageId) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		int expectedEffect = getNumOfClientWithImageId(imageId);

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "update `client` set `imageId`=? where `imageId`=?";
				preState = c.prepareStatement(sql);
				preState.setNull(1, Types.INTEGER);
				preState.setInt(2, imageId);
				effect = preState.executeUpdate();
				if (effect == expectedEffect)
					result = true;
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean updateClient(int id, String name, boolean isOn, String ip, int imageId) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;
		String sql = null;
		
		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				sql = "update `client` set `name`=?, `isOn`=?, `ip`=?, `imageId`=? where `id`=?";
				preState = c.prepareStatement(sql);
				preState.setString(1, name);
				preState.setBoolean(2, isOn);
				preState.setString(3, ip);
				if (imageId != -1)
					preState.setInt(4, imageId);
				else
					preState.setNull(4, Types.INTEGER);
				preState.setInt(5, id);
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

	public static ArrayList<ClientDTO> getAll() {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		ArrayList<ClientDTO> clientList = new ArrayList<ClientDTO>();
		ClientDTO client = null;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "SELECT `id`, `name`, `mac`, `isOn`, `ip`, `imageId`\n" + "FROM `client`";
				preState = c.prepareStatement(sql);
				rs = preState.executeQuery();
				while (rs != null && rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String mac = rs.getString("mac");
					boolean isOn = rs.getBoolean("isOn");
					String ip = rs.getString("ip");
					int imageId = rs.getInt("imageId");
					ImageDTO image;
					if (rs.wasNull())
						image = null;
					else
						image = ImageDAO.getImage(imageId);
					client = new ClientDTO(id, name, mac, isOn, ip, image);
					clientList.add(client);
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientList;
	}

	public static ClientDTO getClient(int id) {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		ClientDTO result = null;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `mac`, `imageId` from `client` where `id`=?";
				preState = c.prepareStatement(sql);
				preState.setInt(1, id);
				rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					String mac = rs.getString("mac");
					int imageId = rs.getInt("imageId");
					ImageDTO image = ImageDAO.getImage(imageId);
					result = new ClientDTO(id, mac, image);
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
				String sql = "select `id` from `client` where `id`=?";
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

	private static boolean checkMacDuplicate(String mac) {
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

	private static boolean checkNameDuplicate(String name) {
		boolean isDuplicate = false;
		Connection c = null;
		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "select `name` from `client` where `name`=?";
				PreparedStatement preState = c.prepareStatement(sql);
				preState.setString(1, name);
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

	public static int getHighestId() {
		Connection c = null;
		PreparedStatement preState = null;
		ResultSet rs = null;
		int result = -1;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "SELECT id \n" + "FROM client \n" + "order by id desc\n" + "limit 1";
				preState = c.prepareStatement(sql);
				rs = preState.executeQuery();
				if (rs != null && rs.next()) {
					result = rs.getInt("id");
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int addClient(int id, String mac, int imageId) {
		int result = 0;
		try {
			boolean isDuplicate = checkDuplicate(id);
			Connection c = DBUtils.ConnectDB();
			if (c != null && !isDuplicate) {
				isDuplicate = checkMacDuplicate(mac);
				if (!isDuplicate) {
					String sql = "insert into `client`(`id`, `mac`, `imageId`) values(?, ?, ?)";
					PreparedStatement preState = c.prepareStatement(sql);
					preState.setInt(1, id);
					preState.setString(2, mac);
					preState.setInt(3, imageId);
					result = preState.executeUpdate();
					c.close();
				}
				if (isDuplicate)
					System.out.println("MAC address duplicated");
			}
			if (isDuplicate) {
				// Show error
				System.out.println("Client id duplicated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String addClient(String name, String mac) {
		int result = 0;
		int id = getHighestId() + 1;
		try {
			boolean isDuplicate = checkDuplicate(id);
			Connection c = DBUtils.ConnectDB();
			if (c != null && !isDuplicate) {
				isDuplicate = checkMacDuplicate(mac);
				if (!isDuplicate) {
					isDuplicate = checkNameDuplicate(name);
					if (!isDuplicate) {
						String sql = "insert into `client`(`id`, `name`, `mac`, `isOn`) values(?, ?, ?, ?)";
						PreparedStatement preState = c.prepareStatement(sql);
						preState.setInt(1, id);
						preState.setString(2, name);
						preState.setString(3, mac);
						preState.setBoolean(4, false);
						result = preState.executeUpdate();
						c.close();
					} else {
						return "Client name duplicated";
					}
				} else {
					return "MAC address duplicated";
				}
			} else if (isDuplicate) {
				// Show error
				return "Client id duplicated";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result == 1 ? "true" : "false";
	}

	public static boolean deleteClient(int id) {
		Connection c = null;
		PreparedStatement preState = null;
		int effect = 0;
		boolean result = false;

		try {
			c = DBUtils.ConnectDB();
			if (c != null) {
				String sql = "delete from `client` where `id`=?";
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

	public static void main(String[] args) {
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
		// addClient("EE-EE-EE-EE-EE-EE", "Test2", "2.3.4.5", true, "Test Image");
//		System.out.println(updateClient("CC-CC-CC", "Test3", "9.3.4.5", false, "Test Image"));
//		System.out.println(deleteClient("EE-EE-EE-EE-EE-EE"));
//		ArrayList<ClientDTO> clientList = getAll();
//		for (ClientDTO x : clientList) {
//			System.out.println(String.format("%d - %s - %d", x.getId(), x.getMac(), x.getImage()));
//		}
		// System.out.println(Integer.parseInt(null));
	}
}
