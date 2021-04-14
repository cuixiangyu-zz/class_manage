package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.StudentGrade;
import org.jeecg.modules.mapper.StudentGradeMapper;
import org.jeecg.modules.service.IStudentGradeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class StudentGradeServiceImpl extends ServiceImpl<StudentGradeMapper, StudentGrade> implements IStudentGradeService {

}
