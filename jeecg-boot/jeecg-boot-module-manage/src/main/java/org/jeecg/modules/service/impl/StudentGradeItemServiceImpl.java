package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.StudentGradeItem;
import org.jeecg.modules.mapper.StudentGradeItemMapper;
import org.jeecg.modules.service.IStudentGradeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 成绩子表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class StudentGradeItemServiceImpl extends ServiceImpl<StudentGradeItemMapper, StudentGradeItem> implements IStudentGradeItemService {

    @Autowired
    StudentGradeItemMapper studentGradeItemMapper;

    @Override
    public void delByStudentGradeId(String studentGradeId) {
        studentGradeItemMapper.delByStudentGradeId(studentGradeId);
    }

    @Override
    public List<StudentGradeItem> getByStudentGradeId(String studentGradeId) {
        return studentGradeItemMapper.getByStudentGradeId(studentGradeId);
    }
}
