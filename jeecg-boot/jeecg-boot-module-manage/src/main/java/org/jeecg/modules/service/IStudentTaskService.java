package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.StudentTask;

/**
 * @Description: 作业表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface IStudentTaskService extends IService<StudentTask> {

    Result<?> getTaskList(String studentId, String xq, String xn);
}
