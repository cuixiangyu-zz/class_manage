package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.StudentGradeItem;

/**
 * @Description: 成绩子表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface StudentGradeItemMapper extends BaseMapper<StudentGradeItem> {

    @Delete({
            " delete from student_grade_item where student_grade_id = #{studentGradeId} "
    })
    void delByStudentGradeId(String studentGradeId);

    @Select({
            " select * from student_grade_item where student_grade_id = #{studentGradeId} "
    })
    List<StudentGradeItem> getByStudentGradeId(String studentGradeId);
}
