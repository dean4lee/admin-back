package cn.inslee.adminback.model.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author dean.lee
 * <p>
 * 通用mapper
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
