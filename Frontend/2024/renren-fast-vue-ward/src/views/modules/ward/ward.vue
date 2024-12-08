<template>
  <el-row :gutter="20">
    <el-col :span="10">
      <div class="mod-config">

        <span class="block">
          <el-cascader v-model="location" :options="wardLocations" :props="{
            checkStrictly: true, expandTrigger: 'hover',
            label: 'locationName', value: 'locationName', children: 'nextLocations'
          }" clearable filterable placeholder="病房位置查询"></el-cascader>
        </span>

        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>

        <el-select v-model="wardType" filterable placeholder="病房类型" clearable style="width: 110px;">
          <el-option v-for="item in allWardType" :key="item.value" :label="item.name" :value="item.id">
          </el-option>
        </el-select>

        <br>
        <br>

        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getWardDataList()">
          <div>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <el-form-item>
              <el-button @click="getWardDataList()" style="width: 90px;">查询</el-button>
              <el-button @click="getAllWardDataList()">查询全部</el-button>
            </el-form-item>
          </div>
        </el-form>

        <el-table :data="dataList" stripe v-loading="dataListLoading" @selection-change="selectionChangeHandle"  
          style="width: 100%;">

          <el-table-column prop="locationDescribe" header-align="center" align="center" label="病房物理位置" width="130px">
          </el-table-column>

          <el-table-column prop="type" header-align="center" align="center" label="病房类型" width="130px">
          </el-table-column>

          <el-table-column header-align="center" align="center" width="140" label="入住率">
            <template slot-scope="scope">
              <el-progress :text-inside="true" :stroke-width="15"
                :percentage="100 - (scope.row.idleSickedbdeNumber / scope.row.sickedbedNumber) * 100"></el-progress>
            </template>
          </el-table-column>

          <el-table-column fixed="right" header-align="center" align="center" width="100" label="操作">
            <template slot-scope="scope">
              <a class="myclass" :ref="`wardId-${scope.row.id}`" @click="queryWardDetails(scope.row.id, $event)">详情</a>
            </template>
          </el-table-column>

        </el-table>

        <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>

        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getWardDataList"></add-or-update>

      </div>
    </el-col>

    <el-col :span="14">

      <div class="mod-config">
        <el-dialog title="病人信息" :visible.sync="dialogFormVisible">

          <el-form :model="patientDetail">

            <el-form-item label="姓名：" :label-width="formLabelWidth">
              <el-input v-model="patientDetail.name" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="年龄：" :label-width="formLabelWidth">
              <el-input v-model="patientDetail.age" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="性别：" :label-width="formLabelWidth">
              <el-radio-group v-model="patientDetail.sex">
                <el-radio-button label="男"></el-radio-button>
                <el-radio-button label="女"></el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="身高(cm)：" :label-width="formLabelWidth">
              <el-input v-model="patientDetail.height" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="体重：" :label-width="formLabelWidth">
              <el-input v-model="patientDetail.weight" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="血型：" :label-width="formLabelWidth">
              <el-select v-model="patientDetail.bloodType" clearable placeholder="请选择" style="width: 150px;">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="入院时间：" :label-width="formLabelWidth">
              <div class="block">
                <el-date-picker v-model="patientDetail.admissionDate" align="right" type="date" placeholder="选择日期"
                  :picker-options="pickerOptions" style="width: 150px;">
                </el-date-picker>
              </div>
            </el-form-item>

            <el-form-item label="身份证：" :label-width="formLabelWidth">
              <el-input v-model="patientDetail.id" autocomplete="off" style="width: 400px;"></el-input>
            </el-form-item>

            <el-form-item label="患病情况：" :label-width="formLabelWidth">
              <el-select v-model="patientDetail.prevalence" filterable placeholder="" clearable style="width: 400px;">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="联系方式：" :label-width="formLabelWidth">
              <el-input v-model="patientDetail.contactInformation" autocomplete="off" style="width: 400px;"></el-input>
            </el-form-item>

          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updatePatient()">修 改</el-button>
          </div>

        </el-dialog>

        <el-dialog title="关联病人" :visible.sync="relationDialogFormVisible">

          <el-form :model="relationPatientInfo">

            <el-form-item label="姓名：" :label-width="formLabelWidth">
              <el-input v-model="relationPatientInfo.name" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="年龄：" :label-width="formLabelWidth">
              <el-input v-model="relationPatientInfo.age" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="性别：" :label-width="formLabelWidth">
              <el-radio-group v-model="relationPatientInfo.sex">
                <el-radio-button label="男"></el-radio-button>
                <el-radio-button label="女"></el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="身高(cm)：" :label-width="formLabelWidth">
              <el-input v-model="relationPatientInfo.height" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="体重：" :label-width="formLabelWidth">
              <el-input v-model="relationPatientInfo.weight" autocomplete="off" style="width: 150px;"></el-input>
            </el-form-item>

            <el-form-item label="血型：" :label-width="formLabelWidth">
              <el-select v-model="relationPatientInfo.bloodType" clearable placeholder="请选择" style="width: 150px;">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="入院时间：" :label-width="formLabelWidth">
              <div class="block">
                <el-date-picker v-model="relationPatientInfo.admissionDate" align="right" type="date" placeholder="选择日期"
                  :picker-options="pickerOptions" style="width: 150px;">
                </el-date-picker>
              </div>
            </el-form-item>

            <el-form-item label="身份证：" :label-width="formLabelWidth">
              <el-input v-model="relationPatientInfo.id" autocomplete="off" style="width: 400px;"></el-input>
            </el-form-item>

            <el-form-item label="患病情况：" :label-width="formLabelWidth">
              <el-select v-model="relationPatientInfo.prevalence" filterable placeholder="" clearable
                style="width: 400px;">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="联系方式：" :label-width="formLabelWidth">
              <el-input v-model="relationPatientInfo.contactInformation" autocomplete="off"
                style="width: 400px;"></el-input>
            </el-form-item>

          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="relationDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="relationPatient()" :plain="true">关 联</el-button>
          </div>
        </el-dialog>




        <el-dialog title="关联护士" :visible.sync="dialogFormVisible01">

          <el-form :model="form">
            <el-form-item label="选择护士" :label-width="formLabelWidth">
              <el-select v-model="nurseId" filterable placeholder="请输入工号/姓名">
                <el-option v-for="item in allOrdinaryNurse" :key="item.value" :label="item.idAndName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible01 = false">取 消</el-button>
            <el-button type="primary" @click="relationSickedbedAndNurse()">确 定</el-button>
          </div>

        </el-dialog>

        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getSickedbedAndPatientDataList()">

          <el-form-item>
            <el-input v-model="sickedAndPatientDataForm.sickedAndPatientKey" placeholder="参数名" clearable></el-input>
          </el-form-item>

          <el-select v-model="sickedbedType" filterable placeholder="病床类型" clearable style="width: 110px;">
            <el-option v-for="item in allSickedbedType" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>

          <el-select v-model="status" filterable placeholder="病床状态" clearable style="width: 110px;">
            <el-option v-for="item in sickdebedStatus" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>

          <el-form-item>
            <el-button @click="getSickedbedAndPatientDataList()">查询</el-button>
            <el-button @click="getSickedbedAndPatientDataAllList()">查询全部</el-button>
          </el-form-item>

        </el-form>

        <el-table :data="sickbedPatientList" stripe v-loading="dataListLoading" @selection-change="selectionChangeHandle"
          style="width: 100%;">

          <el-table-column header-align="center" align="center" width="70" label="病床号">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="light" content="" placement="top">
                <div slot="content">病房位置：<br />{{ scope.row.locationDescribe }}</div>
                <el-button type="text" @click="findWardBySickdbedId(scope.row.sickbedId, scope.row.wardId)"
                  :class="{ 'green-background': scope.row.name == null, 'yellow-background': scope.row.name != null }">{{
                    scope.row.sickbedId }}</el-button>
              </el-tooltip>

            </template>
          </el-table-column>

          <el-table-column prop="type" header-align="center" width="120" align="center" label="病床类型">
          </el-table-column>

          <el-table-column header-align="center" align="center" width="90" label="姓名">
            <template slot-scope="scope">
              <!-- <el-popover placement="right" width="400" trigger="click">
                <div>身份证：{{ patientDetail.id }}</div>
                <div>姓名：{{ patientDetail.name }}</div>
                <div>年龄：{{ patientDetail.age }}</div>
                <div>性别：{{ patientDetail.sex }}</div>
                <div>身高(cm)：{{ patientDetail.height }}</div>
                <div>体重：{{ patientDetail.weight }}</div>
                <div>血型：{{ patientDetail.bloodType }}</div>
                <div>患病情况：{{ patientDetail.prevalence }}</div>
                <div>入院时间：{{ patientDetail.admissionDate }}</div>
                <div>联系方式：{{ patientDetail.contactInformation }}</div>
                <el-button type="text" slot="reference" @click="getPatientDetail(scope.row.patientId)">{{ scope.row.name
                }}</el-button>
              </el-popover> -->
              <el-button type="text" @click="getPatientDetail(scope.row.patientId)">
                {{ scope.row.name }}</el-button>
            </template>
          </el-table-column>


          <el-table-column header-align="center" align="center" width="140" label="身份证">
            <template slot-scope="scope">
              <el-button v-if="scope.row.patientId == null" type="text"
                @click="relationPatientWindow(scope.row.sickbedId)">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;联</el-button>
              <span v-if="scope.row.patientId != null" class="myclass">
                {{ scope.row.patientId }}</span>
            </template>
          </el-table-column>

          <el-table-column header-align="center" align="center" width="60" label="性别">
            <template slot-scope="scope">
              <el-button v-if="scope.row.sex == null" type="text" @click="relationPatientWindow(scope.row.sickbedId)">
                病&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
              <span v-if="scope.row.sex != null" class="myclass" @click="queryWardDetails(scope.row.id, $event)">
                {{ scope.row.sex }}</span>
            </template>
          </el-table-column>

          <el-table-column prop="prevalence" header-align="center" align="center" width="150" label="患病情况">
          </el-table-column>

          <el-table-column fixed="right" header-align="center" align="center" width="80" label="负责护士">
            <template slot-scope="scope">
              <el-button v-if="scope.row.currentNurseId == null" type="text"
                @click="getAllOrdinaryNurse(scope.row.sickbedId, null)">关联护士</el-button>
              <!-- <el-popover v-if="scope.row.currentNurseId != null" placement="right" width="200" trigger="click">
                <div>工号：{{ nurseDetail.id }}&nbsp;&nbsp;&nbsp;等级：{{ nurseDetail.grade }} </div>
                <div>姓名：{{ nurseDetail.name }}&nbsp;年龄：{{ nurseDetail.age }}</div>
                <div></div>
                <div>性别：{{ nurseDetail.sex }}</div>
                <div>联系方式：{{ nurseDetail.contactInformation }}</div>
                <el-button type="text" slot="reference" @click="getNurseDetail(scope.row.currentNurseId)">{{
                  scope.row.currentNurseId }}</el-button>
              </el-popover> -->
              <el-tooltip v-if="scope.row.currentNurseId != null" class="item" effect="light" content="" placement="left">
                <div slot="content">工号：{{ scope.row.currentNurseId }}<br />姓名：{{ scope.row.nurseName }}</div>
                <div slot="content">年龄：{{ scope.row.age }}<br />联系方式：<br />{{ scope.row.contactInformation }}</div>
                <el-button type="text"
                  @click="getAllOrdinaryNurse(scope.row.sickbedId, scope.row.currentNurseId, scope.row.nurseName)">{{
                    scope.row.currentNurseId
                  }}</el-button>
              </el-tooltip>

            </template>
          </el-table-column>

        </el-table>

        <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
          :current-page="sickedAndPatientPageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="sickedAndPatientPageSize"
          :total="sickedAndPatientTotalPage" layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>

        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getWardDataList"></add-or-update>
      </div>
    </el-col>

  </el-row>
