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
        <a-form-item label="用户" v-has="'teacher:add'" v-if="title==='新增'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            ref="userSelect"
            placeholder="请选择用户"
            v-decorator="['baseInfoId']"
            :triggerChange="true"
            :dictOptions="userOptions">
          </j-search-select-tag>
        </a-form-item>
        <a-form-item label="试讲结果" v-has="'schedul:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['trialLecture']" placeholder="请输入试讲结果"></a-input>
        </a-form-item>
        <a-form-item label="月工资" v-has="'college_director:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['wages']" placeholder="请输入月工资"></a-input-number>
        </a-form-item>
        <a-form-item label="评价结果" v-has="'college_director:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['evaluation']" placeholder="请输入评价结果"></a-input>
        </a-form-item>
        <a-form-item label="上传文件" v-has="'teacher:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['files']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="任教科目" v-has="'teacher:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['subject' ]" :trigger-change="true"
                             dictCode="subject" placeholder="请选择任教科目"/>
        </a-form-item>
        <a-form-item label="学历" v-has="'teacher:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['education' ]" :trigger-change="true"
                             dictCode="education_type" placeholder="请选择学历"/>
        </a-form-item>
        <a-form-item label="工龄" v-has="'teacher:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['workingYears']" placeholder="请输入工龄"></a-input-number>
        </a-form-item>
        <a-form-item label="毕业学校类型" v-has="'teacher:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['graduateSchoolType' ]" :trigger-change="true"
                             dictCode="school_type" placeholder="请选择毕业学校"/>
        </a-form-item>
        <a-form-item label="工作状态" v-has="'college_director:add'" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['workStatus']" :trigger-change="true"
                             dictCode="work_status" placeholder="请选择工作状态"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JUpload from '@/components/jeecg/JUpload'

  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'


  export default {
    name: "TeacherInfoModal",
    components: { 
      JUpload,
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
          add: "/manage/teacherInfo/add",
          edit: "/manage/teacherInfo/edit",
        },
        userOptions: []
      }
    },
    created () {
      this.getUserList();
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
          this.form.setFieldsValue(pick(this.model,'baseInfoId','trialLecture','wages','evaluation','files','subject','education','workingYears','graduateSchoolType','workStatus'))
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
        this.form.setFieldsValue(pick(row,'baseInfoId','trialLecture','wages','evaluation','files','subject','education','workingYears','graduateSchoolType','workStatus'))
      },
      getUserList() {
        const that = this;
        var httpurl = '/manage/teacherInfo/getUserList'
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            that.userOptions = res.result
            //that.$refs.teacherSelect.setCurrentDictOptions(this.teacherOptions);
          } else {
            that.$message.warning(res.message);
          }
        })
      },
      
    }
  }
</script>