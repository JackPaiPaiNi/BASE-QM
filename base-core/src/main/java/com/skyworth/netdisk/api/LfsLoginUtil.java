package com.skyworth.netdisk.api;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LfsLoginUtil {

	/**
	 * @param username
	 *            lfs用户名
	 * @param password
	 *            lfs密码
	 * @return LfsSession 对象
	 */
	public static LfsSession login(String loginUrl, String username, String password) {
		LfsSession lfsSession = new LfsSession();
		try {
			URL url = new URL(loginUrl);

			// 构造请求
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			con.setRequestProperty("Accept-Language", "zh-cn");
			con.setRequestMethod("POST");
			// 浦求参数
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("username", username);
			parameters.put("password", password);

			// 这里是要传的参数，postData就是参数
			OutputStream os = con.getOutputStream();
			OutputStreamWriter dos = new OutputStreamWriter(os, "UTF-8");
			if (null != parameters) {
				StringBuffer param = new StringBuffer();
				for (String key : parameters.keySet()) {
					param.append("&");
					param.append(key).append("=").append(parameters.get(key));
				}
				dos.write(param.toString());
				dos.flush();
				dos.close();
			}
			os.flush();
			os.close();

			System.out.println("响应状态：" + con.getResponseCode());
			if (con.getResponseCode() == 200) {
				lfsSession.setStatus("SUCCESS");
				System.out.println("responseMessage : " + con.getResponseMessage());
				// 输入流
				java.io.BufferedReader l_reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(con.getInputStream(), "UTF-8"));
				// 得到返回的信息
				String sCurrentLine = "";
				StringBuffer sTotalString = new StringBuffer();
				while ((sCurrentLine = l_reader.readLine()) != null) {
					sTotalString.append(sCurrentLine);
					sTotalString.append("\n");
				}
				System.out.println("InputStream : " + sTotalString);
				Gson gson = new Gson();
				JsonObject returnObject = (JsonObject) gson.fromJson(sTotalString.toString(), JsonObject.class);
				if (null != returnObject) {
					String status = returnObject.get("status").getAsString();
					int userId = returnObject.get("userId").getAsInt();// 用户id
					if ("SUCCESS".equalsIgnoreCase(status)) {
						lfsSession.setUserId(userId);
					}
				}

			} else {
				lfsSession.setStatus("ERROR");
				System.out.println("responseMessage : " + con.getResponseMessage());
				// 输入流
				java.io.BufferedReader l_reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(con.getErrorStream(), "UTF-8"));
				// 得到返回的信息
				String sCurrentLine = "";
				StringBuffer sTotalString = new StringBuffer();
				while ((sCurrentLine = l_reader.readLine()) != null) {
					sTotalString.append(sCurrentLine);
					sTotalString.append("\n");
				}
			}

			// 第一次运行的时候，记录下来session的值
			String session_value = con.getHeaderField("Set-Cookie");
			String[] sessionId = session_value.split(";");

			lfsSession.setCookieValue(sessionId[0]);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return lfsSession;
	}
}
