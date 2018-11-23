import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Test {
	
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
	        String sql = "select count(*) mbsl from t_flzx_mb where mbbm=?";
	        pre = con.prepareStatement(sql);
			
			for(File f : listFiles){
				String mbbm = f.getName().substring(3, 15);
				//System.out.println(mbbm);
				
		        pre.setString(1, mbbm);
		        result = pre.executeQuery();
		        
		        while(result.next()){
		        	System.out.println(mbbm + "   " + result.getInt("mbsl"));
		        }
				
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
