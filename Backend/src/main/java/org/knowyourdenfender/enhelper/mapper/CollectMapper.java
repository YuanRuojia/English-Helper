package org.knowyourdenfender.enhelper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.knowyourdenfender.enhelper.dao.CollectDAO;
import org.springframework.stereotype.Repository;

@Mapper
public interface CollectMapper extends BaseMapper<CollectDAO> {
}
