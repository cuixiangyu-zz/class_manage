package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.StudentClass;

import java.util.List;
import java.util.Map;

/**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
public interface IStudentClassService extends IService<StudentClass> {

    List<Map<String, String>> getClasses(String studentName, String week,String xn,String xq);
}
