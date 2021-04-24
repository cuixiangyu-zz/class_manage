package org.jeecg.modules.entity;

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
 * @Description: 教师信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
@Data
@TableName("teacher_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="teacher_info对象", description="教师信息")
public class TeacherInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**基础信息表id*/
	@Excel(name = "基础信息表id", width = 15)
    @ApiModelProperty(value = "基础信息表id")
    private String baseInfoId;
	/**试讲结果*/
	@Excel(name = "试讲结果", width = 15)
    @ApiModelProperty(value = "试讲结果")
    private String trialLecture;
	/**月工资*/
	@Excel(name = "月工资", width = 15)
    @ApiModelProperty(value = "月工资")
    private String wages;
	/**评价结果*/
	@Excel(name = "评价结果", width = 15)
    @ApiModelProperty(value = "评价结果")
    private String evaluation;
	/**上传文件:协议书,学历证明*/
	@Excel(name = "上传文件:协议书,学历证明", width = 15)
    @ApiModelProperty(value = "上传文件:协议书,学历证明")
    private String files;
	/**任教科目*/
	@Excel(name = "任教科目", width = 15)
    @ApiModelProperty(value = "任教科目")
    @Dict(dicCode = "subject")
    private String subject;
	/**学历*/
	@Excel(name = "学历", width = 15)
    @ApiModelProperty(value = "学历")
    @Dict(dicCode = "education_type")
    private String education;
	/**工龄*/
	@Excel(name = "工龄", width = 15)
    @ApiModelProperty(value = "工龄")
    private String workingYears;
	/**毕业学校类型:985,211,一本*/
	@Excel(name = "毕业学校类型:985,211,一本", width = 15)
    @ApiModelProperty(value = "毕业学校类型:985,211,一本")
    @Dict(dicCode = "school_type")
    private String graduateSchoolType;
	/**工作状态:已聘任,未聘任*/
	@Excel(name = "工作状态:已聘任,未聘任", width = 15)
    @ApiModelProperty(value = "工作状态:已聘任,未聘任")
    @Dict(dicCode = "work_status")
    private String workStatus;
}
