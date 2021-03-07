package org.jeecg.modules.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.StudentClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date: 2021-01-06
 * @Version: V1.0
 */
public interface StudentClassMapper extends BaseMapper<StudentClass> {

    @Insert({
            " insert into student_class_detail (jwc_account,student_class_id) ",
            "select id , #{id} from student where class_name = #{className}"
    })
    void updateStudentClassDetailByClassName(String id, String className);

    @Insert({
            " insert into student_class_detail (jwc_account,student_class_id) ",
            "select id , #{id} from student where major = #{major}"
    })
    void updateStudentClassDetailByMajor(String id, String major);

    @Insert({
            " insert into student_class_detail (jwc_account,student_class_id) ",
            "select id , #{id} from student where institute = #{institute}"
    })
    void updateStudentClassDetailByInstitute(String id, String institute);

    @Delete({
            "delete from  student_class_detail where student_class_id = #{id}"
    })
    void deleteDetailByInfoId(String id);

    @Select({
            "select dict.item_text as section ,a.星期一,a.星期二,a.星期三,a.星期四,a.星期五,a.星期六,a.星期日  from ( ",
            " SELECT section, ",
            " CASE WHEN classes.week_day = '1' THEN classes.subject_name else '' end '星期一', ",
            " CASE WHEN classes.week_day = '2' THEN classes.subject_name else '' end '星期二' , ",
            " CASE WHEN classes.week_day = '3' THEN classes.subject_name else '' end '星期三' , ",
            " CASE WHEN classes.week_day = '4' THEN classes.subject_name else '' end '星期四' , ",
            " CASE WHEN classes.week_day = '5' THEN classes.subject_name else '' end '星期五' , ",
            " CASE WHEN classes.week_day = '6' THEN classes.subject_name else '' end '星期六' , ",
            " CASE WHEN classes.week_day = '7' THEN classes.subject_name else '' end '星期日'  ",
            " FROM student_class classes ",
            " LEFT JOIN student_class_detail detail ON classes.id = detail.student_class_id  ",
            " WHERE detail.jwc_account = #{studentId}  ",
            " AND classes.week_start <= #{week} AND classes.week_end >= #{week}  ",
            " and classes.xn = #{xn} and classes.xq = #{xq} ",
            " GROUP BY classes.section ) a  ",
            " right join sys_dict_item dict on a.section = dict.item_value ",
            " where dict_id = '1362238747963006977' order by dict.item_value "
    })
    List<Map<String, String>> getClassesByStudent(String studentId, String week,String xn,String xq);
}
