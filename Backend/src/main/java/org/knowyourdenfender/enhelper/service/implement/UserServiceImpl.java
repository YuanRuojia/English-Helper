package org.knowyourdenfender.enhelper.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.knowyourdenfender.enhelper.dao.CollectDAO;
import org.knowyourdenfender.enhelper.dao.MistakeDAO;
import org.knowyourdenfender.enhelper.dao.UserDAO;
import org.knowyourdenfender.enhelper.dao.WordDAO;
import org.knowyourdenfender.enhelper.mapper.CollectMapper;
import org.knowyourdenfender.enhelper.mapper.MistakeMapper;
import org.knowyourdenfender.enhelper.mapper.UserMapper;
import org.knowyourdenfender.enhelper.mapper.WordMapper;
import org.knowyourdenfender.enhelper.pojo.*;
import org.knowyourdenfender.enhelper.service.UserService;
import org.knowyourdenfender.enhelper.service.WordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final CollectMapper collectMapper;
    private final MistakeMapper mistakeMapper;
    private final WordMapper wordMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper,CollectMapper collectMapper,MistakeMapper mistakeMapper,WordMapper wordMapper) {
        this.userMapper = userMapper;
        this.collectMapper=collectMapper;
        this.mistakeMapper=mistakeMapper;
        this.wordMapper=wordMapper;
    }


    @Override
    public UserVO login(UserDTO userDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userDTO.getUserName());
        queryWrapper.eq("user_password",userDTO.getUserPassword());

        UserDAO userDAO = userMapper.selectOne(queryWrapper);

        ArrayList<Integer> result = new ArrayList<>();
        if(userDAO != null){
            UserVO userVO = new UserVO();
            userVO.setUserId(userDAO.getUserId());
            userVO.setUserName(userDAO.getUserName());
            userVO.setUserType(userDAO.getUserType());
            userVO.setUserPhone(userDAO.getUserPhone());
            return userVO;
        }
        return null;
    }

    @Override
    public UserVO UserInfo(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",name);

        UserDAO userDAO = userMapper.selectOne(queryWrapper);
        if(userDAO==null){
            throw new RuntimeException("没有查到用户信息");
        }
        UserVO userVO = new UserVO();
        userVO.setUserId(userDAO.getUserId());
        userVO.setUserType(userDAO.getUserType());
        userVO.setUserName(name);
        userVO.setUserPhone(userDAO.getUserPhone());

        return userVO;
    }

    @Override
    public boolean changePassword(UserPasswordDTO userPasswordDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userPasswordDTO.getName());
        queryWrapper.eq("user_password",userPasswordDTO.getOldPassword());

        UserDAO userDAO = new UserDAO();
        userDAO.setUserPassword(userPasswordDTO.getNewPassword());
        int updateRow = userMapper.update(userDAO, queryWrapper);
        return updateRow>=1?true:false;
    }

    @Override
    public Integer register(UserInfoDTO userInfoDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userInfoDTO.getUserName());

        UserDAO userDAO = userMapper.selectOne(queryWrapper);

        if(userDAO == null){
            UserDAO insertUserDAO = new UserDAO();
            insertUserDAO.setUserName(userInfoDTO.getUserName());
            insertUserDAO.setUserPassword(userInfoDTO.getUserPassword());
            insertUserDAO.setUserPhone(userInfoDTO.getUserPhone());
            insertUserDAO.setUserType(0);
            int insertRow = userMapper.insert(insertUserDAO);
            if(insertRow == 0){
                throw new RuntimeException("注册时保存数据失败");
            }
            else{
                QueryWrapper selectQuerywrapper = new QueryWrapper();
                selectQuerywrapper.eq("user_name",userInfoDTO.getUserName());
                selectQuerywrapper.eq("user_password",userInfoDTO.getUserPassword());

                UserDAO selectUserDAO = userMapper.selectOne(selectQuerywrapper);
                return selectUserDAO.getUserId();
            }
        }
        else{
            throw new RuntimeException("该用户名已存在");
        }
    }

    @Override
    public List<CollectVO> collectInfo(Integer id) {

        ArrayList<CollectVO> voList = new ArrayList<>();

        QueryWrapper<CollectDAO> wrapper= new QueryWrapper<>();
        wrapper.like("user_id",id);
        List<CollectDAO> collectDAOS= this.collectMapper.selectList(wrapper);
        for(CollectDAO cDAO : collectDAOS){
            CollectVO collectVO = new CollectVO();
            collectVO.setUserId(cDAO.getUserId());
            collectVO.setEnglishWords(cDAO.getWord());

            BeanUtils.copyProperties(cDAO,collectVO);

            voList.add(collectVO);
        }
        return voList;
    }

    @Override
    public List<MistakeVO> mistakeInfo(Integer id) {
        ArrayList<MistakeVO> voList = new ArrayList<>();

        QueryWrapper<MistakeDAO> wrapper= new QueryWrapper<>();
        wrapper.like("user_id",id);
        List<MistakeDAO> mistakeDAOS= this.mistakeMapper.selectList(wrapper);
        for(MistakeDAO mDAO : mistakeDAOS){
            MistakeVO mistakeVO = new MistakeVO();
            mistakeVO.setUserId(mDAO.getUserId());
            mistakeVO.setEnglishWords(mDAO.getEnglishWords());
            mistakeVO.setMistakeCount(mDAO.getMistakeCount());

            BeanUtils.copyProperties(mDAO,mistakeVO);

            voList.add(mistakeVO);
        }
        return voList;
    }

    @Override
    public Integer wordCount() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("english_words"," ");
        return wordMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer collectCount(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",id);
        Integer sum = collectMapper.selectCount(queryWrapper);
        return sum;
    }

    @Override
    public List<Integer> weeklyCollect(Integer id) {

        List<String> date=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();
        String monday = sdf.format(mondayDate);
        date.add(monday);

        //将本周从周一到周日的日期依次放入数组
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+1);
        Date tuesdayDate = cal.getTime();
        String tuesday = sdf.format(tuesdayDate);
        date.add(tuesday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+2);
        Date wednesdayDate = cal.getTime();
        String wednesday = sdf.format(wednesdayDate);
        date.add(wednesday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+3);
        Date thursdayDate = cal.getTime();
        String thursday = sdf.format(thursdayDate);
        date.add(thursday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+4);
        Date fridayDate = cal.getTime();
        String friday = sdf.format(fridayDate);
        date.add(friday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+5);
        Date saturdayDate = cal.getTime();
        String saturday = sdf.format(saturdayDate);
        date.add(saturday);

        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String sunday = sdf.format(sundayDate);
        date.add(sunday);


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        queryWrapper.ge("change_time",date.get(0));
        queryWrapper.le("change_time",date.get(6));

        List<CollectDAO> daoList=collectMapper.selectList(null);
        ArrayList<Integer> sumlist=new ArrayList<>();
        for(Integer i=0;i<=6;i++) {
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("user_id", id);
            queryWrapper1.eq("change_time",date.get(i));
            Integer count = collectMapper.selectCount(queryWrapper1);
            sumlist.add(count);
        }
        return sumlist;
    }

    @Override
    public List<Integer> weeklyMistake(Integer id) {
        List<String> date=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();
        String monday = sdf.format(mondayDate);
        date.add(monday);

        //将本周从周一到周日的日期依次放入数组
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+1);
        Date tuesdayDate = cal.getTime();
        String tuesday = sdf.format(tuesdayDate);
        date.add(tuesday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+2);
        Date wednesdayDate = cal.getTime();
        String wednesday = sdf.format(wednesdayDate);
        date.add(wednesday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+3);
        Date thursdayDate = cal.getTime();
        String thursday = sdf.format(thursdayDate);
        date.add(thursday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+4);
        Date fridayDate = cal.getTime();
        String friday = sdf.format(fridayDate);
        date.add(friday);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek+5);
        Date saturdayDate = cal.getTime();
        String saturday = sdf.format(saturdayDate);
        date.add(saturday);

        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String sunday = sdf.format(sundayDate);
        date.add(sunday);


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        queryWrapper.ge("change_time",date.get(0));
        queryWrapper.le("change_time",date.get(6));

        List<MistakeDAO> daoList=mistakeMapper.selectList(null);

        ArrayList<Integer> sumlist=new ArrayList<>();

        for(Integer i=0;i<=6;i++) {
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("user_id", id);
            queryWrapper1.eq("change_time",date.get(i));
            Integer count = mistakeMapper.selectCount(queryWrapper1);
            sumlist.add(count);
        }
        return sumlist;
    }
}
