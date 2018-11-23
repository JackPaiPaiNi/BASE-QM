package com.skyworth.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyworth.core.base.gridExport.ExportHandler;
import com.skyworth.core.base.gridExport.ExportSetting;
import com.skyworth.core.base.gridExport.FileServiceMessage;
import com.skyworth.core.exception.ServiceException;

/**
 * 导出Excel工具类
 * 
 * @author 魏诚
 */
@Component
public class ExcelExportUtils {

	private final Logger logger = Logger.getLogger(getClass());

	@Value("${tmp.file.folder}")
	private String exporterDirectory;

	@Value("${export.size.excel}")
	private int exportSize;

	@Value("${export.filetype.excel}")
	private String fileType;

	public String exportSync(Collection<?> dataList, Map<String, Object> params) {
		String exportParamList = (String)params.get("exportParamList");
		ExportSetting setting = new ExportSetting();
		try {
			ObjectMapper mapper = new ObjectMapper();
			setting = mapper.readValue(exportParamList, ExportSetting.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return this.exportToFile(dataList, setting);
	}

	public String exportMoreSync(List<List<?>> list, Map<String, Object> params) {
		String exportParamList = (String)params.get("exportParamList");
		
		List<ExportSetting> settingList = new ArrayList<ExportSetting>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,ExportSetting.class);
			settingList = mapper.readValue(exportParamList, javaType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return this.exportMoreToFile(list, settingList);
	}

	/**
	 * 拼接上传路径字符串
	 * 
	 * @param dir
	 *            BASE目录
	 * @param ownerModule
	 *            第一层目录
	 * @param timestamp
	 *            第二层目录
	 * @return
	 * @throws FileServiceException
	 */
	private String newFilePath(String dir, String date) {

		StringBuffer filePath = new StringBuffer();
		filePath.append(dir);
		filePath.append(File.separator);
		filePath.append(date);
		filePath.append(File.separator);

		try {
			File d = new File(filePath.toString());
			if (!d.exists())
				d.mkdirs();
		} catch (SecurityException e) {
			throw new ServiceException(FileServiceMessage.FILE_CANNOT_WRITE.toString(), e);
		}

		return filePath.toString();
	}
	
	private String getRealName(){
		Integer rdm = Math.abs(RandomUtils.getRandom().nextInt(1000));
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String date = new SimpleDateFormat("yyyyMMdd").format(ts);
		String fs = new SimpleDateFormat("yyyyMMdd_HHmm").format(ts);

		String fileName = fs + "_" + rdm + "." + fileType;
		String filePath = newFilePath(exporterDirectory, date);

		String realName = filePath + fileName;
		
		return realName;
		
	}

	private String exportToFile(Collection<?> list, ExportSetting setting) {
		String realName = getRealName();

		File f = null;
		try {
			f = new File(realName);
			f.createNewFile();

			OutputStream os = new FileOutputStream(f);

			try {
				// 创建工作文档对象
				Workbook wbook = new SXSSFWorkbook(10000);
				this.export(wbook, list, setting, 1);
				wbook.write(os); // 写入文件
				os.flush();
			} finally {
				try {
					os.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 物理文件名
		return realName;

	}

	private String exportMoreToFile(List<List<?>> list, List<ExportSetting> settingList) {
		String realName = getRealName();

		File f = null;
		try {
			f = new File(realName);
			f.createNewFile();

			OutputStream os = new FileOutputStream(f);

			try {
				// 创建工作文档对象
				Workbook wbook = new SXSSFWorkbook(10000);
				for(int i = 0; i < settingList.size(); i++){
					ExportSetting setting = settingList.get(i);
					List<?> dataList = list.get(i);
					this.export(wbook, dataList, setting, i);
				}

				wbook.write(os); // 写入文件
				os.flush();
			} finally {
				try {
					os.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 物理文件名
		return realName;

	}

    /**
     * 从数据库读数据，写入Excel
     * 
     * @param os
     *            数据流，如果是写本地文件的话，可以是FileOutputStream；
     *            如果是写Web下载的话，可以是ServletOupputStream
     * @param list
     *            数据结果列表
     * @param setting
     *            数据结果集对应Excel表列名映射导出对象
     * @throws Exception
     *             方法内的异常有ServiceException
     */
	private void export(Workbook wbook, Collection<?> list, ExportSetting setting, int index) throws Exception {

        // 创建sheet对象
    	String sheetName = setting.getFileName();
		Sheet sheet = wbook.createSheet(StringUtils.isNotBlank(sheetName) ? sheetName : "sheet" + index);
        
        ExportHandler handler = new ExportHandler();
        handler.handleHead(wbook, sheet, setting);
        handler.handleDatas(wbook, sheet, setting, list);
    }

}
