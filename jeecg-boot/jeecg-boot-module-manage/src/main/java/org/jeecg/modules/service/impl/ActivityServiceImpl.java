package org.jeecg.modules.service.impl;

import org.jeecg.modules.entity.Activity;
import org.jeecg.modules.mapper.ActivityMapper;
import org.jeecg.modules.service.IActivityService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 活动
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

}
