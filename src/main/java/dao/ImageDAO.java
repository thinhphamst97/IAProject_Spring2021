package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbutils.DBUtils;
import dto.ImageDTO;
import dto.KernelDTO;

public class ImageDAO {
	public static int getHighestId() {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        int result = -1;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "SELECT id \n" + 
                		"FROM image \n" + 
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
	
	public static boolean updateStatusImage(int id, boolean isActive) {
        Connection c = null;
        PreparedStatement preState = null;
        int effect = 0;
        boolean result = false;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "update `image` set `isActive`=? where `id`=?";
                preState = c.prepareStatement(sql);
                preState.setBoolean(1, isActive);
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

    public static ImageDTO getImageDetails(int id) {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        ImageDTO result = null;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "select `name`, `description`, `type` from image where id = ?";
                preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                rs = preState.executeQuery();
                if (rs != null && rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    result = new ImageDTO(id, name, type, -1, description, false, null, null);
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<ImageDTO> getImageList() {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        ImageDTO image = null;
        ArrayList<ImageDTO> result = new ArrayList<ImageDTO>();

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "select i.id iId, i.name iName, i.size, k.id kId, k.name kName, i.dateCreated, i.isActive\n"
                        + "from `image` as `i`, `kernel` as `k`\n"
                        + "where i.kernelID = k.id\n"
                        + "order by iId asc";
                preState = c.prepareStatement(sql);
                rs = preState.executeQuery();
                while (rs != null && rs.next()) {
                    int iId = rs.getInt("iId");
                    String iName = rs.getString("iName");
                    float size = rs.getFloat("size");
                    boolean isActive = rs.getBoolean("isActive");
                    Date dateCreated = rs.getDate("dateCreated");
                    int kId = rs.getInt("kId");
                    String kName = rs.getString("kName");
                    KernelDTO kernel = new KernelDTO(kId, kName);
                    image = new ImageDTO(iId, iName, null, size, null, isActive, dateCreated, kernel);
                    image.setKernel(kernel);
                    result.add(image);
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static ArrayList<ImageDTO> getImageActiveList() {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        ImageDTO image = null;
        ArrayList<ImageDTO> result = new ArrayList<ImageDTO>();

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "select i.id iId, i.name iName, i.size, k.id kId, k.name kName, i.dateCreated, i.isActive\n"
                        + "from `image` as `i`, `kernel` as `k`\n"
                        + "where i.kernelID = k.id and i.isActive = true\n"
                        + "order by iId asc";
                preState = c.prepareStatement(sql);
                rs = preState.executeQuery();
                while (rs != null && rs.next()) {
                    int iId = rs.getInt("iId");
                    String iName = rs.getString("iName");
                    float size = rs.getFloat("size");
                    boolean isActive = rs.getBoolean("isActive");
                    Date dateCreated = rs.getDate("dateCreated");
                    int kId = rs.getInt("kId");
                    String kName = rs.getString("kName");
                    KernelDTO kernel = new KernelDTO(kId, kName);
                    image = new ImageDTO(iId, iName, null, size, null, isActive, dateCreated, kernel);
                    image.setKernel(kernel);
                    result.add(image);
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
//    public static ArrayList<String> getListImageName() {
//        Connection c = null;
//        PreparedStatement preState = null;
//        ResultSet rs = null;
//        
//        ArrayList<String> result = new ArrayList<String>();
//        try {
//            c = DBUtils.ConnectDB();
//            if (c != null) {
//                String sql = "select `name` from image order by id";
//                preState = c.prepareStatement(sql);
//                rs = preState.executeQuery();
//                while (rs != null && rs.next()) {
//                    String iName = rs.getString("name");
//                    result.add(iName);
//                }
//                c.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
    
    public static ArrayList<ImageDTO> getAll() {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        ArrayList<ImageDTO> imageList = new ArrayList<ImageDTO>();
        ImageDTO image = null;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "select i.id iId, i.name `iName`, i.type, i.size, k.id `kId`, k.name `kName`, `description`, i.dateCreated, i.isActive\r\n"
                        + "from `image` as `i`, `kernel` as `k`\r\n"
                        + "where i.kernelID = k.id";
                preState = c.prepareStatement(sql);
                rs = preState.executeQuery();
                while (rs != null && rs.next()) {
                    int id = rs.getInt("iId");
                    String name = rs.getString("iName");
                    String type = rs.getString("type");
                    float size = rs.getFloat("size");
                    String description = rs.getString("description");
                    boolean isActive = rs.getBoolean("isActive");
                    Date dateCreated = rs.getDate("dateCreated");
                    int kId = rs.getInt("kId");
                    String kName = rs.getString("kName");
                    KernelDTO kernel = new KernelDTO(kId, kName);
                    image = new ImageDTO(id, name, type, size, description, isActive, dateCreated, kernel);
                    imageList.add(image);
                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageList;
    }

    public static ImageDTO getImage(int id) {
        Connection c = null;
        PreparedStatement preState = null;
        ResultSet rs = null;
        ImageDTO result = null;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "select i.name `iName`, i.type, i.size, k.id `kId`, k.name `kName`, `description`, i.dateCreated, i.isActive\r\n"
                        + "from `image` as `i`, `kernel` as `k`\r\n"
                        + "where i.kernelID = k.id and i.id = ?";
                preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                rs = preState.executeQuery();
                if (rs != null && rs.next()) {
                    String name = rs.getString("iName");
                    String type = rs.getString("type");
                    float size = rs.getFloat("size");
                    String description = rs.getString("description");
                    boolean isActive = rs.getBoolean("isActive");
                    Date dateCreated = rs.getDate("dateCreated");
                    int kId = rs.getInt("kId");
                    String kName = rs.getString("kName");
                    KernelDTO kernel = new KernelDTO(kId, kName);
                    result = new ImageDTO(id, name, type, size, description, isActive, dateCreated, kernel);
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
                String sql = "select `id` from `image` where `id`=?";
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

    public static int addImage(int id, String name, String description, boolean isActive, Date dateCreated, int kernelId) {
        int result = 0;
        try {
            boolean isDuplicate = checkDuplicate(id);
            Connection c = DBUtils.ConnectDB();
            if (c != null && !isDuplicate) {
                String sql = "insert into `image`(`id`, `name`, `description`, `isActive`, `dateCreated`, `kernelId`) values(?, ?, ?, ?, ?, ?)";
                PreparedStatement preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                preState.setString(2, name);
                preState.setString(3, description);
                preState.setBoolean(4, isActive);
                preState.setDate(5, dateCreated);
                preState.setInt(6, kernelId);
                result = preState.executeUpdate();
                c.close();
            }
            if (isDuplicate) {
                // Show error
                System.out.println("Image id duplicated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int addImage(String name, String type, float size, String description, boolean isActive, Date dateCreated, int kernelId) {
        int result = 0;
        int id = getHighestId() + 1;
        try {
            boolean isDuplicate = checkDuplicate(id);
            Connection c = DBUtils.ConnectDB();
            if (c != null && !isDuplicate) {
                String sql = "insert into `image`(`id`, `name`, `type`, `size`, `description`, `isActive`, `dateCreated`, `kernelId`) values(?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preState = c.prepareStatement(sql);
                preState.setInt(1, id);
                preState.setString(2, name);
                preState.setString(3, type);
                preState.setFloat(4, size);
                preState.setString(5, description);
                preState.setBoolean(6, isActive);
                preState.setDate(7, dateCreated);
                preState.setInt(8, kernelId);
                result = preState.executeUpdate();
                c.close();
            }
            if (isDuplicate) {
                // Show error
                System.out.println("Image id duplicated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deleteImage(int id) {
        Connection c = null;
        PreparedStatement preState = null;
        int effect = 0;
        boolean result = false;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "delete from `image` where `id`=?";
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

    public static boolean updateImage(int id, String name, String description, boolean isActive, Date dateCreated, int kernelId) {
        Connection c = null;
        PreparedStatement preState = null;
        int effect = 0;
        boolean result = false;

        try {
            c = DBUtils.ConnectDB();
            if (c != null) {
                String sql = "update `image` set `name`=?, `description`=?, `isActive`=?, `dateCreated`=?, `kernelId`=? where `id`=?";
                preState = c.prepareStatement(sql);
                preState.setString(1, name);
                preState.setString(2, description);
                preState.setBoolean(3, isActive);
                preState.setDate(4, dateCreated);
                preState.setInt(5, kernelId);
                preState.setInt(6, id);
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
//		addImage("Test Image 2", "Test description 2", true, 0);
//		addImage("Test Image 3", "Test description 3", false, 0);
//		addImage("Test Image 4", "Test description 4", true, 0);
//		addImage("Test Image 5", "Test description 5", false, 0);
//		deleteImage("Test description 2");
//		deleteImage("Test description 3");
//		deleteImage("Test description 4");
//		deleteImage("Test description 5");
//		updateImage("Test Image", "Update description 2", true, 0);
//		ImageDTO temp = getImage("image1");
//		System.out.println(temp.getName());
//		System.out.println(temp.getDescription());
//		System.out.println(temp.isActive());
//		System.out.println(temp.getDateCreated());
//		System.out.println(temp.getKernelId());
//		Date date = Date.valueOf(LocalDate.now());
//		updateImage(temp.getName(), temp.getDescription(), temp.isActive(), date, temp.getKernelId());
    }

}
