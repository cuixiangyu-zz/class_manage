<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="人员分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['userType', validatorRules.userType]" :trigger-change="true" dictCode="creater_type" placeholder="请选择人员分类"/>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['company', validatorRules.company]" :trigger-change="true" dictCode="" placeholder="请选择单位"/>
        </a-form-item>
        <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['department']" placeholder="请输入部门"></a-input>
        </a-form-item>
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name']" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="vip" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['vip']" :trigger-change="true" dictCode="yn" placeholder="请选择vip"/>
        </a-form-item>
        <a-form-item label="房间号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['roomNo']" placeholder="请输入房间号"></a-input>
        </a-form-item>
        <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['phoneNum', validatorRules.phoneNum]" placeholder="请输入手机号"></a-input>
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tel']" placeholder="请输入电话"></a-input>
        </a-form-item>
        <a-form-item label="事件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['eventType', validatorRules.eventType]" :trigger-change="true" dictCode="event_work_type" placeholder="请选择事件类型"/>
        </a-form-item>
        <a-form-item label="事件标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['eventTitle', validatorRules.eventTitle]" placeholder="请输入事件标题"></a-input>
        </a-form-item>
        <a-form-item label="事件描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['eventDescription',{trigger:'input'}]"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['remark',{trigger:'input'}]"/>
        </a-form-item>
        <a-form-item label="事件来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['eventSource', validatorRules.eventSource]" :trigger-change="true" dictCode="event_work_source" placeholder="请选择事件来源"/>
        </a-form-item>
        <a-form-item label="事件性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['eventProperties', validatorRules.eventProperties]" :trigger-change="true" dictCode="event_work_properties" placeholder="请选择事件性质"/>
        </a-form-item>
        <a-form-item label="影响度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['affectLevel', validatorRules.affectLevel]" :trigger-change="true" dictCode="priority" placeholder="请选择影响度"/>
        </a-form-item>
        <a-form-item label="紧急度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['emergencyLevel', validatorRules.emergencyLevel]" :trigger-change="true" dictCode="priority" placeholder="请选择紧急度"/>
        </a-form-item>
        <a-form-item label="优先级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['priority', validatorRules.priority]" :trigger-change="true" dictCode="event_work_priority" placeholder="请选择优先级"/>
        </a-form-item>
        <a-form-item label="服务级别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['serviceLevel', validatorRules.serviceLevel]" :trigger-change="true" dictCode="service_level" placeholder="请选择服务级别"/>
        </a-form-item>
        <a-form-item label="解决时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['dealTime', validatorRules.dealTime]" placeholder="请输入解决时间" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="发生时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择发生时间" v-decorator="['startTime', validatorRules.startTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="响应时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['responseTime', validatorRules.responseTime]" placeholder="请输入响应时间" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="处理方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['treatmentMethod']" :trigger-change="true" dictCode="deal_type" placeholder="请选择处理方式"/>
        </a-form-item>
        <a-form-item label="解决时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择解决时间" v-decorator="['solutionTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="解决方案" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['solutions',{trigger:'input'}]"/>
        </a-form-item>
        <a-form-item label="问题附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['businessFile']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="解决附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['solutionsFile']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['status', validatorRules.status]" placeholder="请输入状态"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditor from '@/components/jeecg/JEditor'


  export default {
    name: "EventWorkOrderModal",
    components: { 
      JDate,
      JUpload,
      JDictSelectTag,
      JEditor,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          userType: {
            rules: [
              { required: true, message: '请输入人员分类!'},
            ]
          },
          company: {
            rules: [
              { required: true, message: '请输入单位!'},
            ]
          },
          phoneNum: {
            rules: [
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          eventType: {
            rules: [
              { required: true, message: '请输入事件类型!'},
            ]
          },
          eventTitle: {
            rules: [
              { required: true, message: '请输入事件标题!'},
            ]
          },
          eventDescription: {
            rules: [
              { required: true, message: '请输入事件描述!'},
            ]
          },
          eventSource: {
            rules: [
              { required: true, message: '请输入事件来源!'},
            ]
          },
          eventProperties: {
            rules: [
              { required: true, message: '请输入事件性质!'},
            ]
          },
          affectLevel: {
            rules: [
              { required: true, message: '请输入影响度!'},
            ]
          },
          emergencyLevel: {
            rules: [
              { required: true, message: '请输入紧急度!'},
            ]
          },
          priority: {
            rules: [
              { required: true, message: '请输入优先级!'},
            ]
          },
          serviceLevel: {
            rules: [
              { required: true, message: '请输入服务级别!'},
            ]
          },
          dealTime: {
            rules: [
              { required: true, message: '请输入解决时间!'},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
            ]
          },
          startTime: {
            rules: [
              { required: true, message: '请输入发生时间!'},
            ]
          },
          responseTime: {
            rules: [
              { required: true, message: '请输入响应时间!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入状态!'},
            ]
          },
        },
        url: {
          add: "/order/eventWorkOrder/add",
          edit: "/order/eventWorkOrder/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'userType','company','department','name','vip','roomNo','phoneNum','tel','eventType','eventTitle','eventDescription','remark','eventSource','eventProperties','affectLevel','emergencyLevel','priority','serviceLevel','dealTime','startTime','responseTime','treatmentMethod','solutionTime','solutions','businessFile','solutionsFile','status','dealer','closeTime'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'userType','company','department','name','vip','roomNo','phoneNum','tel','eventType','eventTitle','eventDescription','remark','eventSource','eventProperties','affectLevel','emergencyLevel','priority','serviceLevel','dealTime','startTime','responseTime','treatmentMethod','solutionTime','solutions','businessFile','solutionsFile','status','dealer','closeTime'))
      },

      
    }
  }
</script>