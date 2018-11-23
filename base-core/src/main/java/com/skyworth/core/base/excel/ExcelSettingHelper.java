package com.skyworth.core.base.excel;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * ExcelSettingHelper excel帮助类，解析和生成xml配置信息
 * @author 魏诚
 */
public class ExcelSettingHelper {
	
	private static final Logger logger = Logger.getLogger(ExcelSettingHelper.class);
	
	@SuppressWarnings("unchecked")
	public static List<ExcelSetting> parseExcelSettings(Document document) {
		
		List<ExcelSetting> settings = new ArrayList<ExcelSetting>();
		
		try {
			Element root = document.getRootElement();
			List<Element> nodes = root.elements("excel");
            for (Element node : nodes) {
                settings.add(parseExcelSetting(node));
            }
            
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }

        return settings;
	}

	@SuppressWarnings("unchecked")
	public static ExcelSetting parseExcelSetting(String excelConfig) {
		
		ExcelSetting setting = new ExcelSetting();
		
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(excelConfig));
			
			Element root = document.getRootElement();
			List<Element> nodes = root.elements("excel");
			setting = parseExcelSetting(nodes.get(0));
            
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }

        return setting;
	}
	
	public static String createExcelSetting(ExcelSetting setting) {
		Document document = DocumentHelper.createDocument();
		Element conf = document.addElement("excel-configuration");
		Element excel = conf.addElement("excel");
		excel.addElement("id").addText(setting.getId());
		excel.addElement("className").addText(setting.getClassName());
		excel.addElement("description").addText(setting.getDescription());
		excel.addElement("startRow").addText(String.valueOf(setting.getStartRow()));
		excel.addElement("defaultConfig").addText(String.valueOf(setting.isDefaultConfig()));
		excel.addElement("stopOnError").addText(Boolean.toString(setting.isStopOnError()));
		excel.addElement("redirect").addText(Boolean.toString(setting.getRedirect()));
		if (StringUtils.isNotEmpty(setting.getNextAction()))
			excel.addElement("nextAction").addText(setting.getNextAction());
		if (StringUtils.isNotEmpty(setting.getIdField()))
			excel.addElement("idField").addText(setting.getIdField());
		if (StringUtils.isNotEmpty(setting.getCodeField()))
			excel.addElement("codeField").addText(setting.getCodeField());
		
		
		Element columns = excel.addElement("columns");
		//ExcelSettingColumn excelSettingColumn
		for ( Map.Entry<Integer, ExcelSettingColumn> entry : setting.getColumns().entrySet()) {
			Element column = columns.addElement("column");
			
			column.addAttribute("number", entry.getKey().toString());
			column.addAttribute("name", entry.getValue().getName());
			column.addAttribute("field", entry.getValue().getField());
			column.addAttribute("type", entry.getValue().getType().getName());
			column.addAttribute("required", String.valueOf(entry.getValue().isRequired()));
			if(entry.getValue().getMinLength() != null)
				column.addAttribute("minLength", entry.getValue().getMinLength().toString());
			if(entry.getValue().getMaxLength() != null)
				column.addAttribute("maxLength", entry.getValue().getMaxLength().toString());
		}
		
		
		XMLWriter writer = null;
		StringWriter sw = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");

		try {
			writer = new XMLWriter(sw, format);
			writer.setWriter(sw);
			writer.write(document);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) { }
		}

		return sw.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static ExcelSetting parseExcelSetting(Element node) throws ClassNotFoundException {
		ExcelSetting setting = new ExcelSetting();
		setting.setId(node.elementText("id"));
		setting.setClassName(node.elementText("className"));
		setting.setStartRow(Integer.valueOf(node.elementText("startRow")));
		setting.setDescription(node.elementText("description"));
		if (node.elementText("defaultConfig") != null)
			setting.setDefaultConfig(Boolean.valueOf(node.elementText("defaultConfig")));
		if (node.elementText("stopOnError") != null)
			setting.setStopOnError(Boolean.valueOf(node.elementText("stopOnError")));
		if (node.elementText("redirect") != null)
			setting.setRedirect(Boolean.valueOf(node.elementText("redirect")));
		if (node.elementText("nextAction") != null)
			setting.setNextAction(node.elementText("nextAction"));
		if (node.elementText("idField") != null)
			setting.setIdField(node.elementText("idField"));
		if (node.elementText("codeField") != null)
			setting.setCodeField(node.elementText("codeField"));
		
		
		Map<Integer, ExcelSettingColumn> excelColumns = new LinkedHashMap<Integer, ExcelSettingColumn>();
		List<Element> colNodes = node.element("columns").elements("column");
		for (Element colNode : colNodes) {
			ExcelSettingColumn column = new ExcelSettingColumn();
			
			column.setName(colNode.attributeValue("name"));
			column.setField(colNode.attributeValue("field"));
			column.setType(Class.forName(colNode.attributeValue("type")));
			column.setRequired(Boolean.valueOf(colNode.attributeValue("required")));
			if (StringUtils.isNotEmpty(colNode.attributeValue("minLength")))
				column.setMinLength(Integer.valueOf(colNode.attributeValue("minLength")));
			if (StringUtils.isNotEmpty(colNode.attributeValue("maxLength")))
				column.setMaxLength(Integer.valueOf(colNode.attributeValue("maxLength")));
			
			excelColumns.put(Integer.valueOf(colNode.attributeValue("number")), column);
		}
		
		setting.setColumns(excelColumns);
		
		return setting;
	}
	
}
