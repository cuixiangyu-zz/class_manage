package org.jeecg.modules.service.impl;


import org.jeecg.modules.entity.GradeRate;
import org.jeecg.modules.mapper.GradeRateMapper;
import org.jeecg.modules.online.cgreport.service.CgReportExcelServiceI;
import org.jeecg.modules.service.IGradeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 成绩比例
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class GradeRateServiceImpl extends ServiceImpl<GradeRateMapper, GradeRate> implements IGradeRateService {

    @Autowired
    GradeRateMapper gradeRateMapper;

    @Override
    public GradeRate getBySubjectId(String subjectId) {

        GradeRate gradeRate = gradeRateMapper.getBySubjectId(subjectId);
        if(gradeRate==null){
            gradeRate = gradeRateMapper.getBySubjectId("default");
        }
        return gradeRate;
    }

    @Override
    public boolean save(GradeRate entity) {
        GradeRate gradeRate = gradeRateMapper.getBySubjectId(entity.getSubjectId());
        if(gradeRate!=null){
            entity.setId(gradeRate.getId());
            return super.updateById(entity);
        }
        return super.save(entity);
    }
}
