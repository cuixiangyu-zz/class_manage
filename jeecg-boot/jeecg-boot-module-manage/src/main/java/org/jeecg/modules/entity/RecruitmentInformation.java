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
 * @Description: 招聘信息
 * @Author: jeecg-boot
 * @Date:   2021-04-21
 * @Version: V1.0
 */
@Data
@TableName("recruitment_information")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="recruitment_information对象", description="招聘信息")
public class RecruitmentInformation implements Serializable {
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
	/**课程*/
	@Excel(name = "课程", width = 15)
    @ApiModelProperty(value = "课程")
    @Dict(dicCode = "subject")
    private String subject;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
    private String age;
	/**学历*/
	@Excel(name = "学历", width = 15)
    @ApiModelProperty(value = "学历")
    @Dict(dicCode = "education_type")
    private String education;
	/**性别*/
	@Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别")
    @Dict(dicCode = "sex")
    private String sex;
	/**工龄*/
	@Excel(name = "工龄", width = 15)
    @ApiModelProperty(value = "工龄")
    private String workingYears;
	/**毕业学校类型:985,211,一本*/
	@Excel(name = "毕业学校类型:985,211,一本", width = 15)
    @ApiModelProperty(value = "毕业学校类型:985,211,一本")
    @Dict(dicCode = "school_type")
    private String graduateSchoolType;
	/**类型:派遣制,外聘制*/
	@Excel(name = "类型:派遣制,外聘制", width = 15)
    @ApiModelProperty(value = "类型:派遣制,外聘制")
    @Dict(dicCode = "employment_type")
    private String type;
	/**招聘数量*/
	@Excel(name = "招聘数量", width = 15)
    @ApiModelProperty(value = "招聘数量")
    private String count;
}
