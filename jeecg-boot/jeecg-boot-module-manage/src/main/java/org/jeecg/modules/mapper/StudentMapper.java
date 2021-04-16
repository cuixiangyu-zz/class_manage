package org.jeecg.modules.mapper;

import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2020-12-29
 * @Version: V1.0
 */
public interface StudentMapper extends BaseMapper<Student> {

    @Select({
            "select * from student where name = #{name}"
    })
    Student selectByName(String name);

    @Select({
            "select * from student where student_code = #{studentCode}"
    })
    Student selectByStudentCode(String studentCode);

    @Select({
            " select stu.name as 'text',stu.id as 'value' from student stu " ,
            " left join student_class_detail detail on stu.id= detail.jwc_account ",
            " where  detail.student_class_id = #{subjectId} "
    })
    List<Map<String, String>> getBySubjectId(String subjectId);
}
