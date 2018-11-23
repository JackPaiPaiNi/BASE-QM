package com.skyworth.netdisk.api;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 我的文件（网盘）本地上传工具类
 * 
 * @author Administrator
 * 
 */
@Component
public class LfsNetdiskUtils {

	private static final String HTTP_BOUNDARY = "---------9dx5a2d578c2";
	private static final String MULTIPART_FORM_DATA = "multipart/form-data";
	private static final String LINE_ENTER = "\r\n"; // 换行 回车
	// private static final int RESPONSE_OK = 200;

	@Value("${netdisk.lfsHost}")
	private String lfsHost;
	@Value("${netdisk.lfsDownloadHost}")
	private String lfsDownloadHost;
	@Value("${netdisk.lfsUser}")
	private String lfsUser;
	@Value("${netdisk.lfsPwd}")
	private String lfsPwd;
	@Value("${netdisk.loginUrl}")
	private String loginUrl;
	@Value("${netdisk.actionUrl}")
	private String actionUrl;
	@Value("${netdisk.appName}")
	private String appName;
	@Value("${netdisk.filenameFormat}")
	private String filenameFormat;
	@Value("${netdisk.dateFormat}")
	private String dateFormat;
	@Value("${netdisk.dirFormat}")
	private String dirFormat;
	@Value("${netdisk.filePrefix}")
	private String filePrefix;
	@Value("${netdisk.updDstPath}")
	private String updDstPath;
	@Value("${netdisk.create}")
	private String create;
	@Value("${netdisk.dest}")
	private String dest;
	@Value("${netdisk.apikey}")
	private String apikey;
	
	public LfsResult uploadFile(File inputFile, String subdirectory) {
		LfsResult lfsResult = new LfsResult();
		if (null != inputFile && inputFile.exists()) {

			// 建立文件的输入流
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(inputFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			lfsResult = upload(inputStream, subdirectory, inputFile.getName());
		} else {
			lfsResult.setStatus("ERROR");
			lfsResult.setMsg("文件为空！");
		}
		return lfsResult;
	}

	public LfsResult uploadFile(MultipartFile inputFile, String subdirectory) {
		LfsResult lfsResult = new LfsResult();
		if (!inputFile.isEmpty()) {

			// 建立文件的输入流
			InputStream inputStream = null;
			try {
				inputStream = inputFile.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
			lfsResult = upload(inputStream, subdirectory, inputFile.getOriginalFilename());
		} else {
			lfsResult.setStatus("ERROR");
			lfsResult.setMsg("文件为空！");
		}
		return lfsResult;
	}
	
	private LfsResult upload(InputStream inputStream, String subdirectory, String fileName){
		// 操作之前先登录
		LfsSession lfsSession = LfsLoginUtil.login(lfsHost + loginUrl, lfsUser, lfsPwd);
		
		LfsResult lfsResult = new LfsResult();
		try {
			// 打开远程
			URL url = new URL(lfsHost + actionUrl);// 个人文件普通上传
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			con.setRequestProperty("Accept-Language", "zh-cn");
			con.setRequestProperty("Cookie", lfsSession.getCookieValue());
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive"); // 维持长链接
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + HTTP_BOUNDARY);

			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", lfsSession.getUserId() + "");
			params.put("appName", appName);
			params.put("filenameFormat", filenameFormat);
			params.put("dateFormat", dateFormat);
			params.put("dirFormat", dirFormat);
			params.put("filePrefix", filePrefix);
			params.put("updDstPath", updDstPath + subdirectory);
			params.put("create", create);
			params.put("dest", dest);// dest=1文件中转站上传，默认dest=0 普通上传
			params.put("apikey", apikey);

			StringBuilder formItemData = new StringBuilder();
			// 构建表单字段内容
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formItemData.append("--");
				formItemData.append(HTTP_BOUNDARY);
				formItemData.append(LINE_ENTER);
				formItemData.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
				formItemData.append(entry.getValue());
				formItemData.append(LINE_ENTER);
			}
			DataOutputStream outStream = new DataOutputStream(con.getOutputStream());
			// 发送表单字段内容到服务器
			outStream.write(formItemData.toString().getBytes());

			// 发送上传文件数据
			StringBuilder fileSplit = new StringBuilder();
			fileSplit.append("--");
			fileSplit.append(HTTP_BOUNDARY);
			fileSplit.append(LINE_ENTER);
			fileSplit.append("Content-Disposition: form-data;name=\"filedata\";filename=\"" + fileName
					+ "\"\r\n");
			fileSplit.append("Content-Type:" + "application/octet-stream" + LINE_ENTER + LINE_ENTER);
			outStream.write(fileSplit.toString().getBytes());

			if (inputStream != null) {
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, length);
				}
				inputStream.close();
			}
			outStream.write(LINE_ENTER.getBytes());

			// 数据结束标志
			byte[] endData = ("--" + HTTP_BOUNDARY + "--" + LINE_ENTER).getBytes();
			outStream.write(endData);
			outStream.flush();
			outStream.close();

			System.out.println("响应状态：" + con.getResponseCode());

			String returnJson = null;
			if (con.getResponseCode() == 200) {
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
				// 返回信息（字符串）
				returnJson = sTotalString.toString();
				// 下载和预览的url加上主机名
				ObjectMapper mapper = new ObjectMapper();
				// json字符串反序列化为bean时,忽略json中有而bean里没有的属性
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				lfsResult = mapper.readValue(returnJson, LfsResult.class);
				lfsResult.setDownloadurl(lfsDownloadHost + lfsResult.getDownloadurl());
				lfsResult.setPreviewurl(lfsDownloadHost + lfsResult.getPreviewurl());
				return lfsResult;

			}
		} catch (Exception e) {
			e.printStackTrace();
			lfsResult.setStatus("ERROR");
			lfsResult.setMsg(e.getMessage());
		}
		return lfsResult;
	}
}
