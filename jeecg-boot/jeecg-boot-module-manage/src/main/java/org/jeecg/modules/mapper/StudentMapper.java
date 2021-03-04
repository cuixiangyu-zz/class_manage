package org.jeecg.modules.mapper;

import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2020-12-29
 * @Version: V1.0
 */
public interface StudentMapper extends BaseMapper<Student> {

    @Select({
            "select * from student where name = #{name}"
    })
    Student selectByName(String name);

}
