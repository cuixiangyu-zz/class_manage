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
 * @Description: 发布工单
 * @Author: jeecg-boot
 * @Date:   2020-07-07
 * @Version: V1.0
 */
@Data
@TableName("issue_work_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issue_work_order对象", description="发布工单")
public class IssueWorkOrder implements Serializable {
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
	/**申请人*/
	@Excel(name = "申请人", width = 15)
    @ApiModelProperty(value = "申请人")
    private java.lang.String creater;
	/**部门*/
	@Excel(name = "部门", width = 15)
    @ApiModelProperty(value = "部门")
    private java.lang.String department;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String tel;
	/**申请时间*/
	@Excel(name = "申请时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "申请时间")
    private java.util.Date startTime;
	/**发布类型*/
	@Excel(name = "发布类型", width = 15, dicCode = "issue_work_type")
	@Dict(dicCode = "issue_work_type")
    @ApiModelProperty(value = "发布类型")
    private java.lang.String publishType;
	/**发布分类*/
	@Excel(name = "发布分类", width = 15, dicCode = "issue_work_classification")
	@Dict(dicCode = "issue_work_classification")
    @ApiModelProperty(value = "发布分类")
    private java.lang.String publishClassification;
	/**发布主题*/
	@Excel(name = "发布主题", width = 15)
    @ApiModelProperty(value = "发布主题")
    private java.lang.String publishTitle;
	/**发布内容*/
	@Excel(name = "发布内容", width = 15)
    @ApiModelProperty(value = "发布内容")
    private java.lang.String publishDetil;
	/**发布计划*/
	@Excel(name = "发布计划", width = 15)
    @ApiModelProperty(value = "发布计划")
    private java.lang.String publishPlan;
	/**影响分析与风险评估*/
	@Excel(name = "影响分析与风险评估", width = 15)
    @ApiModelProperty(value = "影响分析与风险评估")
    private java.lang.String publishAnalysis;
	/**附件*/
	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String file;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private java.lang.String deviceName;
	/**上线时间*/
	@Excel(name = "上线时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "上线时间")
    private java.util.Date onlineTime;
	/**ip地址*/
	@Excel(name = "ip地址", width = 15)
    @ApiModelProperty(value = "ip地址")
    private java.lang.String ip;
	/**管理员*/
	@Excel(name = "管理员", width = 15)
    @ApiModelProperty(value = "管理员")
    private java.lang.String administrators;
	/**基本配置*/
	@Excel(name = "基本配置", width = 15)
    @ApiModelProperty(value = "基本配置")
    private java.lang.String basicConfiguration;
	/**品牌型号*/
	@Excel(name = "品牌型号", width = 15)
    @ApiModelProperty(value = "品牌型号")
    private java.lang.String model;
	/**系统环境*/
	@Excel(name = "系统环境", width = 15)
    @ApiModelProperty(value = "系统环境")
    private java.lang.String systemEnvironment;
	/**安装位置*/
	@Excel(name = "安装位置", width = 15)
    @ApiModelProperty(value = "安装位置")
    private java.lang.String installationEnvironment;
	/**其他相关配置*/
	@Excel(name = "其他相关配置", width = 15)
    @ApiModelProperty(value = "其他相关配置")
    private java.lang.String otherConfig;
}
