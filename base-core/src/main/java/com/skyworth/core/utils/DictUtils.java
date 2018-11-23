package com.skyworth.core.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.skyworth.core.dict.service.DictService;
import com.skyworth.core.dict.vo.DictVo;
import com.skyworth.core.spring.SpringUtils;;

/**
 * 字典工具类
 * @author 魏诚
 * @since 2016-11-15
 */
public class DictUtils {
	
	private static DictService dictService = SpringUtils.getBean(DictService.class);
	
	/**
	 * 根据字典类型编码查询数据字典
	 * @param zdlxbm字典类型编码
	 * @return
	 */
	private static List<DictVo> queryByZdlx(String zdlxbm){
		// 去缓存中的数据字典
		
		List<DictVo> dictList = dictService.getDictCacheByZdlx(zdlxbm);
		return dictList;
	}
	
	/**
	 * 将数据字典List转换为html代码
	 * @param list
	 * @param defCode默认值
	 * @return
	 */
	private static String dictListToHtml(List<DictVo> list, String defCode) {
    	if (list == null) {
			return "";
		}
    	StringBuilder sb = new StringBuilder();
		sb.append("<option role=\"option\" value=\"\"");
		if (defCode != null){
			sb.append(">");
		} else {
			sb.append(" selected>");
		}
		sb.append("--请选择--</option>");
    	for (int i = 0; i < list.size(); i++) {
    		DictVo record = list.get(i);
    		sb.append("<option role=\"option\" value=\"");
    		sb.append(record.getBm());
    		sb.append("\" id=\"");
    		sb.append(record.getId());
    		
    		if (defCode != null && defCode.equals(record.getBm())) {
    			sb.append("\" selected>");
			} else {
				sb.append("\">");
			}
    		
    		sb.append(record.getMc());
    		sb.append("</option>");
		}
    	return sb.toString();
    }
	
	/**
	 * 将数据字典List转换为json字符串
	 * @param list
	 * @return
	 */
	private static String dictListToJson(List<DictVo> list) {
    	if (list == null) {
			return "";
		}
    	StringBuilder sb = new StringBuilder();
    	sb.append(":请选择;");
    	for (int i = 0; i < list.size(); i++) {
    		DictVo record = list.get(i);
    		sb.append(record.getBm());
    		sb.append(":");
    		sb.append(record.getMc());
    		sb.append(";");
		}
    	return StringUtils.substringBeforeLast(sb.toString(), ";");
    }
    
	/**
	 * 加载数据字典（无默认值）
	 * @param type
	 * @return
	 */
	public static String loadDictOption(String type){
    	List<DictVo> list = queryByZdlx(type);
    	return dictListToHtml(list, null);
    }
	
	/**
	 * 加载数据字典（有默认值）
	 * @param type
	 * @param defCode
	 * @return
	 */
    public static String loadDictOption(String type, String defCode){
    	List<DictVo> list = queryByZdlx(type);
    	return dictListToHtml(list, defCode);
    }
    
    
    public static String loadDictJson(String type){
    	List<DictVo> list = queryByZdlx(type);
    	return dictListToJson(list);
    }
}