</template>
  
<script>
import Spu from '../product/spu.vue';
import AddOrUpdate from './ward-add-or-update'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      sickedAndPatientDataForm: {
        sickedAndPatientKey: ''
      },
      textColor: '',
      activeText: 0,
      dataList: [],
      sickbedPatientList: [],
      flag: false,
      color: '',
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      sickedAndPatientPageIndex: 1,
      sickedAndPatientPageSize: 10,
      sickedAndPatientTotalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      sickdebedStatus: [{
        value: '1',
        label: '占用'
      }, {
        value: '2',
        label: '空闲'
      }],
      value: '',
      dialogFormVisible: false,
      relationDialogFormVisible: false,
      form: {
        name: '',
        type: []
      },
      formLabelWidth: '90px',
      radio1: '',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      value1: '',
      value2: '',
      restaurants: [],
      state1: '',
      dialogFormVisible01: false,
      wardLocations: [],
      patientDetail: {},
      nurseDetail: {},
      allOrdinaryNurse: [],
      nurseId: '',
      sickedbedId: '',
      location: [],
      allWardType: [],
      wardType: '',
      allSickedbedType: [],
      sickedbedType: '',
      relationPatientInfo: { 'sickbedId': '' },
      status: ''

    }
  },
  components: {
    AddOrUpdate
  },
  activated() {
    this.getWardDataList();
    this.getSickedbedAndPatientDataList();
    this.queryWardLocations();
    this.getAllWardType();
    this.getAllSickedbed();
  },
  methods: {

    //当点击“关联病人”后，要获取病床id，再弹出模态窗口
    relationPatientWindow(sickbedId) {

      this.relationDialogFormVisible = true;
      console.log(sickbedId)
      this.relationPatientInfo.sickbedId = sickbedId;

    },

    //修改病人信息
    updatePatient() {



      this.location = 'http://localhost:8001/#/ward-ward';
      //关闭模态窗口
      this.dialogFormVisible = false;
      this.$http({
        url: this.$http.adornUrl('/ward/ward/updatePatientInfo'),
        method: 'post',
        data: this.$http.adornData(this.patientDetail, false)
      }).then(({ data }) => {
        //弹出关联成功信息
        this.$message({
          message: '病人信息修改成功',
          type: 'success'
        });
      });

    },

    //关联病人
    relationPatient() {



      this.$http({
        url: this.$http.adornUrl('/ward/ward/patientInHospital'),
        method: 'post',
        data: this.$http.adornData(this.relationPatientInfo, false)
      }).then(({ data }) => {

        //关闭模态窗口
        this.relationDialogFormVisible = false;

        //刷新病床&病人列表
        this.getSickedbedAndPatientDataList();
        this.getWardDataList();

        //弹出关联成功信息
        this.$message({
          message: '病人关联成功',
          type: 'success'
        });



      });

    },

    //获取所有病床的类型
    getAllSickedbed() {

      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryAllSickedType'),
        method: 'get',
        params: this.$http.adornParams({})
      }).then(({ data }) => {

        this.allSickedbedType = data.data;

      })

    },

    //查询出所有的病房类型
    getAllWardType() {
      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryAllWardType'),
        method: 'get',
        params: this.$http.adornParams({})
      }).then(({ data }) => {

        this.allWardType = data.data;

      })
    },

    //查询全部病房
    getAllWardDataList() {

      //清空所有“详情”的红色样式
      const elements = document.getElementsByClassName('myclass');
      Array.prototype.forEach.call(elements, (el) => {
        el.style.color = '';
      })

      //清空所有查询条件
      this.key = '';
      this.wardType = '';
      this.location = [];
      this.getWardDataList();

    },

    //点击病床号id，在病房侧找出指定病房，且“详情标红”
    findWardBySickdbedId(sickedbedId, wardId) {

      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryWardBySickedbed'),
        method: 'get',
        params: this.$http.adornParams({
          'sickedbedId': sickedbedId
        })
      }).then(({ data }) => {

        //根据病房的“楼”“栋”信息，刷新病房侧的展示页面，并
        var buding = data.data.locationDescribe.split("栋");
        var flor = buding[1].split("楼");

        this.location = [buding[0] + "栋", flor[0] + "楼"]
        console.log(this.location)

        this.getWardDataList();
        //将病房的将“详情”标红
        var wardId = data.data.id

        //将指定病房下的所有病床信息展现出来
        this.queryWardDetails(wardId);

        //标红指定的病房
        this.$refs[`wardId-${wardId}`].style.color = 'red';

      })

    },

    //关联病床和护士
    relationSickedbedAndNurse() {

      console.log(this.sickedbedId);

      this.$http({
        url: this.$http.adornUrl('/ward/ward/allocationNurseBySickedbed'),
        method: 'get',
        params: this.$http.adornParams({
          'sickedbedId': this.sickedbedId,
          'nurseId': this.nurseId
        })
      }).then(({ data }) => {

        //清空sickedbedId，为下一次关联护士做准备
        this.sickedbedId = '';

        this.dialogFormVisible01 = false

        this.getSickedbedAndPatientDataList();

        this.$message({
          type: 'success',
          message: '关联成功!'
        });

      })

    },

    //查询出所有的护士  展示：工号/姓名
    getAllOrdinaryNurse(sickedbedId, currentNurseId, nurseName) {

      //判断用户是否是通过"护士名xxx"点击进来的。如果是，则要回显内容；否则清空之前的内容
      if (currentNurseId != null) {

        this.nurseId = currentNurseId + '/' + nurseName;

      } else {

        this.nurseId = '';

      }

      //弹出模态窗口
      this.dialogFormVisible01 = true
      //为模态窗口准备选中栏的病床id
      this.sickedbedId = sickedbedId;

      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryAllOrdinaryNurse'),
        method: 'get',
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        this.allOrdinaryNurse = data.data
      })
    },

    //获取护士的详细信息
    getNurseDetail(nurseId) {
      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryNurseDetail'),
        method: 'get',
        params: this.$http.adornParams({
          'nurseId': nurseId
        })
      }).then(({ data }) => {
        this.nurseDetail = data.data;
      })
    },

    //获取病人的详细信息
    getPatientDetail(patientId) {

      this.dialogFormVisible = true;

      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryPatientDetail'),
        method: 'get',
        params: this.$http.adornParams({
          'patientId': patientId
        })
      }).then(({ data }) => {
        this.patientDetail = data.data;
      })
    },

    //获取病房的物理位置，并进行渲染
    queryWardLocations() {
      this.$http({
        url: this.$http.adornUrl('/ward/ward/wardDropdownBoxInformation'),
        method: 'get'
      }).then(({ data }) => {
        this.wardLocations = data.data.location;
      });
    },

    // 获取病房的数据列表
    getWardDataList() {

      //取出级联框中的数据
      //join：二栋，2楼，2房 ===》 replace(/,/g,'')，将字符串中所有','，替换成‘’(空串)  ；g是全局替换的意思  ；/,/g,''是一个正则表达式 
      var location = this.location.join().replace(/,/g, '');


      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/ward/ward/list'),
        method: 'get',
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          'wardTypeId': this.wardType,
          'location': location
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.dataList,
            this.totalPage = data.data.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false
      })
    },

    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getWardDataList()
    },

    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getWardDataList()
    },

    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },

    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },

    //点击详情，查看病房对应的病床&病人信息
    queryWardDetails(id, event) {

      const elements = document.getElementsByClassName('myclass');
      Array.prototype.forEach.call(elements, (el) => {
        el.style.color = '';
      })

      if (event != null) {
        // 获取当前被点击的元素
        const target = event.target;
        // 修改元素的class或style属性
        // target.removeAttribute('style');
        target.style.color = 'red'
      }

      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/ward/ward/queryDetails/' + id),
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.sickbedPatientList = data.data.dataList,
            this.sickedAndPatientTotalPage = data.data.totalCount;
        } else {
          this.sickbedPatientList = [];
          this.sickedAndPatientTotalPage = 0;
        }
        this.dataListLoading = false
      })

    },

    // 查询病床病人信息
    getSickedbedAndPatientDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/ward/ward/list/patientAndSickedbed'),
        method: 'get',
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          'key': this.sickedAndPatientDataForm.sickedAndPatientKey,
          'sickedbedType': this.sickedbedType,
          'status': this.status
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.sickbedPatientList = data.data.dataList,
            this.sickedAndPatientTotalPage = data.data.totalCount;
        } else {
          this.sickbedPatientList = [];
          this.sickedAndPatientTotalPage = 0;
        }
        this.dataListLoading = false
      })

    },

    getSickedbedAndPatientDataAllList() {

      this.sickedAndPatientDataForm.sickedAndPatientKey = '';
      this.sickedbedType = '';
      this.status = '';
      this.getSickedbedAndPatientDataList();

    }

  },
  mounted() {
    this.restaurants = this.loadAll();
  }
}
</script>

<style scoped>
.green-background {
  background-color: #b3ffb3 !important;
  padding: 0;
  margin: 0;
  width: 100%;
  height: 100%;
}

.yellow-background {
  background-color: #ffffb3 !important;
  padding: 0;
  margin: 0;
  width: 100%;
  height: 100%;
}

.box {
  width: 400px;

  .top {
    text-align: center;
  }

  .left {
    float: left;
    width: 60px;
  }

}
</style>