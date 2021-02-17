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

        <a-form-item label="学生" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            placeholder="请做出你的选择"
            v-decorator="['studentCode']"
            dict="student,name,id"
            :async="true">
          </j-search-select-tag>
<!--          <a-input v-decorator="['studentCode']" placeholder="请输入学生"></a-input>-->
        </a-form-item>
        <a-form-item label="表彰名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['awardName']" placeholder="请输入表彰名"></a-input>
        </a-form-item>
        <a-form-item label="表彰原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['awardReason']" placeholder="请输入表彰原因"></a-input>
        </a-form-item>
        <a-form-item label="学年" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['xnxq']" :trigger-change="true"
                             dictCode="	entrance_year" placeholder="请选择学年"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "StudentAwardModal",
    components: { JSearchSelectTag
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
          add: "/manage/studentAward/add",
          edit: "/manage/studentAward/edit",
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
          this.form.setFieldsValue(pick(this.model,'studentCode','awardName','awardReason','xnxq'))
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
        this.form.setFieldsValue(pick(row,'studentCode','awardName','awardReason','xnxq'))
      },

      
    }
  }
</script>