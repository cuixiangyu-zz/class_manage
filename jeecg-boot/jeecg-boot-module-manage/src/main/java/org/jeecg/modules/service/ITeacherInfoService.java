package org.jeecg.modules.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.TeacherInfo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
