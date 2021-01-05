package com.classManage.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.classManage.module.entity.CollegeClass;
import org.jeecg.common.exception.JeecgBootException;

/**
 * @Description: 学院班级表
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
public interface ICollegeClassService extends IService<CollegeClass> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addCollegeClass(CollegeClass collegeClass);
	
	/**修改节点*/
	void updateCollegeClass(CollegeClass collegeClass) throws JeecgBootException;
	
	/**删除节点*/
	void deleteCollegeClass(String id) throws JeecgBootException;

}
