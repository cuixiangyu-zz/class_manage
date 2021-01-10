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

        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name']" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['sex']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['age']" placeholder="请输入年龄"></a-input>
        </a-form-item>
        <a-form-item label="学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['studentCode']" placeholder="请输入学号"></a-input>
        </a-form-item>
        <a-form-item label="入学年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['entranceYear']" :trigger-change="true" dictCode="	entrance_year" placeholder="请选择入学年份"/>
        </a-form-item>
        <a-form-item label="学院" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag  :triggerChange="true" v-decorator="['institute']" placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'institute'"/>
<!--          <j-search-select-tag v-decorator="['institute']" dict="" />-->
        </a-form-item>
        <a-form-item label="专业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag :triggerChange="true" v-decorator="['major']" placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'major'"/>
<!--          <j-search-select-tag v-decorator="['major']" dict="" />-->
        </a-form-item>
        <a-form-item label="班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag :triggerChange="true" v-decorator="['className']" placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'class'"/>
<!--          <j-search-select-tag v-decorator="['class']" dict="" />-->
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['phone']" placeholder="请输入电话"></a-input>
        </a-form-item>
        <a-form-item label="头像" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['imgUrl']" placeholder="请输入头像"></a-input>
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
    name: "StudentModal",
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
          add: "/manage/student/add",
          edit: "/manage/student/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','sex','age','studentCode','entranceYear','institute','major','className','phone','imgUrl'))
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
        this.form.setFieldsValue(pick(row,'name','sex','age','studentCode','entranceYear','institute','major','className','phone','imgUrl'))
      },

      
    }
  }
</script>