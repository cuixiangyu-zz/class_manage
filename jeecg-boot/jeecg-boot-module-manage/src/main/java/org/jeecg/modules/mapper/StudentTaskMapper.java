package org.jeecg.modules.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.StudentTask;

/**
 * @Description: 作业表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface StudentTaskMapper extends BaseMapper<StudentTask> {

    @Select({
            "<script>",
            " select task.task_name as 'text',task.id as 'value' from student_task task " ,
            " left join student_class class on task.subject_id = class.id",
            " left join student_class_detail classDetail on class.id = classDetail.student_class_id ",
            " where 1=1 ",
            "<if test='studentId != null and studentId != \" \" '>",
            " and classDetail.jwc_account = #{studentId}",
            "</if>",
            "<if test='xq != null and xq != \"\" '>",
            " and class.xq = #{xq}",
            "</if>",
            "<if test='xn != null and xn != \"\" '>",
            " and class.xn = #{xn}",
            "</if>",
            "</script>"
    })
    List<Map<String, String>> getTaskList(String studentId, String xq, String xn);
}
