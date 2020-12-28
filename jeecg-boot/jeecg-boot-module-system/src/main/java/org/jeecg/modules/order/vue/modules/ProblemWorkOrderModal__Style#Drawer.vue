<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="问题归属" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['problemAttribution', validatorRules.problemAttribution]" :trigger-change="true" dictCode="creater_type" placeholder="请选择问题归属"/>
        </a-form-item>
        <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入联系人"></a-input>
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['tel', validatorRules.tel]" placeholder="请输入电话" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="问题标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['problemTitle', validatorRules.problemTitle]" placeholder="请输入问题标题"></a-input>
        </a-form-item>
        <a-form-item label="问题描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['problemDescription']" placeholder="请输入问题描述"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['remark']" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['files']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="问题性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['problemProperties', validatorRules.problemProperties]" :trigger-change="true" dictCode="problem_properties" placeholder="请选择问题性质"/>
        </a-form-item>
        <a-form-item label="问题类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['problemType', validatorRules.problemType]" :trigger-change="true" dictCode="problem_type" placeholder="请选择问题类别"/>
        </a-form-item>
        <a-form-item label="影响度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['affectLevel', validatorRules.affectLevel]" :trigger-change="true" dictCode="priority" placeholder="请选择影响度"/>
        </a-form-item>
        <a-form-item label="紧急度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['emergencyLevel', validatorRules.emergencyLevel]" :trigger-change="true" dictCode="priority" placeholder="请选择紧急度"/>
        </a-form-item>
        <a-form-item label="优先级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['priority', validatorRules.priority]" :trigger-change="true" dictCode="priority" placeholder="请选择优先级"/>
        </a-form-item>
        <a-form-item label="重复问题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['sameProblem', validatorRules.sameProblem]" :trigger-change="true" dictCode="yn" placeholder="请选择重复问题"/>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['status', validatorRules.status]" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="解决人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dealer']" placeholder="请输入解决人"></a-input>
        </a-form-item>
        <a-form-item label="解决时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择解决时间" v-decorator="['closeTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "ProblemWorkOrderModal",
    components: { 
      JDate,
      JUpload,
      JDictSelectTag,
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
          problemAttribution: {
            rules: [
              { required: true, message: '请输入问题归属!'},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入联系人!'},
            ]
          },
          tel: {
            rules: [
              { required: true, message: '请输入电话!'},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          problemTitle: {
            rules: [
              { required: true, message: '请输入问题标题!'},
            ]
          },
          problemProperties: {
            rules: [
              { required: true, message: '请输入问题性质!'},
            ]
          },
          problemType: {
            rules: [
              { required: true, message: '请输入问题类别!'},
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
          sameProblem: {
            rules: [
              { required: true, message: '请输入重复问题!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入状态!'},
            ]
          },
        },
        url: {
          add: "/order/problemWorkOrder/add",
          edit: "/order/problemWorkOrder/edit",
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
          this.form.setFieldsValue(pick(this.model,'problemAttribution','name','tel','problemTitle','problemDescription','remark','files','problemProperties','problemType','affectLevel','emergencyLevel','priority','sameProblem','status','dealer','closeTime'))
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
        this.form.setFieldsValue(pick(row,'problemAttribution','name','tel','problemTitle','problemDescription','remark','files','problemProperties','problemType','affectLevel','emergencyLevel','priority','sameProblem','status','dealer','closeTime'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>