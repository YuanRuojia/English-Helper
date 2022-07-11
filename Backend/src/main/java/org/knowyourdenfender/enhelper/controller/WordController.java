package org.knowyourdenfender.enhelper.controller;

import org.apache.catalina.Server;
import org.knowyourdenfender.enhelper.dao.CollectDAO;
import org.knowyourdenfender.enhelper.dao.MistakeDAO;
import org.knowyourdenfender.enhelper.dao.WordDAO;
import org.knowyourdenfender.enhelper.pojo.ServerResult;
import org.knowyourdenfender.enhelper.pojo.WordTestVO;
import org.knowyourdenfender.enhelper.pojo.WordInsertDTO;
import org.knowyourdenfender.enhelper.pojo.WordTestVO;
import org.knowyourdenfender.enhelper.pojo.WordUpdateDTO;
import org.knowyourdenfender.enhelper.service.UserService;
import org.knowyourdenfender.enhelper.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contains entrypoint for word related request.
 *
 * @author Miris,xxx...
 */
@RestController
@RequestMapping("/word")
@CrossOrigin
public class WordController {
    private final WordService wordService;
    private final UserService userService;

    @RequestMapping("/search")
    public ServerResult search(String englishWords){
        WordDAO wordDAO = wordService.search(englishWords);
        if(wordDAO==null)
            return new ServerResult(2, "搜索失败，单词不存在", null);
        else
            return new ServerResult(0,"搜索成功", wordDAO);
    }

    @RequestMapping("/mistake")
    public ServerResult mistake(Integer id, String word){
        MistakeDAO mistakeDAO = wordService.mistake(id, word);
        return new ServerResult(0, "添加错题成功", mistakeDAO);
    }

    @RequestMapping("/collect")
    public ServerResult collect(Integer id, String word){
        CollectDAO collectDAO = wordService.collect(id, word);
        if(collectDAO==null)
            return new ServerResult(1, "收藏本中已存在", null);
        else
            return new ServerResult(0, "添加收藏本成功", collectDAO);
    }

    @RequestMapping("/test")
    public ServerResult test(){
        WordTestVO wordTestVO = wordService.test();
        return new ServerResult(0, "成功", wordTestVO);
    }

    @RequestMapping("/insert")
    public ServerResult insert(WordInsertDTO dto){
        boolean isSuccess = wordService.insert(dto);
        if (isSuccess){
            return new ServerResult(0,"添加成功",null);
        }else{
            return new ServerResult(301,"添加失败",null);
        }
    }

    @RequestMapping("/update")
    public ServerResult update(WordUpdateDTO dto){
        boolean isSuccess = wordService.update(dto);
        if (isSuccess){
            return new ServerResult(0,"修改成功",null);
        }else{
            return new ServerResult(303,"修改失败",null);

        }
    }

    @RequestMapping("/delete")
    public ServerResult delete(String word){
        boolean isSuccess = wordService.delete(word);
        if (isSuccess){
            return new ServerResult(0,"删除成功",null);
        }else{
            return new ServerResult(302,"删除失败",null);

        }
    }

    @Autowired
    public WordController(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }
}
