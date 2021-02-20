package org.jeecg.modules.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.jeecg.modules.entity.StudentClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
public interface StudentClassMapper extends BaseMapper<StudentClass> {

    @Insert({

    })
    void updateStudentClassDetailByClassName(String id, String className);

    @Insert({

    })
    void updateStudentClassDetailByMajor(String id, String major);

    @Insert({

    })
    void updateStudentClassDetailByInstitute(String id, String institute);

    @Delete({
            "delete from  student_class_detail where student_class_id = #{id}"
    })
    void deleteDetailByInfoId(String id);
}
