package org.jeecg.modules.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.StudentGrade;
import org.jeecg.modules.entity.StudentGradeItem;
import org.jeecg.modules.mapper.StudentGradeItemMapper;
import org.jeecg.modules.mapper.StudentGradeMapper;
import org.jeecg.modules.service.IGradeRateService;
import org.jeecg.modules.service.IStudentCheckService;
import org.jeecg.modules.service.IStudentGradeService;
import org.jeecg.modules.service.ITaskResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class StudentGradeServiceImpl extends ServiceImpl<StudentGradeMapper, StudentGrade> implements IStudentGradeService {

    @Autowired
    StudentGradeMapper studentGradeMapper;

    @Autowired
    StudentGradeItemMapper studentGradeItemMapper;

    @Autowired
    ITaskResultsService taskResultsService;

    @Autowired
    IStudentCheckService studentCheckService;

    @Autowired
    IGradeRateService gradeRateService;

    public Result calculate(String studentId,String xq,String xn){
        List<StudentGradeItem> taskGrade = taskResultsService.calculateTask(studentId,xq,xn);
        return Result.ok(taskGrade);
    }

    public Result getGradeList(String studentId,String xq,String xn){
        List<StudentGradeItem> taskGrade = taskResultsService.getGradeList(studentId,xq,xn);
        return Result.ok(taskGrade);
    }

    @Override
    public StudentGrade getByStudentAndXqXn(String studentId, String xq, String xn) {
        return studentGradeMapper.getByStudentAndXqXn(studentId,xq,xn);
    }
}
