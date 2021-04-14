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

        <a-form-item label="课程" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subjectId']" placeholder="请输入课程"></a-input>
        </a-form-item>
        <a-form-item label="作业名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['taskName']" placeholder="请输入作业名"></a-input>
        </a-form-item>
        <a-form-item label="作业详情" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['taskDetail']" placeholder="请输入作业详情"></a-input>
        </a-form-item>
        <a-form-item label="作业类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['taskType']" placeholder="请输入作业类型"></a-input>
        </a-form-item>
        <a-form-item label="班级id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['classId']" placeholder="请输入班级id"></a-input>
        </a-form-item>
        <a-form-item label="截止时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择截止时间" v-decorator="['endTime']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['file']" :trigger-change="true"></j-upload>
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


  export default {
    name: "StudentTaskModal",
    components: { 
      JDate,
      JUpload,
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
        },
        url: {
          add: "/manage/studentTask/add",
          edit: "/manage/studentTask/edit",
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
          this.form.setFieldsValue(pick(this.model,'subjectId','taskName','taskDetail','taskType','classId','endTime','file'))
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
        this.form.setFieldsValue(pick(row,'subjectId','taskName','taskDetail','taskType','classId','endTime','file'))
      },

      
    }
  }
</script>