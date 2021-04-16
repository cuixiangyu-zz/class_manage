<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学号">
              <a-input placeholder="请输入学号" v-model="queryParam.studentCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学院">
              <j-dict-select-tag @change="changeMajor" v-model="queryParam.institute"
                                 placeholder="请选择学院" dictCode="college_class,name,code,type = 'institute'"/>
            </a-form-item>
          </a-col>
<!--          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="学院">
                <j-dict-select-tag @change="changeMajor" v-model="queryParam.institute"
                                   placeholder="请选择用户名称" dictCode="college_class,name,code,type = 'institute'"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="专业">
                <j-search-select-tag
                  @change="changeClass"
                  ref="majorSelect"
                  placeholder="请选择专业"
                  v-model="queryParam.major"
                  :dictOptions="majorOptions">
                </j-search-select-tag>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="班级">
                <j-search-select-tag
                  ref="classSelect"
                  placeholder="请选择班级"
                  v-model="queryParam.className"
                  :dictOptions="classOptions">
                </j-search-select-tag>
              </a-form-item>
            </a-col>
          </template>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
<!--              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('学生表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="showModal(record)">查看课程表</a>
          <a-divider type="vertical" />
          <a @click="showGrade(record)">查看成绩单</a>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <student-modal ref="modalForm" @ok="modalFormOk"></student-modal>

    <!--课程表-->
    <a-modal
      title="课程表"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
      width="800px"
    >
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchClasses">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="周">
                <a-week-picker v-model="classesQueryParam.week"  placeholder="请输入课程开始周" format="YYYY-ww"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="学年">
                <j-dict-select-tag type="list" v-model="classesQueryParam.xn"  placeholder="请输入学年"
                                   dictCode="entrance_year"/>
              </a-form-item>
            </a-col><a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学期">
              <j-dict-select-tag type="list" v-model="classesQueryParam.xq"  placeholder="请输入学期"
                                 dictCode="xq"/>
            </a-form-item>
          </a-col>

            <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchClasses" icon="search">查询</a-button>
<!--              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
            </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="classesColumns"
        :dataSource="classesDataSource"
        :loading="classesLoading"
        pagination="false"
        class="j-table-force-nowrap"
        @change="handleTableChange">

      </a-table>
    </a-modal>


    <!--成绩单-->
    <a-modal
      title="成绩单"
      :visible="gradeVisible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
      width="800px"
    >
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchGrade">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="学年">
                <j-dict-select-tag type="list" v-model="gradeQueryParam.xn"  placeholder="请输入学年"
                                   dictCode="entrance_year"/>
              </a-form-item>
            </a-col><a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学期">
              <j-dict-select-tag type="list" v-model="gradeQueryParam.xq"  placeholder="请输入学期"
                                 dictCode="xq"/>
            </a-form-item>
          </a-col>

            <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchGrade" icon="search">查询</a-button>
              <a-button type="primary" @click="calculateGrade" icon="search">重新计算成绩</a-button>
            </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="gradeTable"
        size="middle"
        bordered
        rowKey="id"
        :columns="gradeColumns"
        :dataSource="gradeDataSource"
        :loading="gradeLoading"
        pagination="false"
        class="j-table-force-nowrap"
        @change="handleTableChange">

      </a-table>
    </a-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import StudentModal from './modules/StudentModal'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {httpAction} from "@api/manage";
  import moment from "moment";

  export default {
    name: "StudentList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JSearchSelectTag,
      StudentModal
    },
    data () {
      return {
        description: '学生表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'姓名',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'性别',
            align:"center",
            dataIndex: 'sex_dictText'
          },
          {
            title:'年龄',
            align:"center",
            dataIndex: 'age'
          },
          {
            title:'学号',
            align:"center",
            dataIndex: 'studentCode'
          },
          {
            title:'入学年份',
            align:"center",
            dataIndex: 'entranceYear_dictText'
          },
          {
            title:'学院',
            align:"center",
            dataIndex: 'institute_dictText'
          },
          {
            title:'专业',
            align:"center",
            dataIndex: 'major_dictText'
          },
          {
            title:'班级',
            align:"center",
            dataIndex: 'className_dictText'
          },
          {
            title:'电话',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'头像',
            align:"center",
            dataIndex: 'imgUrl'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        classesColumns: [
          {
            title: '时间',
            align: "center",
            dataIndex: 'section'
          },
          {
            title: '星期一',
            align: "center",
            dataIndex: '星期一'
          },
          {
            title: '星期二',
            align: "center",
            dataIndex: '星期二'
          },
          {
            title: '星期三',
            align: "center",
            dataIndex: '星期三'
          },
          {
            title: '星期四',
            align: "center",
            dataIndex: '星期四'
          },
          {
            title: '星期五',
            align: "center",
            dataIndex: '星期五'
          },
          {
            title: '星期六',
            align: "center",
            dataIndex: '星期六'
          },
          {
            title: '星期日',
            align: "center",
            dataIndex: '星期日'
          },
        ],
        classesLoading:false,
        classesDataSource:[],

        gradeColumns: [
          {
            title: '课程',
            align: "center",
            dataIndex: 'subjectName',
          },
          {
            title: '作业成绩',
            align: "center",
            dataIndex: 'homeworkGrade'
          },
          {
            title: '考勤成绩',
            align: "center",
            dataIndex: 'checkGrade'
          },
          {
            title: '期中成绩',
            align: "center",
            dataIndex: 'midTermGrade'
          },
          {
            title: '期末成绩',
            align: "center",
            dataIndex: 'finalTermGrade'
          },
          {
            title: '总成绩',
            align: "center",
            dataIndex: 'totleGrade'
          },
        ],
        gradeLoading:false,
        gradeDataSource:[],

        visible: false,
        gradeVisible: false,
        confirmLoading: false,
        url: {
          list: "/manage/student/list",
          delete: "/manage/student/delete",
          deleteBatch: "/manage/student/deleteBatch",
          exportXlsUrl: "/manage/student/exportXls",
          importExcelUrl: "manage/student/importExcel",
          getClasses: "/manage/studentClass/getClasses",
          getGrade: "/manage/studentGrade/getGradeList",
          calculateGrade: "/manage/studentGrade/calculateGrade"
        },
        dictOptions:{},
        majorOptions: [],
        classOptions: [],
        classesQueryParam:{
          xn:'2020',
          xq:'up',
          week:null,
          studentName:''
        },
        gradeQueryParam:{
          xn:'2020',
          xq:'up',
          studentId:''
        }
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    created() {
      //this.classesQueryParam.week = moment(new Date())
    },
    methods: {
      moment,
      initDictConfig(){
      },
      changeMajor(e) {
        console.log('aaaaaaa')
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
      showModal(record) {
        this.visible = true;
        console.log(record)
        this.classesQueryParam.studentName = record.name
        this.searchClasses();
      },
      showGrade(record) {
        this.gradeVisible = true;
        console.log(record);
        this.gradeQueryParam.studentId = record.id;
        this.searchGrade();
      },
      handleOk(e) {
        this.ModalText = 'The modal will be closed after two seconds';
        this.confirmLoading = true;
        setTimeout(() => {
          this.visible = false;
          this.gradeVisible = false;
          this.confirmLoading = false;
        }, 2000);
      },
      handleCancel(e) {
        console.log('Clicked cancel button');
        this.visible = false;
        this.gradeVisible = false;
      },
      searchClasses() {
        //this.visible = true;
        let week = this.classesQueryParam.week
        let param = this.classesQueryParam
        if(this.classesQueryParam.week){
          param.week = moment(new Date(week)).format('YYYY-ww')
        }else{
          param.week = moment().format('YYYY-ww')
        }
        let url = this.url.getClasses
        url+='?xn='+param.xn+'&xq='+param.xq+'&week='+param.week+'&studentName='+param.studentName
        httpAction(url, param, 'get').then((res) => {
          if (res.success) {
            this.classesDataSource = res.result
          } else {
            this.$message.warning(res.message);
          }
        })
      },

      searchGrade() {
        let param = this.gradeQueryParam;

        let url = this.url.getGrade;
        url+='?xn='+param.xn+'&xq='+param.xq+'&studentId='+param.studentId;
        httpAction(url, param, 'get').then((res) => {
          if (res.success) {
            this.gradeDataSource = res.result
          } else {
            this.$message.warning(res.message);
          }
        })
      },

      calculateGrade() {
        let param = this.gradeQueryParam;

        let url = this.url.calculateGrade;
        url+='?xn='+param.xn+'&xq='+param.xq+'&studentId='+param.studentId;
        httpAction(url, param, 'get').then((res) => {
          if (res.success) {
            this.gradeDataSource = res.result
          } else {
            this.$message.warning(res.message);
          }
        })
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>