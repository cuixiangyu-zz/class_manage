package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.StudentClass;
import org.jeecg.modules.mapper.StudentClassMapper;
import org.jeecg.modules.service.IStudentClassService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Service
public class StudentClassServiceImpl extends ServiceImpl<StudentClassMapper, StudentClass> implements IStudentClassService {

}
