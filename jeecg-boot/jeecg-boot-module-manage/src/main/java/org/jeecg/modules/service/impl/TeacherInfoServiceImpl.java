package org.jeecg.modules.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.controller.TeacherInfoController;
import org.jeecg.modules.entity.RecruitmentInformation;
import org.jeecg.modules.entity.TeacherInfo;
import org.jeecg.modules.mapper.TeacherInfoMapper;
import org.jeecg.modules.service.IRecruitmentInformationService;
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

    @Autowired
    IRecruitmentInformationService recruitmentInformationService;

    @Autowired
    ISysBaseAPI sysBaseAPI;

    //试用
    private final String STATUS_TEST = "testing";
    //在职
    private final String STATUS_ON_JOB = "on_job";
    //离职
    private final String STATUS_QUIT = "quit";


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

    @Override
    public boolean checkIsTeacher(String userId) {
        List<String> ids = teacherInfoMapper.checkIsTeacher();
        if(ids.contains(userId)){
            return true;
        }
        return false;
    }

    @Override
    public Result<?> getAddTeacherList(String recruitmentInformationId) {
        RecruitmentInformation information = recruitmentInformationService.getById(recruitmentInformationId);
        if(information==null){
            return Result.error("招聘信息不存在！");
        }
        List<TeacherInfo> teacherInfoList = teacherInfoMapper.getAddTeacherList(information);

        Page<TeacherInfo> page = new Page<TeacherInfo>(1, 50);
        IPage<TeacherInfo> pageList = page.setRecords(teacherInfoList);

        return Result.ok(pageList);
    }

    @Override
    public String getTeacherName(String baseInfoId) {
        return teacherInfoMapper.getTeacherName(baseInfoId);
    }

    @Override
    public Result<?> confirmTeacher(TeacherInfoController.ConfirmBean confirmBean) {
        teacherInfoMapper.confirmTeacher(confirmBean.getRecruitmentId(),confirmBean.getTeacherList());
        teacherInfoMapper.updateStatus(confirmBean.getTeacherList(),STATUS_TEST);
        return Result.ok();
    }

    @Override
    public boolean checkIsSchedul(String userName) {
        List<String> roleList = sysBaseAPI.getRolesByUsername(userName);
        if(roleList.contains("schedul")){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRole(String userName,String roleName) {
        List<String> roleList = sysBaseAPI.getRolesByUsername(userName);
        if(roleList.contains(roleName)){
            return true;
        }
        return false;
    }

    @Override
    public List<DictModel> getByCode(String subject) {
        return teacherInfoMapper.getByCode(subject);
    }

    @Override
    public String getNameByTeacherId(String id) {
        return teacherInfoMapper.getNameByTeacherId(id);
    }

    @Override
    public TeacherInfo getTeacherByUserId(String userId){
        return teacherInfoMapper.getTeacherByUserId(userId);
    }
}
