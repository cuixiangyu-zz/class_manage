package org.jeecg.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.CollegeClass;
import org.jeecg.common.exception.JeecgBootException;

import java.util.List;

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
	Result<?> addCollegeClass(CollegeClass collegeClass);
	
	/**修改节点*/
	void updateCollegeClass(CollegeClass collegeClass) throws JeecgBootException;
	
	/**删除节点*/
	void deleteCollegeClass(String id) throws JeecgBootException;

    List<DictModel> getByCode(String code);

	Result<?> getCollegeClassTreeData();
}
