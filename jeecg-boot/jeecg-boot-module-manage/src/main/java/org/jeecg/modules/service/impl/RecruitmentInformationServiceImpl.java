package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.RecruitmentInformation;
import org.jeecg.modules.mapper.RecruitmentInformationMapper;
import org.jeecg.modules.service.IRecruitmentInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 招聘信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
@Service
public class RecruitmentInformationServiceImpl extends ServiceImpl<RecruitmentInformationMapper, RecruitmentInformation> implements IRecruitmentInformationService {

    @Autowired
    private RecruitmentInformationMapper  recruitmentInformationMapper;


    @Override
    public void updateRecruitmentByTeacherId(String teacherId) {
        RecruitmentInformation recruitmentInformation = recruitmentInformationMapper.getByTeacherId(teacherId);
        if(recruitmentInformation==null){
            return;
        }
        Integer count = recruitmentInformationMapper.getOnJobCount(recruitmentInformation.getId());
        if(count>=Integer.parseInt(recruitmentInformation.getCount())){
            recruitmentInformationMapper.updateStatus(recruitmentInformation.getId());
            recruitmentInformation.setStatus("0");
            updateById(recruitmentInformation);
        }
    }
}
