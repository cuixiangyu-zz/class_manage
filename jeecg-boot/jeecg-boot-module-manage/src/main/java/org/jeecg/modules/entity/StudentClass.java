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
 * @Description: 课程表
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Data
@TableName("student_class")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="student_class对象", description="课程表")
public class StudentClass implements Serializable {
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
	/**学号*/
	@Excel(name = "学号", width = 15)
    @ApiModelProperty(value = "学号")
    private String jwcAccount;
	/**周几*/
	@Excel(name = "周几", width = 15)
    @ApiModelProperty(value = "周几")
    private String weekDay;
	/**第几节课程*/
	@Excel(name = "第几节课程", width = 15)
    @ApiModelProperty(value = "第几节课程")
    private String section;
	/**课程名称*/
	@Excel(name = "课程名称", width = 15)
    @ApiModelProperty(value = "课程名称")
    private String subjectName;
	/**上课班级*/
	@Excel(name = "上课班级", width = 15)
    @ApiModelProperty(value = "上课班级")
    private String className;
	/**老师*/
	@Excel(name = "老师", width = 15)
    @ApiModelProperty(value = "老师")
    private String teacher;
	/**课程详细周数*/
	@Excel(name = "课程详细周数", width = 15)
    @ApiModelProperty(value = "课程详细周数")
    private String weekSeq;
	/**课程周范围*/
	@Excel(name = "课程周范围", width = 15)
    @ApiModelProperty(value = "课程周范围")
    private String weekStr;
	/**教室*/
	@Excel(name = "教室", width = 15)
    @ApiModelProperty(value = "教室")
    private String location;
	/**学年,学期*/
	@Excel(name = "学年,学期", width = 15)
    @ApiModelProperty(value = "学年,学期")
    private String xnxqh;
}
