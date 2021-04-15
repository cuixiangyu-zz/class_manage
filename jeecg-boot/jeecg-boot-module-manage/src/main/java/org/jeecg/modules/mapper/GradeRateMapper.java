package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.GradeRate;

/**
 * @Description: 成绩比例
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface GradeRateMapper extends BaseMapper<GradeRate> {

    @Select({
            " select * from grade_rate where subject_id = #{subjectId}"
    })
    GradeRate getBySubjectId(String subjectId);
}
