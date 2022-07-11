package org.knowyourdenfender.enhelper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.knowyourdenfender.enhelper.dao.WordDAO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WordMapper extends BaseMapper<WordDAO> {
}
