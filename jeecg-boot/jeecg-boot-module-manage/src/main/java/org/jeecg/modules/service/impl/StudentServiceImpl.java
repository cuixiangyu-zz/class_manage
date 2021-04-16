package org.jeecg.modules.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.entity.Student;
import org.jeecg.modules.mapper.StudentMapper;
import org.jeecg.modules.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2020-12-29
 * @Version: V1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public boolean checkStudentCode(String studentCode) {
        if(StringUtils.isEmpty(studentCode)){
            return false;
        }
        Student student = studentMapper.selectByStudentCode(studentCode);
        return student != null;
    }

    @Override
    public List<Map<String, String>> getBySubjectId(String subjectId) {
        return studentMapper.getBySubjectId(subjectId);
    }
}
