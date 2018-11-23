package com.skyworth.core.base.gridExport;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.skyworth.core.utils.DateUtils;

import net.sf.cglib.beans.BeanMap;

/**
 * 导出处理类
 * 
 * @author 魏诚
 *
 */
public class ExportHandler {

	/**
	 * 定义表头
	 */
	public void handleHead(final Workbook wbook, final Sheet sheet, final ExportSetting setting) {

		// 根据ExportColModelList来创建Excel的列名
		List<GridModel> exportColModels = setting.getColumns();
		List<GridGroupHeaders> groupHeaderList = setting.getGroupHeaders();
		int titleRowTotal = groupHeaderList.size();

		CellStyle style = wbook.createCellStyle();// 新建样式对象
		style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置表头背景颜色
		style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);

		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 下边框
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 左边框
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 上边框
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 右边框

		XSSFFont font = (XSSFFont) wbook.createFont();// 创建字体对象
		font.setFontName("微软雅黑");
		font.setFontHeight(9);
		font.setBold(true);
		style.setFont(font);

		Map<Integer, Integer> colNumMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < titleRowTotal; i++) {
			List<GridGroupHeader> list = groupHeaderList.get(i).getGroupHeaderList();
			Row header = sheet.createRow(i);
			for (int j = 0; j < list.size(); j++) {
				GridGroupHeader groupHeader = list.get(j);
				int colNum = groupHeader.getStartColumn();

				Integer total = colNumMap.get(colNum);
				if (total == null) {
					total = 0;
				}
				colNumMap.put(colNum, total + 1);
				Integer numberOfColumns = groupHeader.getNumberOfColumns();
				if (1 != numberOfColumns) {
					int colEnd = colNum + numberOfColumns - 1;
					CellRangeAddress cra = new CellRangeAddress(i, i, colNum, colEnd);
					this.setRegionStyle(sheet, cra, style);
					sheet.addMergedRegion(cra);

					int curNum = colNum + 1;
					while (curNum <= colEnd) {
						Integer curTotal = colNumMap.get(curNum);
						if (curTotal == null) {
							curTotal = 0;
						}
						colNumMap.put(curNum, curTotal + 1);
						curNum++;
					}
				}
				Cell cell = header.createCell(colNum);
				cell.setCellStyle(style);
				cell.setCellValue(groupHeader.getTitleText());
			}
		}

		int count = exportColModels.size();
		Row header = sheet.createRow(titleRowTotal);
		for (int i = 0; i < count; i++) {
			String name = exportColModels.get(i).getLabel();
			if (name == null) {
				break;
			}
			Integer rowNum = titleRowTotal;
			if (titleRowTotal != 0) {
				rowNum = colNumMap.get(i);
				rowNum = rowNum == null ? 0 : rowNum;
				if (titleRowTotal != rowNum) {
					CellRangeAddress cra = new CellRangeAddress(rowNum, titleRowTotal, i, i);
					this.setRegionStyle(sheet, cra, style);
					sheet.addMergedRegion(cra);
				}
			}
			header = sheet.getRow(rowNum);
			Cell cell = header.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(name);
		}
	}

	/**
	 * 处理数据
	 */
	public void handleDatas(final Workbook wbook, final Sheet sheet, final ExportSetting setting,	final Collection<?> list) {
		List<GridGroupHeaders> groupHeaderList = setting.getGroupHeaders();
		int startRowNum = groupHeaderList.size();

		List<GridModel> exportColModels = setting.getColumns();
		
		for (int i = 0; i < exportColModels.size(); i++) {
			GridModel model = exportColModels.get(i);
			
			CellStyle cellStyle = getTextStyle(wbook);
			
			String skyFormatType = model.getSkyFormatType();
			if("date".equals(skyFormatType) || "number".equals(skyFormatType)){
				String format = model.getSkyFormat();
				DataFormat dataFormat = wbook.createDataFormat();
				cellStyle.setDataFormat(dataFormat.getFormat(format));
			}				
			
			model.setCellStyle(cellStyle);
		}

		int rowNum = startRowNum;

		for (Object obj : list) {
			rowNum++;
			Row row = sheet.createRow(rowNum);
			Map<?, ?> map = BeanMap.create(obj);
			for (int i = 0; i < exportColModels.size(); i++) {
				GridModel model = exportColModels.get(i);
				CellStyle cellStyle = model.getCellStyle();
				String col = model.getName();
				Object propertye = null;
				propertye = map.get(col);
				Cell cell = row.createCell(i);

				if (propertye == null) {
					cell.setCellStyle(cellStyle);
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.BLANK);
				} else {
					cell.setCellStyle(cellStyle);
					if(propertye instanceof Integer){
						cell.setCellValue(Integer.parseInt(propertye.toString()));
					}else if(propertye instanceof Double){
						Integer unit = model.getSkyFormatUnit();
						Double value = Double.parseDouble(propertye.toString());
						if(unit != null){
							value = value/unit;
						}
						cell.setCellValue(value);
					}else if(propertye instanceof Date){					
						Date date = null;
						try {
							date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(propertye.toString());
						} catch (Exception e) {
							date = DateUtils.convert(propertye.toString());
						}
						cell.setCellStyle(cellStyle);
						cell.setCellValue(date);
					}else{
						cell.setCellValue(propertye.toString());
					}
				}

			}
		}
	}
	
	protected CellStyle getTextStyle(Workbook wbook){
		// 设置单元格类型
		CellStyle cellStyle = wbook.createCellStyle();

		cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 左边框
		cellStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 上边框
		cellStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 右边框

		XSSFFont font = (XSSFFont) wbook.createFont();// 创建字体对象
		font.setFontName("微软雅黑");
		font.setFontHeight(9);
		cellStyle.setFont(font);
		return cellStyle;
	}

	protected void setRegionStyle(Sheet sheet, CellRangeAddress region, CellStyle cellStyle) {
		RegionUtil.setBorderTop(cellStyle.getBorderTopEnum().getCode(), region, sheet);
		RegionUtil.setBorderRight(cellStyle.getBorderRightEnum().getCode(), region, sheet);
		RegionUtil.setBorderBottom(cellStyle.getBorderBottomEnum().getCode(), region, sheet);
		RegionUtil.setBorderLeft(cellStyle.getBorderLeftEnum().getCode(), region, sheet);
	}

}
