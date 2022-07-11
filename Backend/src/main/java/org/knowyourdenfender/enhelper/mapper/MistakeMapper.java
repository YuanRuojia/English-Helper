package org.knowyourdenfender.enhelper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.knowyourdenfender.enhelper.dao.MistakeDAO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MistakeMapper extends BaseMapper<MistakeDAO> {
}
