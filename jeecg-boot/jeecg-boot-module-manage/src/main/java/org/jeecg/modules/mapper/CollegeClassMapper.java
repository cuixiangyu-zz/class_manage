package org.jeecg.modules.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.entity.CollegeClass;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学院班级表
 * @Author: jeecg-boot
 * @Date:   2020-12-30
 * @Version: V1.0
 */
public interface CollegeClassMapper extends BaseMapper<CollegeClass> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

	@Select({
			"select child.name as 'text',child.code as 'value' from college_class child left join college_class par on child.pid = par.id where par.code=#{code} "
	})
	List<DictModel> getByCode(String code);

	@Select({
			" SELECT RIGHT(max(code),3)+1 FROM college_class where pid = #{pid}"
	})
	String getNextCode(String pid);

	@Select({
			"select child.name as 'title',child.code as 'key' from college_class child " +
					"left join college_class par on child.pid = par.id where par.code=#{code} "
	})
	List<Map<String, Object>> getChildDataByCode(String code);

	@Select({
			"select name as 'title',code as 'key' from college_class  " +
					" where pid=#{code} "
	})
	List<Map<String, Object>> getRootData(String code);

	@Select({
			"<script>",
			"select",
			"code",
			"from college_class",
			"where code in",
			"<foreach collection='array' item='code' open='(' separator=',' close=')'>",
			"#{code}",
			"</foreach>",
			" and type = 'class' ",
			"</script>"
	})
	List<String> checkClasses(String[] codes);
}
