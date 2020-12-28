package org.jeecg.modules.order.service.impl;

import org.jeecg.modules.order.entity.EventWorkOrder;
import org.jeecg.modules.order.mapper.EventWorkOrderMapper;
import org.jeecg.modules.order.service.IEventWorkOrderService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 事件工单
 * @Author: jeecg-boot
 * @Date:   2020-07-07
 * @Version: V1.0
 */
@Service
public class EventWorkOrderServiceImpl extends ServiceImpl<EventWorkOrderMapper, EventWorkOrder> implements IEventWorkOrderService {

}
