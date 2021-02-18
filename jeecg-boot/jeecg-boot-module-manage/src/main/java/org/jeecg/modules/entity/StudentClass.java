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

	/**周几*/
	@Excel(name = "周几", width = 15)
    @ApiModelProperty(value = "周几")
    @Dict(dicCode = "week_num")
    private String weekDay;
	/**第几节课程*/
	@Excel(name = "第几节课程", width = 15)
    @ApiModelProperty(value = "第几节课程")
    @Dict(dicCode = "sections")
    private String section;
	/**课程名称*/
	@Excel(name = "课程名称", width = 15)
    @ApiModelProperty(value = "课程名称")
    private String subjectName;

	/**老师*/
	@Excel(name = "老师", width = 15)
    @ApiModelProperty(value = "老师")
    @Dict(dicCode = "id",dictTable="sys_user",dicText="realname")
    private String teacher;
	/**课程详细周数*/
	@Excel(name = "课程详细周数", width = 15)
    @ApiModelProperty(value = "课程详细周数")
    private String weekSeq;
	/**课程周范围*/
	@Excel(name = "课程周范围", width = 15)
    @ApiModelProperty(value = "课程周范围")
    private String weekStart;

    /**课程周范围*/
    @Excel(name = "课程周范围", width = 15)
    @ApiModelProperty(value = "课程周范围")
    private String weekEnd;

	/**教室*/
	@Excel(name = "教室", width = 15)
    @ApiModelProperty(value = "教室")
    private String location;
	/**学年,学期*/
	@Excel(name = "学年", width = 15)
    @ApiModelProperty(value = "学年")
    @Dict(dicCode = "entrance_year")
    private String xn;

    /**学年,学期*/
    @Excel(name = "学期", width = 15)
    @ApiModelProperty(value = "学期")
    @Dict(dicCode = "xq")
    private String xq;
    /**学院*/
    @Excel(name = "学院", width = 15)
    @ApiModelProperty(value = "学院")
    @Dict(dicCode = "code",dictTable="college_class",dicText="name")
    private String institute;
    /**专业*/
    @Excel(name = "专业", width = 15)
    @ApiModelProperty(value = "专业")
    @Dict(dicCode = "code",dictTable="college_class",dicText="name")
    private String major;
    /**班级*/
    @Excel(name = "班级", width = 15)
    @ApiModelProperty(value = "班级")
    @Dict(dicCode = "code",dictTable="college_class",dicText="name")
    private String className;
}
