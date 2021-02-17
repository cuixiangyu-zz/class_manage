package org.jeecg.modules.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.entity.Activity;
import org.jeecg.modules.mapper.ActivityMapper;
import org.jeecg.modules.mapper.CollegeClassMapper;
import org.jeecg.modules.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;

/**
 * @Description: 活动
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    CollegeClassMapper collegeClassMapper;

    @Override
    public boolean save(Activity entity) {
        if(!StringUtils.isEmpty(entity.getClasses())){
            String s = checkClasses(entity.getClasses());
            entity.setClasses(s);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(Activity entity) {
        if(!StringUtils.isEmpty(entity.getClasses())){
            String s = checkClasses(entity.getClasses());
            entity.setClasses(s);
        }
        return super.updateById(entity);
    }

    private String checkClasses(String classes){
        String[] split = classes.split(",");
        List<String> strings = collegeClassMapper.checkClasses(split);
        return JSONObject.toJSONString(strings);
    }

}
