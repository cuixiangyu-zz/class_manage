package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.Student;
import org.jeecg.modules.mapper.StudentMapper;
import org.jeecg.modules.service.IStudentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2020-12-29
 * @Version: V1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
