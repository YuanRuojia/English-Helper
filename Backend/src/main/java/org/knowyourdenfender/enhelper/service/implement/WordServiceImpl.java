package org.knowyourdenfender.enhelper.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.knowyourdenfender.enhelper.dao.CollectDAO;
import org.knowyourdenfender.enhelper.dao.MistakeDAO;
import org.knowyourdenfender.enhelper.dao.WordDAO;
import org.knowyourdenfender.enhelper.mapper.CollectMapper;
import org.knowyourdenfender.enhelper.mapper.MistakeMapper;
import org.knowyourdenfender.enhelper.mapper.WordMapper;
import org.knowyourdenfender.enhelper.pojo.WordInsertDTO;
import org.knowyourdenfender.enhelper.pojo.WordTestVO;
import org.knowyourdenfender.enhelper.pojo.WordUpdateDTO;
import org.knowyourdenfender.enhelper.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@Service
public class WordServiceImpl implements WordService {
    private int[] selected = new int[100];
    private int selectCount = 0;
    private final WordMapper wordMapper;
    private final MistakeMapper mistakeMapper;
    private final CollectMapper collectMapper;

    @Autowired
    public WordServiceImpl(WordMapper wordMapper, MistakeMapper mistakeMapper, CollectMapper collectMapper) {
        this.wordMapper = wordMapper;
        this.mistakeMapper = mistakeMapper;
        this.collectMapper = collectMapper;
    }

    @Override
    public WordDAO search(String word) {  //根据单词英文在单词表中查询单词信息
        //select * from english_words_table where english_words=word
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("english_words", word);

        WordDAO wordDAO = wordMapper.selectOne(queryWrapper);
        return wordDAO;
    }

    @Override
    public MistakeDAO mistake(Integer id, String word) {  //向错题本中新增单词
        int mistakeCount = 1;

        //检查该用户的错题本中是否有要添加的单词
        //select * from user_mistake_table where user_id=id and english_words=word
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        queryWrapper.eq("english_words",word);
        MistakeDAO mistakeDAO = mistakeMapper.selectOne(queryWrapper);

        //确认要操作的错题本中的新内容的基本信息
        MistakeDAO newMistakeDAO = new MistakeDAO();
        newMistakeDAO.setUserId(id);
        newMistakeDAO.setEnglishWords(word);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        simpleDateFormat.format(date);
        newMistakeDAO.setChangeTime(date);

        //当错题本中没有相应的单词
        if(mistakeDAO == null){
            //完善错题本要添加的内容的信息
            newMistakeDAO.setMistakeCount(mistakeCount);
            //向错题本中添加单词
            mistakeMapper.insert(newMistakeDAO);
        }
        //当错题本中存在该单词
        else{
            //错误次数加一
            mistakeCount = mistakeDAO.getMistakeCount();
            mistakeCount+=1;
            //完善要修改的错题本内容信息
            newMistakeDAO.setMistakeCount(mistakeCount);
            //在错题本中修改内容
            mistakeMapper.update(newMistakeDAO,queryWrapper);
        }
        return newMistakeDAO;
    }

    @Override
    public CollectDAO collect(Integer id, String word) {  //向收藏本中新增单词
        //检查该用户的收藏本中是否有要添加的单词
        //select * from user_collect_table where user_id=id and english_words=word
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        queryWrapper.eq("english_words",word);
        CollectDAO collectDAO = collectMapper.selectOne(queryWrapper);

        //当收藏本中没有相应的单词
        if(collectDAO == null){
            //确认要添加的单词的基本内容
            CollectDAO newCollectDAO = new CollectDAO();
            newCollectDAO.setUserId(id);
            newCollectDAO.setWord(word);
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            simpleDateFormat.format(date);
            newCollectDAO.setChangeTime(date);
            //向收藏本中添加单词
            collectMapper.insert(newCollectDAO);

            return newCollectDAO;
        }
        //当收藏本中存在该单词
        else
            return null;
    }
	@Override
    public WordTestVO test(){
        WordTestVO wordTestVO = new WordTestVO();

        QueryWrapper quetion = new QueryWrapper();
        List<WordDAO> questionList = wordMapper.selectList(quetion);
        int num = questionList.size();
        Random p = new Random();
        int questionNum = p.nextInt(num);
        int i = 0;
        while(i!=this.selectCount)
        {
            if(this.selected[i]==questionNum)
            {
                i = 0;
                questionNum = p.nextInt(num);
            }
            else
                i++;
        }
        this.selected[selectCount] = questionNum;
        this.selectCount++;

        wordTestVO.setWord(questionList.get(questionNum).getWord());
        String word = questionList.get(questionNum).getWord();
        wordTestVO.setSymbol(questionList.get(questionNum).getSymbol());

        Random r = new Random();
        int answer = r.nextInt(4);
        wordTestVO.setAnswer(answer);

        String A = null;
        String B = null;
        String C = null;
        String D = null;
        int a = -1;
        int b = -1;
        int c = -1;
        int d = -1;
        switch(answer){
            case 0:
                A = questionList.get(questionNum).getTrans();
                a = -2;
                break;
            case 1:
                B = questionList.get(questionNum).getTrans();
                b = -2;
                break;
            case 2:
                C = questionList.get(questionNum).getTrans();
                c = -2;
                break;
            case 3:
                D = questionList.get(questionNum).getTrans();
                d = -2;
                break;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("english_words", word);
        List<WordDAO> list = wordMapper.selectList(queryWrapper);
        int count = list.size();
        Random s = new Random();
        if(a==-1) {
            a = s.nextInt(count);
            A = list.get(a).getTrans();
        }
        if(b==-1) {

            b = s.nextInt(count);
            while(b==a)
                b = s.nextInt(count);
            B = list.get(b).getTrans();
        }
        if(c==-1) {
            c = s.nextInt(count);
            while(c==a || c==b)
                c = s.nextInt(count);
            C = list.get(c).getTrans();
        }
        if(d==-1){
            d = s.nextInt(count);
            while(d==a || d==b || d==c)
                d = s.nextInt(count);
            D = list.get(d).getTrans();
        }

        wordTestVO.setA(A);
        wordTestVO.setB(B);
        wordTestVO.setC(C);
        wordTestVO.setD(D);

        return wordTestVO;
    }    
	//插入单词
    @Override
    public boolean insert(WordInsertDTO wordInsertDTO) {
        //QueryWrapper queryWrapper = new QueryWrapper();
        //queryWrapper.eq("id",wordInsertDTO.getEnglishWords());

        WordDAO wordDAO = new WordDAO();
        BeanUtils.copyProperties(wordInsertDTO,wordDAO);
        int insertRow = wordMapper.insert(wordDAO);
        return insertRow>=1?true:false;
    }

    //更新修改单词
    @Override
    public boolean update(WordUpdateDTO wordUpdateDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("english_words",wordUpdateDTO.getEnglishWords());

        //新的数据放在dao中
        WordDAO wordDAO = new WordDAO();
        BeanUtils.copyProperties(wordUpdateDTO,wordDAO);

        int updateRow = wordMapper.update(wordDAO,queryWrapper);
        return updateRow>=1?true:false;
    }

    //删除单词
    @Override
    public boolean delete(String word) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("english_words",word);

        int deleteRow = wordMapper.delete(queryWrapper);
        return deleteRow>=1?true:false;
    }
}
