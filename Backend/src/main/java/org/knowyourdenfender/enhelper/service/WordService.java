package org.knowyourdenfender.enhelper.service;

import org.knowyourdenfender.enhelper.dao.CollectDAO;
import org.knowyourdenfender.enhelper.dao.MistakeDAO;
import org.knowyourdenfender.enhelper.dao.WordDAO;
import org.knowyourdenfender.enhelper.pojo.WordInsertDTO;
import org.knowyourdenfender.enhelper.pojo.WordTestVO;
import org.knowyourdenfender.enhelper.pojo.WordUpdateDTO;

public interface WordService {
    WordDAO search(String word);
    MistakeDAO mistake(Integer id, String word);
    CollectDAO collect(Integer id, String word);
    boolean insert(WordInsertDTO wordInsertDTO);
    boolean update(WordUpdateDTO wordUpdateDTO);
    boolean delete(String word);
	WordTestVO test();
}