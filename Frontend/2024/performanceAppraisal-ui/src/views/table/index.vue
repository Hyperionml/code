<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:table:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:table:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:table:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:table:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="部门名" align="center" prop="deptName" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="其它费" align="center" prop="otherFree" />
      <el-table-column label="诊疗费" align="center" prop="consultationFee" />
      <el-table-column label="挂号费" align="center" prop="registrationFee" />
      <el-table-column label="检查费" align="center" prop="checkFee" />
      <el-table-column label="检验费" align="center" prop="surveyFee" />
      <el-table-column label="放射费" align="center" prop="radiationFee" />
      <el-table-column label="疫苗费" align="center" prop="vaccineFee" />
      <el-table-column label="公共卫生费" align="center" prop="hygieneFee" />
      <el-table-column label="卫生材料" align="center" prop="hygienicMaterial" />
      <el-table-column label="B超" align="center" prop="bModeUltrasonography" />
      <el-table-column label="手术费" align="center" prop="operationFee" />
      <el-table-column label="体检费" align="center" prop="physicalexaminationFee" />
      <el-table-column label="西药费" align="center" prop="westernmedicine" />
      <el-table-column label="治疗费" align="center" prop="treatment" />
      <el-table-column label="特检费" align="center" prop=" special_examination" />
      <el-table-column label="中成药费" align="center" prop="traditionalchinesemedicine" />
      <el-table-column label="物理治疗费" align="center" prop="physicaltherapy" />
      <el-table-column label="理疗费" align="center" prop=" physiotherapy" />
      <el-table-column label="心电图" align="center" prop="electrocardiogram" />
      <el-table-column label="中医治疗费" align="center" prop="traditionalchinesemedicineFee" />
      <el-table-column label="麻醉费" align="center" prop=" anesthesia" />
      <el-table-column label="中草药费" align="center" prop=" chineseherbalmedicineFee" />
      <el-table-column label="床位费" align="center" prop="bedFee" />
      <el-table-column label="护理费" align="center" prop=" nursingFee" />
      <el-table-column label="输氧费" align="center" prop="oxygenFee" />
      <el-table-column label="CT费" align="center" prop=" ctFee" />
      <el-table-column label="合计" align="center" prop="amount" />
      <el-table-column label="绩效" align="center" prop="performance" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:table:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:table:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门名" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入部门名" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="其它费" prop="otherFree">
          <el-input v-model="form.otherFree" placeholder="请输入其它费" />
        </el-form-item>
        <el-form-item label="诊疗费" prop="consultationFee">
          <el-input v-model="form.consultationFee" placeholder="请输入诊疗费" />
        </el-form-item>
        <el-form-item label="挂号费" prop="registrationFee">
          <el-input v-model="form.registrationFee" placeholder="请输入挂号费" />
        </el-form-item>
        <el-form-item label="检查费" prop="checkFee">
          <el-input v-model="form.checkFee" placeholder="请输入检查费" />
        </el-form-item>
        <el-form-item label="检验费" prop="surveyFee">
          <el-input v-model="form.surveyFee" placeholder="请输入检验费" />
        </el-form-item>
        <el-form-item label="放射费" prop="radiationFee">
          <el-input v-model="form.radiationFee" placeholder="请输入放射费" />
        </el-form-item>
        <el-form-item label="疫苗费" prop="vaccineFee">
          <el-input v-model="form.vaccineFee" placeholder="请输入疫苗费" />
        </el-form-item>
        <el-form-item label="公共卫生费" prop="hygieneFee">
          <el-input v-model="form.hygieneFee" placeholder="请输入公共卫生费" />
        </el-form-item>
        <el-form-item label="卫生材料" prop="hygienicMaterial">
          <el-input v-model="form.hygienicMaterial" placeholder="请输入卫生材料" />
        </el-form-item>
        <el-form-item label="B超" prop="bModeUltrasonography">
          <el-input v-model="form.bModeUltrasonography" placeholder="请输入B超" />
        </el-form-item>
        <el-form-item label="手术费" prop="operationFee">
          <el-input v-model="form.operationFee" placeholder="请输入手术费" />
        </el-form-item>
        <el-form-item label="体检费" prop="physicalexaminationFee">
          <el-input v-model="form.physicalexaminationFee" placeholder="请输入体检费" />
        </el-form-item>
        <el-form-item label="西药费" prop="westernmedicine">
          <el-input v-model="form.westernmedicine" placeholder="请输入西药费" />
        </el-form-item>
        <el-form-item label="治疗费" prop="treatment">
          <el-input v-model="form.treatment" placeholder="请输入治疗费" />
        </el-form-item>
        <el-form-item label="特检费" prop=" special examination">
          <el-input v-model="form. special_examination" placeholder="请输入特检费" />
        </el-form-item>
        <el-form-item label="中成药费" prop="traditionalchinesemedicine">
          <el-input v-model="form.traditionalchinesemedicine" placeholder="请输入中成药费" />
        </el-form-item>
        <el-form-item label="物理治疗费" prop="physicaltherapy">
          <el-input v-model="form.physicaltherapy" placeholder="请输入物理治疗费" />
        </el-form-item>
        <el-form-item label="理疗费" prop=" physiotherapy">
          <el-input v-model="form. physiotherapy" placeholder="请输入理疗费" />
        </el-form-item>
        <el-form-item label="心电图" prop="electrocardiogram">
          <el-input v-model="form.electrocardiogram" placeholder="请输入心电图" />
        </el-form-item>
        <el-form-item label="中医治疗费" prop="traditionalchinesemedicineFee">
          <el-input v-model="form.traditionalchinesemedicineFee" placeholder="请输入中医治疗费" />
        </el-form-item>
        <el-form-item label="麻醉费" prop=" anesthesia">
          <el-input v-model="form. anesthesia" placeholder="请输入麻醉费" />
        </el-form-item>
        <el-form-item label="中草药费" prop=" chineseherbalmedicineFee">
          <el-input v-model="form. chineseherbalmedicineFee" placeholder="请输入中草药费" />
        </el-form-item>
        <el-form-item label="床位费" prop="bedFee">
          <el-input v-model="form.bedFee" placeholder="请输入床位费" />
        </el-form-item>
        <el-form-item label="护理费" prop=" nursingFee">
          <el-input v-model="form. nursingFee" placeholder="请输入护理费" />
        </el-form-item>
        <el-form-item label="输氧费" prop="oxygenFee">
          <el-input v-model="form.oxygenFee" placeholder="请输入输氧费" />
        </el-form-item>
        <el-form-item label="CT费" prop=" ctFee">
          <el-input v-model="form. ctFee" placeholder="请输入CT费" />
        </el-form-item>
        <el-form-item label="合计" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入合计" />
        </el-form-item>
        <el-form-item label="绩效" prop="performance">
          <el-input v-model="form.performance" placeholder="请输入绩效" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTable, getTable, delTable, addTable, updateTable } from "@/api/system/table";

export default {
  name: "Table",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      tableList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptName: null,
        name: null,
        otherFree: null,
        consultationFee: null,
        registrationFee: null,
        checkFee: null,
        surveyFee: null,
        radiationFee: null,
        vaccineFee: null,
        hygieneFee: null,
        hygienicMaterial: null,
        bModeUltrasonography: null,
        operationFee: null,
        physicalexaminationFee: null,
        westernmedicine: null,
        treatment: null,
         special_examination: null,
        traditionalchinesemedicine: null,
        physicaltherapy: null,
         physiotherapy: null,
        electrocardiogram: null,
        traditionalchinesemedicineFee: null,
         anesthesia: null,
         chineseherbalmedicineFee: null,
        bedFee: null,
         nursingFee: null,
        oxygenFee: null,
         ctFee: null,
        amount: null,
        performance: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptName: [
          { required: true, message: "部门名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listTable(this.queryParams).then(response => {
        this.tableList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.deptName)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const deptName = row.deptName || this.ids
      getTable(deptName).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.deptName != null) {
            updateTable(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTable(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const deptNames = row.deptName || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + deptNames + '"的数据项？').then(function() {
        return delTable(deptNames);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/table/export', {
        ...this.queryParams
      }, `table_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
