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
          <a-input v-decorator="['name', { rules: [{ required: true}] }]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['sex', { rules: [{ required: true}] }]" :trigger-change="true" dictCode="sex"
                             placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['age', { rules: [{ required: true}] }]" placeholder="请输入年龄"></a-input>
        </a-form-item>
        <a-form-item label="学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['studentCode',validatorRules.studentCode]" placeholder="请输入学号"></a-input>
        </a-form-item>
        <a-form-item label="入学年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['entranceYear', { rules: [{ required: true}] }]" :trigger-change="true"
                             dictCode="entrance_year" placeholder="请选择入学年份"/>
        </a-form-item>
        <a-form-item label="学院" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag @change="changeMajor" :triggerChange="true" v-decorator="['institute', { rules: [{ required: true}] }]"
                             placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'institute'"/>
          <!--          <j-search-select-tag v-decorator="['institute']" dict="" />-->
        </a-form-item>
        <a-form-item label="专业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            @change="changeClass"
            ref="majorSelect"
            placeholder="请选择专业"
            v-decorator="['major', { rules: [{ required: true}] }]"
            :triggerChange="true"
            :dictOptions="majorOptions">
          </j-search-select-tag>
          <!--          <j-dict-select-tag :triggerChange="true" v-decorator="['major']" placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'major',"/>-->
        </a-form-item>
        <a-form-item label="班级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            ref="classSelect"
            placeholder="请选择班级"
            v-decorator="['className', { rules: [{ required: true}] }]"
            :triggerChange="true"
            :dictOptions="classOptions">
          </j-search-select-tag>
<!--          <j-dict-select-tag :triggerChange="true" v-decorator="['className']" placeholder="请选择用户名称"
                             dictCode="college_class,name,code,type = 'class'"/>-->
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

import {httpAction} from '@/api/manage'
import pick from 'lodash.pick'
import {validateDuplicateValue} from '@/utils/util'
import JDictSelectTag from "@/components/dict/JDictSelectTag"
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'


export default {
  name: "StudentModal",
  components: {
    JDictSelectTag,
    JSearchSelectTag,
  },
  data() {
    return {
      form: this.$form.createForm(this),
      title: "操作",
      width: 800,
      visible: false,
      model: {},
      labelCol: {
        xs: {span: 24},
        sm: {span: 5},
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 16},
      },
      confirmLoading: false,
      validatorRules: {
        studentCode:{rules:[{ required: true, message: '请输入学号!'} ,{validator: this.checkStudentCode}]},
      },
      url: {
        add: "/manage/student/add",
        edit: "/manage/student/edit",
      },
      majorOptions: [],
      classOptions: [],
      result:[]
    }
  },
  created() {
  },
  methods: {
    add() {
      this.edit({});
    },
    edit(record) {
      this.form.resetFields();
      this.model = Object.assign({}, record);
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'name', 'sex', 'age', 'studentCode', 'entranceYear', 'institute', 'major', 'className', 'phone', 'imgUrl'))
      })
    },
    close() {
      this.$emit('close');
      this.visible = false;
    },
    handleOk() {
      const that = this;
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if (!this.model.id) {
            httpurl += this.url.add;
            method = 'post';
          } else {
            httpurl += this.url.edit;
            method = 'put';
          }
          let formData = Object.assign(this.model, values);
          console.log("表单提交数据", formData)
          httpAction(httpurl, formData, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message);
              that.$emit('ok');
            } else {
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
            that.close();
          })
        }

      })
    },
    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      this.form.setFieldsValue(pick(row, 'name', 'sex', 'age', 'studentCode', 'entranceYear', 'institute', 'major', 'className', 'phone', 'imgUrl'))
    },
    changeMajor(e) {
      this.form.setFieldsValue({major:'',className:''})
      if (e!==undefined&&e!== null && e !== '') {
        var httpurl = '/manage/collegeClass/getChildOption?code=' + e
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.majorOptions = res.result
            this.$refs.majorSelect.setCurrentDictOptions(this.majorOptions);
            console.log(this.majorOptions)
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },
    changeClass(e) {
      this.form.setFieldsValue({className:''})
      if (e!==undefined&&e!== null && e !== '') {
        var httpurl = '/manage/collegeClass/getChildOption?code=' + e
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            this.classOptions = res.result
            this.$refs.classSelect.setCurrentDictOptions(this.classOptions);
            console.log(this.classOptions)
          } else {
            that.$message.warning(res.message);
          }
        })
      }
    },
    getChildOption(code) {

    },
    checkStudentCode(rule,value,callback){
      let endTime = this.form.getFieldValue("weekEnd")
      if(!value){
        callback()
      }else{
        var httpurl = '/manage/student/checkStudentCode?studentCode=' + value
        httpAction(httpurl, null, 'get').then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("学号已存在")
          }
        })
      }
    },
  }
}
</script>