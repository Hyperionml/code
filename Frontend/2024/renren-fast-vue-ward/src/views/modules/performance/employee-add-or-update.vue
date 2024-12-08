<template>
  <el-dialog
    :title="!dataForm.userId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="部门ID" prop="deptId">
      <el-input v-model="dataForm.deptId" placeholder="部门ID"></el-input>
    </el-form-item>
    <el-form-item label="用户账号" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="用户昵称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder="用户昵称"></el-input>
    </el-form-item>
    <el-form-item label="用户类型（00系统用户）" prop="userType">
      <el-input v-model="dataForm.userType" placeholder="用户类型（00系统用户）"></el-input>
    </el-form-item>
    <el-form-item label="用户邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="用户邮箱"></el-input>
    </el-form-item>
    <el-form-item label="手机号码" prop="phonenumber">
      <el-input v-model="dataForm.phonenumber" placeholder="手机号码"></el-input>
    </el-form-item>
    <el-form-item label="用户性别（0男 1女 2未知）" prop="sex">
      <el-input v-model="dataForm.sex" placeholder="用户性别（0男 1女 2未知）"></el-input>
    </el-form-item>
    <el-form-item label="头像地址" prop="avatar">
      <el-input v-model="dataForm.avatar" placeholder="头像地址"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="dataForm.password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="帐号状态（0正常 1停用）" prop="status">
      <el-input v-model="dataForm.status" placeholder="帐号状态（0正常 1停用）"></el-input>
    </el-form-item>
    <el-form-item label="删除标志（0代表存在 2代表删除）" prop="delFlag">
      <el-input v-model="dataForm.delFlag" placeholder="删除标志（0代表存在 2代表删除）"></el-input>
    </el-form-item>
    <el-form-item label="最后登录IP" prop="loginIp">
      <el-input v-model="dataForm.loginIp" placeholder="最后登录IP"></el-input>
    </el-form-item>
    <el-form-item label="最后登录时间" prop="loginDate">
      <el-input v-model="dataForm.loginDate" placeholder="最后登录时间"></el-input>
    </el-form-item>
    <el-form-item label="创建者" prop="createBy">
      <el-input v-model="dataForm.createBy" placeholder="创建者"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="更新者" prop="updateBy">
      <el-input v-model="dataForm.updateBy" placeholder="更新者"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="学历" prop="education">
      <el-input v-model="dataForm.education" placeholder="学历"></el-input>
    </el-form-item>
    <el-form-item label="工龄" prop="seniority">
      <el-input v-model="dataForm.seniority" placeholder="工龄"></el-input>
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
          userId: 0,
          deptId: '',
          userName: '',
          nickName: '',
          userType: '',
          email: '',
          phonenumber: '',
          sex: '',
          avatar: '',
          password: '',
          status: '',
          delFlag: '',
          loginIp: '',
          loginDate: '',
          createBy: '',
          createTime: '',
          updateBy: '',
          updateTime: '',
          remark: '',
          education: '',
          seniority: ''
        },
        dataRule: {
          deptId: [
            { required: true, message: '部门ID不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          nickName: [
            { required: true, message: '用户昵称不能为空', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '用户类型（00系统用户）不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '用户邮箱不能为空', trigger: 'blur' }
          ],
          phonenumber: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '用户性别（0男 1女 2未知）不能为空', trigger: 'blur' }
          ],
          avatar: [
            { required: true, message: '头像地址不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '帐号状态（0正常 1停用）不能为空', trigger: 'blur' }
          ],
          delFlag: [
            { required: true, message: '删除标志（0代表存在 2代表删除）不能为空', trigger: 'blur' }
          ],
          loginIp: [
            { required: true, message: '最后登录IP不能为空', trigger: 'blur' }
          ],
          loginDate: [
            { required: true, message: '最后登录时间不能为空', trigger: 'blur' }
          ],
          createBy: [
            { required: true, message: '创建者不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateBy: [
            { required: true, message: '更新者不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          education: [
            { required: true, message: '学历不能为空', trigger: 'blur' }
          ],
          seniority: [
            { required: true, message: '工龄不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.userId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.userId) {
            this.$http({
              url: this.$http.adornUrl(`/performance/employee/info/${this.dataForm.userId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.deptId = data.employee.deptId
                this.dataForm.userName = data.employee.userName
                this.dataForm.nickName = data.employee.nickName
                this.dataForm.userType = data.employee.userType
                this.dataForm.email = data.employee.email
                this.dataForm.phonenumber = data.employee.phonenumber
                this.dataForm.sex = data.employee.sex
                this.dataForm.avatar = data.employee.avatar
                this.dataForm.password = data.employee.password
                this.dataForm.status = data.employee.status
                this.dataForm.delFlag = data.employee.delFlag
                this.dataForm.loginIp = data.employee.loginIp
                this.dataForm.loginDate = data.employee.loginDate
                this.dataForm.createBy = data.employee.createBy
                this.dataForm.createTime = data.employee.createTime
                this.dataForm.updateBy = data.employee.updateBy
                this.dataForm.updateTime = data.employee.updateTime
                this.dataForm.remark = data.employee.remark
                this.dataForm.education = data.employee.education
                this.dataForm.seniority = data.employee.seniority
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
              url: this.$http.adornUrl(`/performance/employee/${!this.dataForm.userId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'userId': this.dataForm.userId || undefined,
                'deptId': this.dataForm.deptId,
                'userName': this.dataForm.userName,
                'nickName': this.dataForm.nickName,
                'userType': this.dataForm.userType,
                'email': this.dataForm.email,
                'phonenumber': this.dataForm.phonenumber,
                'sex': this.dataForm.sex,
                'avatar': this.dataForm.avatar,
                'password': this.dataForm.password,
                'status': this.dataForm.status,
                'delFlag': this.dataForm.delFlag,
                'loginIp': this.dataForm.loginIp,
                'loginDate': this.dataForm.loginDate,
                'createBy': this.dataForm.createBy,
                'createTime': this.dataForm.createTime,
                'updateBy': this.dataForm.updateBy,
                'updateTime': this.dataForm.updateTime,
                'remark': this.dataForm.remark,
                'education': this.dataForm.education,
                'seniority': this.dataForm.seniority
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
