package org.jeecg.modules.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2020-12-29
 * @Version: V1.0
 */
public interface IStudentService extends IService<Student> {

    boolean checkStudentCode(String studentCode);

    List<Map<String, String>> getBySubjectId(String subjectId);
}
