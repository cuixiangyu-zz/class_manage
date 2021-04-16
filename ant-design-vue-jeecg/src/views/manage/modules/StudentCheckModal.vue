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

        <a-form-item label="课程id" v-show="false" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['subjectId']" placeholder="请输入课程id"></a-input>
        </a-form-item>
        <a-form-item label="学生" :disable="disable" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            ref="student"
            placeholder="请选择学生"
            v-decorator="['studentId',{ rules: [{ required: true}] }]"
            :triggerChange="true"
            :dictOptions="studentOptions">
          </j-search-select-tag>
          <!--<a-input v-decorator="['studentId']" placeholder="请输入学生id"></a-input>-->
        </a-form-item>
        <a-form-item label="日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择日期" v-decorator="['checkDate']" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['type', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="check_type" placeholder="请选择类型"/>
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JDictSelectTag from "@comp/dict/JDictSelectTag";

  export default {
    name: "StudentCheckModal",
    components: { 
      JDate,
      JSearchSelectTag,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        subjectId:'',
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
          add: "/manage/studentCheck/add",
          edit: "/manage/studentCheck/edit",
        },
        studentOptions:[],
        disable:false
      }
    },
    created () {
    },
    methods: {
      add (id) {
        this.subjectId = id;
        this.edit({});
        this.disable = false
      },
      edit (record) {
        console.log(record);
        if(record!==undefined&&record!==null&&(this.subjectId===null||this.subjectId==='')){
          this.subjectId = record.subjectId;
          this.disable = true
        }
        if(this.subjectId!==null&&this.subjectId!==''){
          this.changeStudent(this.subjectId);
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'subjectId','studentId','checkDate','type'))
        });

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
            if(this.subjectId!==null&&this.subjectId!==''){
              formData.subjectId = this.subjectId
            }
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
        this.form.setFieldsValue(pick(row,'subjectId','studentId','checkDate','type'))
      },

      changeStudent(subjectId) {
        this.studentOptions = []

        if (subjectId!==undefined&&subjectId!== null && subjectId !== '') {
          var httpurl = '/manage/student/queryBySubjectId?subjectId=' + subjectId
          httpAction(httpurl, null, 'get').then((res) => {
            if (res.success) {
              this.studentOptions = res.result
              this.$refs.student.setCurrentDictOptions(this.studentOptions);
            } else {
              that.$message.warning(res.message);
            }
          })
        }
      },
    }
  }
</script>