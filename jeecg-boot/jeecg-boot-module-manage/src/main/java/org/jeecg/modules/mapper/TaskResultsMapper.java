package org.jeecg.modules.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.TaskResults;

/**
 * @Description: 作业成绩
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface TaskResultsMapper extends BaseMapper<TaskResults> {

    @Select({
            "select * from task_results where student_id = #{studentId} and task_id = #{taskId}"
    })
    TaskResults getByTaskAndStudent(String taskId,String studentId);

    @Select({
            " select avg(case when result.results is null or result.results = '' then 0 else result.results end ) " ,
            " from task_results result left join student_task task on result.task_id = task.id ",
            " where result.student_id = #{studentId} and task.subject_id = #{subjectId} and task.task_type = 'homeWork'"
    })
    Double calculateGrade(String studentId, String subjectId);

    @Select({
            " select result.results   from task_results result left join student_task task on result.task_id = task.id ",
            " where result.student_id = #{studentId} and task.subject_id = #{subjectId} and task.task_type = #{taskType}"
    })
    Double getByTaskType(String studentId, String subjectId,String taskType);
}
