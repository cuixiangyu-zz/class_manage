package org.jeecg.modules.entity;

import java.io.Serializable;
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

/**
 * @Description: 学院班级表
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Data
@TableName("college_class")
@ApiModel(value="college_class对象", description="学院班级表")
public class CollegeClass implements Serializable {
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
	/**父级节点*/
	@Excel(name = "父级节点", width = 15)
	@ApiModelProperty(value = "父级节点")
	private String pid;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
	@ApiModelProperty(value = "是否有子节点")
	private String hasChild;
	/**名字*/
	@Excel(name = "名字", width = 15)
	@ApiModelProperty(value = "名字")
	private String name;
	/**编码*/
	@Excel(name = "编码", width = 15)
	@ApiModelProperty(value = "编码")
	private String code;
	/**类型*/
	@Excel(name = "类型", width = 15, dicCode = "class_type")
	@Dict(dicCode = "class_type")
	@ApiModelProperty(value = "类型")
	private String type;
	/**是否删除*/
	@Excel(name = "是否删除", width = 15)
	@ApiModelProperty(value = "是否删除")
	private String isDel;
}
