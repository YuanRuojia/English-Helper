package org.knowyourdenfender.enhelper.service.implement;

import org.knowyourdenfender.enhelper.mapper.UserMapper;
import org.knowyourdenfender.enhelper.mapper.WordMapper;
import org.knowyourdenfender.enhelper.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserMapper userMapper;
    private final WordMapper wordMapper;

    @Autowired
    public AdminServiceImpl(UserMapper userMapper, WordMapper wordMapper) {
        this.userMapper = userMapper;
        this.wordMapper = wordMapper;
    }
}
