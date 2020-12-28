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
 * @Description: 问题工单
 * @Author: jeecg-boot
 * @Date:   2020-07-07
 * @Version: V1.0
 */
@Data
@TableName("problem_work_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="problem_work_order对象", description="问题工单")
public class ProblemWorkOrder implements Serializable {
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
	/**问题归属*/
	@Excel(name = "问题归属", width = 15, dicCode = "creater_type")
	@Dict(dicCode = "creater_type")
    @ApiModelProperty(value = "问题归属")
    private java.lang.String problemAttribution;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private java.lang.String name;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.Integer tel;
	/**问题标题*/
	@Excel(name = "问题标题", width = 15)
    @ApiModelProperty(value = "问题标题")
    private java.lang.String problemTitle;
	/**问题描述*/
	@Excel(name = "问题描述", width = 15)
    @ApiModelProperty(value = "问题描述")
    private java.lang.String problemDescription;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**附件*/
	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String files;
	/**问题性质*/
	@Excel(name = "问题性质", width = 15, dicCode = "problem_properties")
	@Dict(dicCode = "problem_properties")
    @ApiModelProperty(value = "问题性质")
    private java.lang.String problemProperties;
	/**问题类别*/
	@Excel(name = "问题类别", width = 15, dicCode = "problem_type")
	@Dict(dicCode = "problem_type")
    @ApiModelProperty(value = "问题类别")
    private java.lang.String problemType;
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
	@Excel(name = "优先级", width = 15, dicCode = "priority")
	@Dict(dicCode = "priority")
    @ApiModelProperty(value = "优先级")
    private java.lang.String priority;
	/**重复问题*/
	@Excel(name = "重复问题", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "重复问题")
    private java.lang.Integer sameProblem;
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
