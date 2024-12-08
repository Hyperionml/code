<template>
  <el-dialog
    :title="!dataForm.patientId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="任务执行日期（主键）" prop="taskExecutionDate">
      <el-input v-model="dataForm.taskExecutionDate" placeholder="任务执行日期（主键）"></el-input>
    </el-form-item>
    <el-form-item label="任务完成状态(1:未开始;2:正在进行中;3:已完成;4:未完成)" prop="status">
      <el-input v-model="dataForm.status" placeholder="任务完成状态(1:未开始;2:正在进行中;3:已完成;4:未完成)"></el-input>
    </el-form-item>
    <el-form-item label="任务主体(外键)(药品id、设备id)" prop="mainPartId">
      <el-input v-model="dataForm.mainPartId" placeholder="任务主体(外键)(药品id、设备id)"></el-input>
    </el-form-item>
    <el-form-item label="任务详情(如：带病人去x光室，检查xxx部位)" prop="details">
      <el-input v-model="dataForm.details" placeholder="任务详情(如：带病人去x光室，检查xxx部位)"></el-input>
    </el-form-item>
    <el-form-item label="药品数量(若是药品任务，该数据需要与药品管理系统同步)" prop="drugAmount">
      <el-input v-model="dataForm.drugAmount" placeholder="药品数量(若是药品任务，该数据需要与药品管理系统同步)"></el-input>
    </el-form-item>
    <el-form-item label="任务开始时间" prop="startDate">
      <el-input v-model="dataForm.startDate" placeholder="任务开始时间"></el-input>
    </el-form-item>
    <el-form-item label="任务结束时间" prop="expireDate">
      <el-input v-model="dataForm.expireDate" placeholder="任务结束时间"></el-input>
    </el-form-item>
    <el-form-item label="任务备注(用于备注任务未完成/他人代做任务等特殊情况)" prop="notes">
      <el-input v-model="dataForm.notes" placeholder="任务备注(用于备注任务未完成/他人代做任务等特殊情况)"></el-input>
    </el-form-item>
    <el-form-item label="任务类型（1:检查任务;2:吃药任务）" prop="taskType">
      <el-input v-model="dataForm.taskType" placeholder="任务类型（1:检查任务;2:吃药任务）"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          patientId: 0,
          taskExecutionDate: '',
          status: '',
          mainPartId: '',
          details: '',
          drugAmount: '',
          startDate: '',
          expireDate: '',
          notes: '',
          taskType: ''
        },
        dataRule: {
          taskExecutionDate: [
            { required: true, message: '任务执行日期（主键）不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '任务完成状态(1:未开始;2:正在进行中;3:已完成;4:未完成)不能为空', trigger: 'blur' }
          ],
          mainPartId: [
            { required: true, message: '任务主体(外键)(药品id、设备id)不能为空', trigger: 'blur' }
          ],
          details: [
            { required: true, message: '任务详情(如：带病人去x光室，检查xxx部位)不能为空', trigger: 'blur' }
          ],
          drugAmount: [
            { required: true, message: '药品数量(若是药品任务，该数据需要与药品管理系统同步)不能为空', trigger: 'blur' }
          ],
          startDate: [
            { required: true, message: '任务开始时间不能为空', trigger: 'blur' }
          ],
          expireDate: [
            { required: true, message: '任务结束时间不能为空', trigger: 'blur' }
          ],
          notes: [
            { required: true, message: '任务备注(用于备注任务未完成/他人代做任务等特殊情况)不能为空', trigger: 'blur' }
          ],
          taskType: [
            { required: true, message: '任务类型（1:检查任务;2:吃药任务）不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.patientId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.patientId) {
            this.$http({
              url: this.$http.adornUrl(`/ward/task/info/${this.dataForm.patientId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.taskExecutionDate = data.task.taskExecutionDate
                this.dataForm.status = data.task.status
                this.dataForm.mainPartId = data.task.mainPartId
                this.dataForm.details = data.task.details
                this.dataForm.drugAmount = data.task.drugAmount
                this.dataForm.startDate = data.task.startDate
                this.dataForm.expireDate = data.task.expireDate
                this.dataForm.notes = data.task.notes
                this.dataForm.taskType = data.task.taskType
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ward/task/${!this.dataForm.patientId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'patientId': this.dataForm.patientId || undefined,
                'taskExecutionDate': this.dataForm.taskExecutionDate,
                'status': this.dataForm.status,
                'mainPartId': this.dataForm.mainPartId,
                'details': this.dataForm.details,
                'drugAmount': this.dataForm.drugAmount,
                'startDate': this.dataForm.startDate,
                'expireDate': this.dataForm.expireDate,
                'notes': this.dataForm.notes,
                'taskType': this.dataForm.taskType
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
