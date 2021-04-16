package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.StudentGrade;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface StudentGradeMapper extends BaseMapper<StudentGrade> {

    @Select({
            " select * from student_grade where student_id = #{studentId} and xq = #{xq} and xn=#{xn}"
    })
    StudentGrade getByStudentAndXqXn(String studentId, String xq, String xn);
}
