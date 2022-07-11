package org.knowyourdenfender.enhelper.service;

import org.knowyourdenfender.enhelper.dao.CollectDAO;
import org.knowyourdenfender.enhelper.dao.MistakeDAO;
import org.knowyourdenfender.enhelper.pojo.*;

import java.util.List;

public interface UserService {
     public UserVO login(UserDTO userDTO);
     public UserVO UserInfo(String name);
     public boolean changePassword(UserPasswordDTO userPasswordDTO);
     public Integer register(UserInfoDTO userInfoDTO);
     public List<CollectVO> collectInfo(Integer id);
     public List<MistakeVO> mistakeInfo(Integer id);
     public Integer wordCount();
     public Integer collectCount(Integer id);
     public List<Integer> weeklyCollect(Integer id);
     public List<Integer> weeklyMistake(Integer id);
}
