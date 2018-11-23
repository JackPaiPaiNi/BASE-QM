package com.skyworth.core.dict.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.skyworth.core.base.BaseService;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.dict.dao.DictDao;
import com.skyworth.core.dict.vo.DictVo;

/**
 * 数据字典管理Service
 * @author 魏诚
 */
@Service
public class DictService extends BaseService<DictDao, DictVo> {
	
	@CacheEvict(value="sysCache",key="'DICT_'+#vo.getZdlxbm()")
	@Override
	public ResultEntity save(DictVo vo) {
		return super.save(vo);
	}

	@CacheEvict(value="sysCache",key="'DICT_'+#vo.getZdlxbm()")
	@Override
	public ResultEntity delete(DictVo vo) {
		return super.delete(vo);
	}
	
	/**
	 * 根据字典类型取数据字典缓存
	 * @param zdlxbm
	 * @return
	 */
	@Cacheable(value="sysCache",key="'DICT_'+#zdlxbm")
	public List<DictVo> getDictCacheByZdlx(String zdlxbm) {
		DictVo vo = new DictVo();
		vo.setZdlxbm(zdlxbm);
		List<DictVo> dictList = query(vo);
		return dictList;
	}
	
}