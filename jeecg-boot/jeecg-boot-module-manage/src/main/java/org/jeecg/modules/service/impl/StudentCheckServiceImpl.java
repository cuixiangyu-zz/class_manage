package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.StudentCheck;
import org.jeecg.modules.mapper.StudentCheckMapper;
import org.jeecg.modules.service.IStudentCheckService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 考勤表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class StudentCheckServiceImpl extends ServiceImpl<StudentCheckMapper, StudentCheck> implements IStudentCheckService {

}
