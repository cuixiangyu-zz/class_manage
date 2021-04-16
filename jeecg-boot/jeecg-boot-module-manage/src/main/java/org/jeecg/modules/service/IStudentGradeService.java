package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.StudentGrade;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface IStudentGradeService extends IService<StudentGrade> {

    StudentGrade getByStudentAndXqXn(String studentId, String xq, String xn);

    Result getGradeList(String studentId, String xq, String xn);

    Result calculate(String studentId,String xq,String xn);
}
