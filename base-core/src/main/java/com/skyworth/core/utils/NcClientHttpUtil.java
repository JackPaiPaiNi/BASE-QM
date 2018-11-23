package com.skyworth.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class NcClientHttpUtil {

	public static Map<String, Object> trans(String url, String xmlStr) {
		Map<String, Object> result = new HashMap<String, Object>();

		String xmlReturn = "";
		boolean flag = true;
		int count = 0;
		// 连接中断重试5次
		do {
			try {
				xmlReturn = client(url, xmlStr);
				count = 0;
				flag = false;
			} catch (Exception e) {
				count++;
				flag = true;
				xmlReturn = e.getMessage();
			}
		} while (count <= 5 && flag == true);

		if (flag) {
			result.put("code", -400);
			result.put("msg", "连接中断");
			return result;
		}

		try {
			String code = getXmlCode(xmlReturn);

			if (StringUtils.isBlank(code)) {
				result.put("code", -505);
				result.put("msg", "对方无返回");
			}else{
				result.put("code", code);
				result.put("msg", xmlReturn);
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			result.put("code", -404);
			result.put("msg", e.getMessage());
			return result;
		}
		return result;
	}

	/**
	 * Doc转换成list
	 * 
	 * @param doc
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static String getXmlCode(String xml)
			throws SAXException, IOException, ParserConfigurationException {
		String code = "";
		Document doc = string2Doc(xml);
		NodeList nodelist = doc.getElementsByTagName("sendresult");
		Element sendresult = null;
		sendresult = (Element) nodelist.item(0);
		// 获取resultcode结点
		NodeList resultcodenodes = sendresult.getElementsByTagName("resultcode");

		if (resultcodenodes.getLength() == 1) {
			code = resultcodenodes.item(0).getTextContent();
		}
		return code;

	}

	/**
	 * String 转换为 Document 对象
	 * 
	 * @param xml
	 *            字符串
	 * @return Document 对象
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static Document string2Doc(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		InputSource source = null;
		StringReader reader = null;
		try {
			builder = factory.newDocumentBuilder();
			reader = new StringReader(xml);
			source = new InputSource(reader);// 使用字符流创建新的输入源
			doc = builder.parse(source);
			return doc;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static String client(String url, String xmlStr) throws IOException {
		// String backXmlInfo = "";
		HttpURLConnection connection = null;
		try {
			// 把字符流转换成字节流 utf-8
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			OutputStreamWriter fileout = new OutputStreamWriter(byteArray, "UTF-8");
			fileout.write(xmlStr);
			fileout.close();
			// Java代码调用方式 获取Servlet连接并设置请求的方法
			URL realURL = new URL(url);
			connection = (HttpURLConnection) realURL.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestMethod("POST");
			byte[] xml = byteArray.toByteArray();

			// 将Document对象写入连接的输出流中
			BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(xml));

			BufferedOutputStream out = null;
			OutputStream os = connection.getOutputStream();
			out = new BufferedOutputStream(os);
			int length;
			byte[] buffer = new byte[1024];
			while ((length = input.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			input.close();
			out.close();
			// System.out.println("数据处理完毕++++++ ");

			InputStream is = connection.getInputStream();
			length = -1;
			buffer = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((length = is.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, length));
			}

			return sb.toString();

		} finally {
			connection.disconnect();
		}
	}
}
