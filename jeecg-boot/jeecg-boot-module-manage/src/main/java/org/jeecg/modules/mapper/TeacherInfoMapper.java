package org.jeecg.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.RecruitmentInformation;
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
            "select user.realname as 'text',user.id as 'value' from sys_user user left join teacher_info info on info.base_info_id = user.id " ,
            " left join sys_user_role userRole on user.id = userRole.user_id left join sys_role role on userRole.role_id",
            " = role.id where info.id is null and role.role_code = 'teacher' "
    })
    List<DictModel> getUserList();

    @Select({
            " select user.id from sys_user user left join sys_user_role userrole on user.id = userrole.user_id ",
            " left join sys_role role on  userrole.role_id = role.id where role.role_code = 'teacher' "
    })
    List<String> checkIsTeacher();

    @Select({
            "<script>",
            " select tea.* from teacher_info tea left join  sys_user user on tea.base_info_id = user.id ",
            " where 1=1 ",
            "  <if test='information.subject != null and information.subject!=\"\" '> ",
            " and  tea.subject = #{information.subject}",
            "  </if> ",
            "  <if test='information.education != null and information.education!=\"\" '> ",
            " and  tea.education = #{information.education}",
            "  </if> ",
            "  <if test='information.workingYears != null and information.workingYears!=\"\" '> ",
            " and  tea.working_years = #{information.workingYears}",
            "  </if> ",
            "  <if test='information.graduateSchoolType != null and information.graduateSchoolType!=\"\" '> ",
            " and  tea.graduate_school_type = #{information.graduateSchoolType}",
            "  </if> ",
            "  <if test='information.sex != null and information.sex!=\"\" '> ",
            " and  user.sex = #{information.sex}",
            "  </if> ",
            " and  tea.work_status = 'quit' ",
            " and tea.id not in(select teacher_id from recruitment_teacher where recruitment_id = #{information.id}) ",
            "</script>"
    })
    List<TeacherInfo> getAddTeacherList(@Param("information") RecruitmentInformation information);

    @Select({
            " select realname from sys_user where id = #{baseInfoId}"
    })
    String getTeacherName(String baseInfoId);

    @Insert({
            "<script>",
            " insert into recruitment_teacher VALUES ",
            " <foreach collection='list' item='id' index='index' open='' separator=',' close='' > ",
            " (#{recruitmentId},#{id}) ",
            " </foreach> ",
            " </script> "
    })
    void confirmTeacher(@Param("recruitmentId") String recruitmentId,@Param("list") List<String> list);

    @Update({
            "<script>",
            " update teacher_info set work_status = #{status} where id in ",
            " <foreach collection='list' item='id' index='index' open='(' separator=',' close=')' > ",
            " #{id} ",
            " </foreach> ",
            " </script> "
    })
    void updateStatus(@Param("list") List<String> list, @Param("status") String status);

    @Select({
            " select user.realname as 'text',tea.id as 'value' from teacher_info tea left join sys_user user on ",
            " tea.base_info_id = user.id where tea.subject=#{subject} and tea.work_status = 'on_job'"
    })
    List<DictModel> getByCode(String subject);

    @Select({
            " select user.realname as 'text' from teacher_info tea left join sys_user user on ",
            " tea.base_info_id = user.id where tea.id=#{id} "
    })
    String getNameByTeacherId(String id);

    @Select({
            " select tea.* from teacher_info tea left join sys_user user on ",
            " tea.base_info_id = user.id where user.id=#{userId} "
    })
    TeacherInfo getTeacherByUserId(String userId);
}
