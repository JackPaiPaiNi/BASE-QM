import org.apache.shiro.crypto.hash.SimpleHash;


public class Test {
	
	public static void main(String[] args) {
		
		String newPassword = new SimpleHash(
                "md5",
                "1",
                null,
                Integer.valueOf(1)).toHex();
		System.out.println(newPassword);
	}

}
