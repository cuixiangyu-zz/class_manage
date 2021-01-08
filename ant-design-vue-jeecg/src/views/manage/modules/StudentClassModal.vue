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

        <a-form-item label="学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['jwcAccount']" placeholder="请输入学号"></a-input>
        </a-form-item>
        <a-form-item label="周几" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['weekDay']" placeholder="请输入周几"></a-input>
        </a-form-item>
        <a-form-item label="第几节课程" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['section']" placeholder="请输入第几节课程"></a-input>
        </a-form-item>
        <a-form-item label="课程名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subjectName']" placeholder="请输入课程名称"></a-input>
        </a-form-item>
        <a-form-item label="上课班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['className']" placeholder="请输入上课班级"></a-input>
        </a-form-item>
        <a-form-item label="老师" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['teacher']" placeholder="请输入老师"></a-input>
        </a-form-item>
        <a-form-item label="课程详细周数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['weekSeq']" placeholder="请输入课程详细周数"></a-input>
        </a-form-item>
        <a-form-item label="课程周范围" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['weekStr']" placeholder="请输入课程周范围"></a-input>
        </a-form-item>
        <a-form-item label="教室" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['location']" placeholder="请输入教室"></a-input>
        </a-form-item>
        <a-form-item label="学年,学期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['xnxqh']" placeholder="请输入学年,学期"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'


  export default {
    name: "StudentClassModal",
    components: { 
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
          add: "/manage/studentClass/add",
          edit: "/manage/studentClass/edit",
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
          this.form.setFieldsValue(pick(this.model,'jwcAccount','weekDay','section','subjectName','className','teacher','weekSeq','weekStr','location','xnxqh'))
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
        this.form.setFieldsValue(pick(row,'jwcAccount','weekDay','section','subjectName','className','teacher','weekSeq','weekStr','location','xnxqh'))
      },

      
    }
  }
</script>