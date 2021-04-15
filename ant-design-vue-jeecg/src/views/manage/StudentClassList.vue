<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="课程名称">
              <a-input placeholder="请输入课程名称" v-model="queryParam.subjectName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="老师">
              <j-search-select-tag
                placeholder="请选择教师"
                v-model="queryParam.teacher"
                dict="sys_user,realname,id"
                :async="true">
              </j-search-select-tag>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="学年">
              <j-dict-select-tag type="list" v-model="queryParam.xn"  placeholder="请输入学年"
                                 dictCode="entrance_year"/>
            </a-form-item>
          </a-col>
                    <template v-if="toggleSearchStatus">
                      <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="学院">
                          <j-dict-select-tag v-model="queryParam.institute"
                                             placeholder="请选择学院" dictCode="college_class,name,code,type = 'institute'"/>
                        </a-form-item>
                      </a-col>
                    </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                            <a @click="handleToggleSearch" style="margin-left: 8px">
                              {{ toggleSearchStatus ? '收起' : '展开' }}
                              <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
                            </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('课程表')">导出</a-button>
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
          <a @click="addTask(record)">添加作业</a>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                <a @click="addRate(record)">添加作业成绩比例</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <studentClass-modal ref="modalForm" @ok="modalFormOk"></studentClass-modal>

    <studentTask-modal ref="taskResultsForm" @ok="modalFormOk"></studentTask-modal>

    <gradeRate-modal ref="gradeRateForm" @ok="modalFormOk"></gradeRate-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import StudentClassModal from './modules/StudentClassModal'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import StudentTaskModal from './modules/StudentTaskModal'
  import GradeRateModal from './modules/GradeRateModal'

  export default {
    name: "StudentClassList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      StudentClassModal,
      JSearchSelectTag,
      StudentTaskModal,
      GradeRateModal
    },
    data () {
      return {
        description: '课程表管理页面',
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
            title:'周几',
            align:"center",
            dataIndex: 'weekDay_dictText'
          },
          {
            title:'第几节课程',
            align:"center",
            dataIndex: 'section_dictText'
          },
          {
            title:'课程名称',
            align:"center",
            dataIndex: 'subjectName'
          },
          {
            title:'老师',
            align:"center",
            dataIndex: 'teacher_dictText'
          },
          {
            title:'课程详细周数',
            align:"center",
            dataIndex: 'weekSeq'
          },
          {
            title:'课程开始周',
            align:"center",
            dataIndex: 'weekStart'
          },
          {
            title:'课程结束周',
            align:"center",
            dataIndex: 'weekEnd'
          },
          {
            title:'单双周',
            align:"center",
            dataIndex: 'isSingle_dictText'
          },
          {
            title:'教室',
            align:"center",
            dataIndex: 'location'
          },
          {
            title:'学年',
            align:"center",
            dataIndex: 'xn_dictText'
          },
          {
            title:'学期',
            align:"center",
            dataIndex: 'xq_dictText'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/manage/studentClass/list",
          delete: "/manage/studentClass/delete",
          deleteBatch: "/manage/studentClass/deleteBatch",
          exportXlsUrl: "/manage/studentClass/exportXls",
          importExcelUrl: "manage/studentClass/importExcel",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      addTask: function (record) {
        this.$refs.taskResultsForm.add(record.id);
        this.$refs.taskResultsForm.title = "新增";
        this.$refs.taskResultsForm.disableSubmit = false;
      },
      addRate: function (record) {
        this.$refs.gradeRateForm.add(record.id);
        this.$refs.gradeRateForm.title = "新增";
        this.$refs.gradeRateForm.disableSubmit = false;
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>