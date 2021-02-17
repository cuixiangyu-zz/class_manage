package org.jeecg.modules.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.CollegeClass;
import org.jeecg.modules.mapper.CollegeClassMapper;
import org.jeecg.modules.service.ICollegeClassService;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学院班级表
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
@Service
public class CollegeClassServiceImpl extends ServiceImpl<CollegeClassMapper, CollegeClass> implements ICollegeClassService {

	@Autowired
	CollegeClassMapper collegeClassMapper;

	@Override
	public Result<?> addCollegeClass(CollegeClass collegeClass) {
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
		String code = collegeClassMapper.getNextCode(collegeClass.getPid());
		if(StringUtils.isEmpty(code)){
			code = "001";
		}
		if(ICollegeClassService.ROOT_PID_VALUE.equals(collegeClass.getPid())){
			if(code.length()==1){
				collegeClass.setCode("00"+code);
			}else if(code.length()==2){
				collegeClass.setCode("0"+code);
			}else if(code.length()==3){
				collegeClass.setCode(code);
			}
			collegeClass.setType("school");
		}else{
			CollegeClass collegeClass1 = baseMapper.selectById(collegeClass.getPid());
			if(collegeClass1.getType().equals("class")){
				return Result.error("上级不可以是班级!");
			}
			if(code.length()==1){
				collegeClass.setCode(collegeClass1.getCode()+"-00"+code);
			}else if(code.length()==2){
				collegeClass.setCode(collegeClass1.getCode()+"-0"+code);
			}else if(code.length()==3){
				collegeClass.setCode(collegeClass1.getCode()+"-"+code);
			}

			if(collegeClass1.getType().equals("school")){
				collegeClass.setType("institute");
			}else if(collegeClass1.getType().equals("institute")){
				collegeClass.setType("major");
			}else if(collegeClass1.getType().equals("major")){
				collegeClass.setType("class");
			}
		}
		baseMapper.insert(collegeClass);
		return Result.ok();
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
		collegeClassMapper.deleteById(id);
	}

	@Override
	public List<DictModel> getByCode(String code) {
		return collegeClassMapper.getByCode(code);
	}

	@Override
	public Result<?> getCollegeClassTreeData() {
		List<Map<String,Object>> data = collegeClassMapper.getRootData(ICollegeClassService.ROOT_PID_VALUE);
		List<Map<String,Object>> tree = treeData(data);
		return Result.ok(tree);
	}

	private List<Map<String,Object>> treeData(List<Map<String,Object>> parent){
		for (Map<String, Object> map : parent) {
			List<Map<String,Object>> data = collegeClassMapper.getChildDataByCode(map.get("key").toString());
			if(data!=null&&data.size()>0){
				treeData(data);
				map.put("children",data);
			}
		}
		return parent;
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
