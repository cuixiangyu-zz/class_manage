package org.jeecg.modules.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.controller.TeacherInfoController;
import org.jeecg.modules.entity.TeacherInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 教师信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
public interface ITeacherInfoService extends IService<TeacherInfo> {

    Result<?> getTeacherListForSalary();

    Result<?> getUserList();

    boolean checkIsTeacher(String userId);

    Result<?> getAddTeacherList(String recruitmentInformationId);

    String getTeacherName(String baseInfoId);

    Result<?> confirmTeacher(TeacherInfoController.ConfirmBean confirmBean);

    boolean checkIsSchedul(String userName);

    List<DictModel> getByCode(String subject);

    String getNameByTeacherId(String id);

    boolean checkRole(String userName,String roleName);

    TeacherInfo getTeacherByUserId(String userId);
}
