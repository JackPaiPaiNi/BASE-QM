import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Test2 {

	public static void main(String[] args) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int maxDay = calendar.getMaximum(Calendar.DAY_OF_MONTH);
		
		// 当前日期
		String dqrq = format1.format(today);
		result.put("dqrq", dqrq);
		// 当前年月
		String dqny = format2.format(today);
		result.put("dqny", dqny);
		// 当月月初
		String dyyc = format2.format(today) + "-01";
		result.put("dyyc", dyyc);
		// 当月月底
		String dyyd = format2.format(today) + "-" + String.valueOf(maxDay);
		result.put("dyyd", dyyd);
		// 当前财年
		String dqcn = "";
		if(month > 3){
			dqcn = String.valueOf(year);
		} else {
			dqcn = String.valueOf(year - 1);
		}
		result.put("dqcn", dqcn);
		// 当前财年开始
		String dqcnks = dqcn + "-04";
		result.put("dqcnks", dqcnks);
		// 当前财年结束
		String dqcnjs = String.valueOf(Integer.valueOf(dqcn) + 1) + "-03";
		result.put("dqcnjs", dqcnjs);
		
		System.out.println(result);
	}
}
