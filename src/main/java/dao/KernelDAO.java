package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbutils.DBUtils;
import dto.KernelDTO;

public class KernelDAO {
	public static int getHighestId() {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        int result = -1;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "SELECT id \n" + 
                		"FROM kernel \n" + 
                		"order by id desc\n" + 
                		"limit 1";
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

    public static KernelDTO getKernel(int id) {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        KernelDTO result = null;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "select `name` from `kernel` where `id`=?";
                preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                rs = preState.executeQuery();
                if (rs != null && rs.next()) {
                    String name = rs.getString("name");
                    result = new KernelDTO(id, name);
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
                String sql = "select `id` from `kernel` where `id`=?";
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
    
    public static int addKernel(String name) {
        int result = 0;
        int id = getHighestId() + 1;
        try {
            boolean isDuplicate = checkDuplicate(id);
            Connection c = DBUtils.ConnectDB();
            if (c != null && !isDuplicate) {
                String sql = "insert into `kernel`(`id`, `name`) values(?, ?)";
                PreparedStatement preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                preState.setString(2, name);
                result = preState.executeUpdate();
                c.close();
            }
            if (isDuplicate) {
                // Show error
                System.out.println("Kernel id duplicated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int addKernel(int id, String name) {
        int result = 0;
        try {
            boolean isDuplicate = checkDuplicate(id);
            Connection c = DBUtils.ConnectDB();
            if (c != null && !isDuplicate) {
                String sql = "insert into `kernel`(`id`, `name`) values(?, ?)";
                PreparedStatement preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                preState.setString(2, name);
                result = preState.executeUpdate();
                c.close();
            }
            if (isDuplicate) {
                // Show error
                System.out.println("Kernel id duplicated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deleteKernel(int id) {
        Connection c = null;
        PreparedStatement preState = null;
        int effect = 0;
        boolean result = false;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "delete from `kernel` where `id`=?";
                preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                effect = preState.executeUpdate();
                if (effect > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean updateKernel(int id, String name) {
        Connection c = null;
        PreparedStatement preState = null;
        int effect = 0;
        boolean result = false;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "update `kernel` set `name`=? where `id`=?";
                preState = c.prepareStatement(sql);
                preState.setString(1, name);
                preState.setInt(2, id);
                effect = preState.executeUpdate();
                if (effect > 0) {
                    result = true;
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
//		KernelDTO temp = getKernel(4);
//		System.out.println(temp.getId());
//		System.out.println(temp.getName());
//		System.out.println(temp.getVersion());
        addKernel(2, "Test 3");
    }
}
