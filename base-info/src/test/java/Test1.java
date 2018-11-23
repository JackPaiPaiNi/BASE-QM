import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Test1 {
	
	public static void main(String[] args) {
		Connection con = null;
	    PreparedStatement pre = null;
	    ResultSet result = null;

		try {
			
			String filePath = "E:\\workspace_fl\\skysoft\\base-web\\src\\main\\webapp\\WEB-INF\\views\\fl\\flzc\\zxmb";
			File file  = new File(filePath);		
			File[] listFiles = file.listFiles();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
	        String url = "jdbc:oracle:thin:@172.20.212.16:1521:FLDBTEST";
	        String user = "FL_DEV";
	        String password = "Aa123456";
	        con = DriverManager.getConnection(url, user, password);
	        String sql = "select mbbm from t_flzx_mb";
	        pre = con.prepareStatement(sql);
	        result = pre.executeQuery();
	        while(result.next()){
	        	String dbMbbm = result.getString("mbbm");
	        	int count = 0;
	        	for(File f : listFiles){
					String mbbm = f.getName().substring(3, 15);
					if(dbMbbm.equals(mbbm)){
						count ++;
					}
				}
	        	System.out.println(dbMbbm + ":" + count);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(result != null){
					result.close();
				} 
	            if(pre != null){
	            	pre.close();
	            }
	            if(con != null){
	            	con.close();
	            }
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

}
