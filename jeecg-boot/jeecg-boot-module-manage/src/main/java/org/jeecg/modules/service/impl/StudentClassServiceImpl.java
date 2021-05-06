package org.jeecg.modules.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.Student;
import org.jeecg.modules.entity.StudentClass;
import org.jeecg.modules.mapper.StudentClassMapper;
import org.jeecg.modules.mapper.StudentMapper;
import org.jeecg.modules.service.IStudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

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

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ISysBaseAPI sysBaseAPI;


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

    @Override
    public List<Map<String, String>> getClasses(String studentName, String week,String xn,String xq) {
        Student student = studentMapper.selectByName(studentName);
        return getClassesByStudent(student.getId(),week,xn,xq);
    }

    @Override
    public String getsubjectNameByCode(String subjectName) {
        List<DictModel> dictModelList = sysBaseAPI.queryDictItemsByCode("subject");
        for (DictModel dictModel : dictModelList) {
            if(dictModel.getValue().equals(subjectName)){
                return dictModel.getText();
            }
        }
        return null;
    }

    private List<Map<String, String>> getClassesByStudent(String studentId, String week,String xn,String xq){
        String isSingle = "0";
        if(Integer.parseInt(week.substring(5,7))%2==1){
            isSingle = "1";
        }
        return studentClassMapper.getClassesByStudent(studentId,week,xn,xq,isSingle);
    }
}
