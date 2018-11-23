package com.skyworth.core.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skyworth.core.exception.ServiceException;
import com.skyworth.netdisk.api.LfsNetdiskUtils;

@Controller
@RequestMapping("/core/file")
public class FileUtils {
	@Value("${netdisk.updDstPath.fj}")
	private String updDstPathFj;
	
	@Autowired
	private LfsNetdiskUtils lfsNetdiskUtils;
	
	/**
	 * 文件下载
	 * @param response
	 * @param realName
	 * @param fileName
	 */
    @RequestMapping(value = "download")
	@ResponseBody
	public void download(HttpServletResponse response,HttpServletRequest request, String realName, String fileName){
		FileInputStream input = null;
		ServletOutputStream output = null;
		String codedFileName = "";
		// 获取浏览器版本
		String objUserAgent=request.getHeader("User-Agent").toLowerCase();
		String userAgent = objUserAgent == null ? "" : objUserAgent.toString();
		try {
			fileName += realName.substring(realName.lastIndexOf("."));
			// 火狐浏览器中文乱码处理方式 
			if (userAgent.indexOf("firefox") > -1) {
				codedFileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			} else {
				// 通常解决汉字乱码
				codedFileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + codedFileName);
			File file = new File(realName);
			input = new FileInputStream(file);
			output = response.getOutputStream();
			IOUtils.copy(input, output);
		} catch (FileNotFoundException e) {
			throw new ServiceException("服务器端文件不存在！");
		} catch (IOException e) {
			throw new ServiceException(e);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
    
    /**
	 * 文件上传
	 * @param file
	 */
    @RequestMapping(value = "upload")
	@ResponseBody
	public Object upload(MultipartFile file){
    	return lfsNetdiskUtils.uploadFile(file, updDstPathFj);
	}
	

}
