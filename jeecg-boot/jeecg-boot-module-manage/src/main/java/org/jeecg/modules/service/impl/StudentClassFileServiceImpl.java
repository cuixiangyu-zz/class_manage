package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.entity.Activity;
import org.jeecg.modules.entity.StudentClassFile;
import org.jeecg.modules.mapper.ActivityMapper;
import org.jeecg.modules.mapper.StudentClassFileMapper;
import org.jeecg.modules.service.IStudentClassFileService;
import org.springframework.stereotype.Service;

/**
 * @program: class_manage
 * @description:
 * @author: CuiXiangYu
 * @create: 2021-05-06 23:23
 **/
@Service
public class StudentClassFileServiceImpl extends ServiceImpl<StudentClassFileMapper, StudentClassFile> implements IStudentClassFileService {
}
