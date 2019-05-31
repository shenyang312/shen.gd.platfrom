package shen.gd.platform.service;

import shen.gd.platform.SyMapperUtil;
import shen.gd.platform.entity.GdOrderMerch;

import shen.gd.platform.mapper.GdOrderMerchMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class GdOrderMerchService {
	@Resource private GdOrderMerchMapper mapper;

	public Integer addGdOrderMerch(GdOrderMerch gdOrderMerch) {
		return mapper.insertSelective(gdOrderMerch);
	}

	public Integer modifyGdOrderMerch(GdOrderMerch gdOrderMerch, String... fieldStrs) {
		Example example = SyMapperUtil.generateExample(gdOrderMerch, fieldStrs);
		return mapper.updateByExampleSelective(gdOrderMerch, example);
	}

	public GdOrderMerch getGdOrderMerch(GdOrderMerch gdOrderMerch) {
		return mapper.selectOne(gdOrderMerch);
	}

	public List<GdOrderMerch> getGdOrderMerchs(GdOrderMerch gdOrderMerch) {
		return mapper.select(gdOrderMerch);
	}

}

