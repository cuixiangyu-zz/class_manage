package org.jeecg.modules.order.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 事件工单
 * @Author: jeecg-boot
 * @Date:   2020-07-07
 * @Version: V1.0
 */
@Data
@TableName("event_work_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="event_work_order对象", description="事件工单")
public class EventWorkOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**人员分类*/
	@Excel(name = "人员分类", width = 15, dicCode = "creater_type")
	@Dict(dicCode = "creater_type")
    @ApiModelProperty(value = "人员分类")
    private java.lang.String userType;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String company;
	/**部门*/
	@Excel(name = "部门", width = 15)
    @ApiModelProperty(value = "部门")
    private java.lang.String department;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private java.lang.String name;
	/**vip*/
	@Excel(name = "vip", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "vip")
    private java.lang.String vip;
	/**房间号*/
	@Excel(name = "房间号", width = 15)
    @ApiModelProperty(value = "房间号")
    private java.lang.String roomNo;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phoneNum;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String tel;
	/**事件类型*/
	@Excel(name = "事件类型", width = 15, dicCode = "event_work_type")
	@Dict(dicCode = "event_work_type")
    @ApiModelProperty(value = "事件类型")
    private java.lang.String eventType;
	/**事件标题*/
	@Excel(name = "事件标题", width = 15)
    @ApiModelProperty(value = "事件标题")
    private java.lang.String eventTitle;
	/**事件描述*/
	@Excel(name = "事件描述", width = 15)
    @ApiModelProperty(value = "事件描述")
    private java.lang.String eventDescription;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**事件来源*/
	@Excel(name = "事件来源", width = 15, dicCode = "event_work_source")
	@Dict(dicCode = "event_work_source")
    @ApiModelProperty(value = "事件来源")
    private java.lang.String eventSource;
	/**事件性质*/
	@Excel(name = "事件性质", width = 15, dicCode = "event_work_properties")
	@Dict(dicCode = "event_work_properties")
    @ApiModelProperty(value = "事件性质")
    private java.lang.String eventProperties;
	/**影响度*/
	@Excel(name = "影响度", width = 15, dicCode = "priority")
	@Dict(dicCode = "priority")
    @ApiModelProperty(value = "影响度")
    private java.lang.String affectLevel;
	/**紧急度*/
	@Excel(name = "紧急度", width = 15, dicCode = "priority")
	@Dict(dicCode = "priority")
    @ApiModelProperty(value = "紧急度")
    private java.lang.String emergencyLevel;
	/**优先级*/
	@Excel(name = "优先级", width = 15, dicCode = "event_work_priority")
	@Dict(dicCode = "event_work_priority")
    @ApiModelProperty(value = "优先级")
    private java.lang.String priority;
	/**服务级别*/
	@Excel(name = "服务级别", width = 15, dicCode = "service_level")
	@Dict(dicCode = "service_level")
    @ApiModelProperty(value = "服务级别")
    private java.lang.String serviceLevel;
	/**解决时间*/
	@Excel(name = "解决时间", width = 15)
    @ApiModelProperty(value = "解决时间")
    private java.math.BigDecimal dealTime;
	/**发生时间*/
	@Excel(name = "发生时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间")
    private java.util.Date startTime;
	/**响应时间*/
	@Excel(name = "响应时间", width = 15)
    @ApiModelProperty(value = "响应时间")
    private java.lang.Integer responseTime;
	/**处理方式*/
	@Excel(name = "处理方式", width = 15, dicCode = "deal_type")
	@Dict(dicCode = "deal_type")
    @ApiModelProperty(value = "处理方式")
    private java.lang.String treatmentMethod;
	/**解决时间*/
	@Excel(name = "解决时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "解决时间")
    private java.util.Date solutionTime;
	/**解决方案*/
	@Excel(name = "解决方案", width = 15)
    @ApiModelProperty(value = "解决方案")
    private java.lang.String solutions;
	/**问题附件*/
	@Excel(name = "问题附件", width = 15)
    @ApiModelProperty(value = "问题附件")
    private java.lang.String businessFile;
	/**解决附件*/
	@Excel(name = "解决附件", width = 15)
    @ApiModelProperty(value = "解决附件")
    private java.lang.String solutionsFile;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**解决人*/
	@Excel(name = "解决人", width = 15)
    @ApiModelProperty(value = "解决人")
    private java.lang.String dealer;
	/**解决时间*/
	@Excel(name = "解决时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "解决时间")
    private java.util.Date closeTime;
}
