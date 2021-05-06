package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.entity.RecruitmentInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 招聘信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
public interface RecruitmentInformationMapper extends BaseMapper<RecruitmentInformation> {

    @Select({
            " select rec.* from recruitment_information rec left join recruitment_teacher ret on rec.id = ret.recruitment_id ",
            " where ret.teacher_id = #{teacherId} and rec.status = '1'"
    })
    RecruitmentInformation getByTeacherId(String teacherId);

    @Select({
            " select count(tea.id) from recruitment_information rec left join recruitment_teacher ret on rec.id = ret.recruitment_id ",
            " left join teacher_info tea on ret.teacher_id = tea.id ",
            " where rec.id = #{id} and  tea.work_status = 'on_job' "
    })
    Integer getOnJobCount(String id);

    @Update({
            " update  teacher_info tea set tea.work_status = 'quit' where tea.work_status <> 'on_job' and  " ,
            " tea.id in (select ret.teacher_id from recruitment_teacher ret where ret.recruitment_id = #{id}) "
    })
    void updateStatus(String id);
}
