package org.jeecg.modules.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.entity.StudentClass;
import org.jeecg.modules.mapper.StudentClassMapper;
import org.jeecg.modules.service.IStudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StudentClassMapper studentClassMapper;


    @Override
    public boolean save(StudentClass entity) {
        super.save(entity);
        studentClassMapper.deleteDetailByInfoId(entity.getId());
        if(!StringUtils.isEmpty(entity.getClassName())){
            studentClassMapper.updateStudentClassDetailByClassName(entity.getId(),entity.getClassName());
        }else if(!StringUtils.isEmpty(entity.getMajor())){
            studentClassMapper.updateStudentClassDetailByMajor(entity.getId(),entity.getMajor());
        }else if(!StringUtils.isEmpty(entity.getInstitute())){
            studentClassMapper.updateStudentClassDetailByInstitute(entity.getId(),entity.getInstitute());
        }
        return true;
    }
}
