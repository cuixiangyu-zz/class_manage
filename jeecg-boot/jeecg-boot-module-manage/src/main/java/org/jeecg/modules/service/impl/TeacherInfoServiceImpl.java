package org.jeecg.modules.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.TeacherInfo;
import org.jeecg.modules.mapper.TeacherInfoMapper;
import org.jeecg.modules.service.ITeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 教师信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
@Service
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements ITeacherInfoService {

    @Autowired
    TeacherInfoMapper teacherInfoMapper;

    /**
     * 获取在职教师列表
     * @return
     */
    @Override
    public Result<?> getTeacherListForSalary() {
        List<DictModel> dictModelList =  teacherInfoMapper.getTeacherListForSalary();
        return Result.ok(dictModelList);
    }

    /**
     * 获取未关联教师用户列表
     * @return
     */
    @Override
    public Result<?> getUserList() {
        List<DictModel> dictModelList =  teacherInfoMapper.getUserList();
        return Result.ok(dictModelList);
    }
}
