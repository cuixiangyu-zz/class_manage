package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.StudentGradeItem;

import java.util.List;

/**
 * @Description: 成绩子表
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
public interface IStudentGradeItemService extends IService<StudentGradeItem> {

    void delByStudentGradeId(String id);

    List<StudentGradeItem> getByStudentGradeId(String id);
}
