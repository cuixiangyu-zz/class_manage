package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.StudentGradeItem;
import org.jeecg.modules.entity.TaskResults;

import java.util.List;
import java.util.Map;

/**
 * @Description: 作业成绩
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface ITaskResultsService extends IService<TaskResults> {

    Result updateTask(TaskResults taskResults);

    List<StudentGradeItem> calculateTask(String studentId, String xq, String xn);

    List<StudentGradeItem> getGradeList(String studentId, String xq, String xn);
}
