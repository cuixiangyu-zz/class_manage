package com.classManage.module.service.impl;

import com.classManage.module.entity.CollegeClass;
import com.classManage.module.mapper.CollegeClassMapper;
import com.classManage.module.service.ICollegeClassService;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学院班级表
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Service
public class CollegeClassServiceImpl extends ServiceImpl<CollegeClassMapper, CollegeClass> implements ICollegeClassService {

	@Override
	public void addCollegeClass(CollegeClass collegeClass) {
		if(oConvertUtils.isEmpty(collegeClass.getPid())){
			collegeClass.setPid(ICollegeClassService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			CollegeClass parent = baseMapper.selectById(collegeClass.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(collegeClass);
	}
	
	@Override
	public void updateCollegeClass(CollegeClass collegeClass) {
		CollegeClass entity = this.getById(collegeClass.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = collegeClass.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				collegeClass.setPid(ICollegeClassService.ROOT_PID_VALUE);
			}
			if(!ICollegeClassService.ROOT_PID_VALUE.equals(collegeClass.getPid())) {
				baseMapper.updateTreeNodeStatus(collegeClass.getPid(), ICollegeClassService.HASCHILD);
			}
		}
		baseMapper.updateById(collegeClass);
	}
	
	@Override
	public void deleteCollegeClass(String id) throws JeecgBootException {
		CollegeClass collegeClass = this.getById(id);
		if(collegeClass==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(collegeClass.getPid());
		baseMapper.deleteById(id);
	}
	
	
	
	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!ICollegeClassService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<CollegeClass>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, ICollegeClassService.NOCHILD);
			}
		}
	}

}
