package shen.gd.platform;

import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;
import tk.mybatis.mapper.common.base.select.SelectByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeySelectiveMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.common.example.SelectCountByExampleMapper;
import tk.mybatis.mapper.common.example.UpdateByExampleSelectiveMapper;
import tk.mybatis.mapper.common.rowbounds.SelectByExampleRowBoundsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * zmkj framework mapper, it extends that:
 * SelectOneMapper
 * SelectByExampleMapper
 * SelectByPrimaryKeyMapper
 * InsertSelectiveMapper
 * UpdateByExampleSelectiveMapper
 * UpdateByPrimaryKeySelectiveMapper
 *
 * @param <T> entity class
 * @author emmet
 */

public interface SyMapper<T> extends InsertListMapper<T>,InsertSelectiveMapper<T>, UpdateByExampleSelectiveMapper<T>, UpdateByPrimaryKeySelectiveMapper<T>, SelectOneMapper<T>, SelectByPrimaryKeyMapper<T>, SelectMapper<T>, SelectByExampleMapper<T>, SelectByExampleRowBoundsMapper<T>,SelectCountByExampleMapper<T> {

}

