package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.StudentCheck;

/**
 * @Description: 考勤表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface StudentCheckMapper extends BaseMapper<StudentCheck> {

    @Select({
            " select sum(case when type='late' or type = 'leave_early' then 1 when type = 'absence' then 2 end) " ,
            " from student_check " ,
            " where student_id = #{studentId} and subject_id = #{subjectId} "
    })
    Double calculateGrade(String studentId, String subjectId);
}
