package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.StudentTask;
import org.jeecg.modules.mapper.StudentTaskMapper;
import org.jeecg.modules.service.IStudentTaskService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 作业表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class StudentTaskServiceImpl extends ServiceImpl<StudentTaskMapper, StudentTask> implements IStudentTaskService {

}
