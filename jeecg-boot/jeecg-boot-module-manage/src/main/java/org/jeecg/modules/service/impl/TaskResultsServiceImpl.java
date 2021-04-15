package org.jeecg.modules.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.entity.GradeRate;
import org.jeecg.modules.entity.TaskResults;
import org.jeecg.modules.mapper.StudentCheckMapper;
import org.jeecg.modules.mapper.StudentClassMapper;
import org.jeecg.modules.mapper.TaskResultsMapper;
import org.jeecg.modules.service.IGradeRateService;
import org.jeecg.modules.service.ITaskResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 作业成绩
 * @Author: jeecg-boot
 * @Date:   2021-04-14
 * @Version: V1.0
 */
@Service
public class TaskResultsServiceImpl extends ServiceImpl<TaskResultsMapper, TaskResults> implements ITaskResultsService {

    @Autowired
    TaskResultsMapper taskResultsMapper;

    @Autowired
    StudentClassMapper studentClassMapper;

    @Autowired
    StudentCheckMapper studentCheckMapper;

    @Autowired
    IGradeRateService gradeRateService;

    /**
     * 更新成绩
     * @param taskResults   成绩
     * @return  成功
     */
    @Override
    public Result updateTask(TaskResults taskResults){
        if(taskResults==null|| StringUtils.isEmpty(taskResults.getTaskId())||StringUtils.isEmpty(taskResults.getStudentId())){
            return Result.error("信息不全");
        }
        TaskResults oldResult = taskResultsMapper.getByTaskAndStudent(taskResults.getTaskId(), taskResults.getStudentId());
        if(oldResult==null){
            this.save(taskResults);
        }else{
            oldResult.setResults(taskResults.getResults());
            this.updateById(oldResult);
        }
        return Result.ok();
    }

    @Override
    public List<Map<String,Object>> calculateTask(String studentId, String xq, String xn) {

        List<Map<String,Object>> result = new ArrayList<>();
        //获取课程列表
        List<Map<String,Object>> classList = studentClassMapper.getClassListByStudent(studentId, xq, xn);
        if(classList==null||classList.isEmpty()){
            return null;
        }
        for (Map<String, Object> classMap : classList) {
            //作业成绩
            Double taskGrade = taskResultsMapper.calculateGrade(studentId,classMap.get("id").toString());
            classMap.put("taskGrade",taskGrade==null?100d:taskGrade);

            //考勤成绩
            Double checkGrade = studentCheckMapper.calculateGrade(studentId,classMap.get("id").toString());
            if(checkGrade==null){
                classMap.put("checkGrade",100d);
            }else if(checkGrade>=0){
                classMap.put("checkGrade",0d);
            }else{
                classMap.put("checkGrade",100-checkGrade);
            }

            //期中成绩
            Double midGrade = taskResultsMapper.getByTaskType(studentId,classMap.get("id").toString(),"mid_term");
            classMap.put("midGrade",midGrade==null?0d:midGrade);

            //期末成绩
            Double finalGrade = taskResultsMapper.getByTaskType(studentId,classMap.get("id").toString(),"final_term");
            classMap.put("finalGrade",finalGrade==null?0d:finalGrade);

            GradeRate gradeRate = gradeRateService.getBySubjectId(classMap.get("id").toString());

            Double totle = 0d;

            totle = (taskGrade*gradeRate.getHomeworkRate() + checkGrade*gradeRate.getCheckRate()
                    + midGrade*gradeRate.getMidTermGradeRate() + finalGrade*gradeRate.getFinalTermGradeRate())/100;
            classMap.put("totle",totle);

            result.add(classMap);
        }
        return result;
    }
}
