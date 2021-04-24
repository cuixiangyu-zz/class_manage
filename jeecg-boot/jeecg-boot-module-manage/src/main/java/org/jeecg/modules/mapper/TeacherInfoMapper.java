package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.TeacherInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 教师信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {

    @Select({
            "select user.realname as 'text',info.id as 'value' from teacher_info info left join sys_user user on info.base_info_id = user.id where info.work_status='on_job' "
    })
    List<DictModel> getTeacherListForSalary();

    @Select({
            "select user.realname as 'text',user.id as 'value' from sys_user user left join teacher_info info on info.base_info_id = user.id where info.id is null "
    })
    List<DictModel> getUserList();
}
