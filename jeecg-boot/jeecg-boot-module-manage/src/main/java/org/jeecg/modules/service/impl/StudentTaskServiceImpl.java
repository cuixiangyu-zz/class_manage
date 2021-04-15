package org.jeecg.modules.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.StudentTask;
import org.jeecg.modules.mapper.StudentTaskMapper;
import org.jeecg.modules.service.IStudentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 作业表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class StudentTaskServiceImpl extends ServiceImpl<StudentTaskMapper, StudentTask> implements IStudentTaskService {

    @Autowired
    StudentTaskMapper studentTaskMapper;

    @Override
    public Result<?> getTaskList(String studentId, String xq, String xn) {
        List<Map<String,String>> list = studentTaskMapper.getTaskList(studentId,xq,xn);
        return Result.ok(list);
    }
}
