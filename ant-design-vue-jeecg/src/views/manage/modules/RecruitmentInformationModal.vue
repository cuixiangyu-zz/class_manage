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
          <j-dict-select-tag type="list" v-decorator="['subject', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="subject" placeholder="请选择课程"/>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['age']" placeholder="请输入年龄"></a-input-number>
        </a-form-item>
        <a-form-item label="学历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['education', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="education_type" placeholder="请选择学历"/>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['sex', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="工龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['workingYears']" placeholder="请输入工龄"></a-input-number>
        </a-form-item>
        <a-form-item label="毕业学校类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['graduateSchoolType', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="school_type" placeholder="请选择毕业学校类型"/>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['type', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="employment_type" placeholder="请选择类型"/>
        </a-form-item>
        <a-form-item label="招聘数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['count']" placeholder="请输入招聘数量"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'


  export default {
    name: "RecruitmentInformationModal",
    components: {
      JDictSelectTag,
      JSearchSelectTag,
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
          add: "/manage/recruitmentInformation/add",
          edit: "/manage/recruitmentInformation/edit",
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
          this.form.setFieldsValue(pick(this.model,'subject','age','education','sex','workingYears','graduateSchoolType','type','count'))
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
        this.form.setFieldsValue(pick(row,'subject','age','education','sex','workingYears','graduateSchoolType','type','count'))
      },

      
    }
  }
</script>