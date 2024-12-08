<template>
  <div class="addBrand-container">
    <div class="container">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
            <el-radio v-model="ruleForm.sex" label="1">男</el-radio>
            <el-radio v-model="ruleForm.sex" label="2">女</el-radio>
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="ruleForm.idNumber"></el-input>
        </el-form-item>
        <div class="subBox">
          <el-button type="primary" @click="submitForm('ruleForm',false)">保存</el-button>
          <el-button 
            v-if="this.optType === 'add'" 
            type="primary" 
            @click="submitForm('ruleForm',true)">保存并继续添加员工
          </el-button>
          <el-button @click="() => this.$router.push('/employee')">返回</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">

export default {
  
}
</script>

<style lang="scss" scoped>
.addBrand {
  &-container {
    margin: 30px;
    margin-top: 30px;
    .HeadLable {
      background-color: transparent;
      margin-bottom: 0px;
      padding-left: 0px;
    }
    .container {
      position: relative;
      z-index: 1;
      background: #fff;
      padding: 30px;
      border-radius: 4px;
      // min-height: 500px;
      .subBox {
        padding-top: 30px;
        text-align: center;
        border-top: solid 1px $gray-5;
      }
    }
    .idNumber {
      margin-bottom: 39px;
    }

    .el-form-item {
      margin-bottom: 29px;
    }
    .el-input {
      width: 293px;
    }
  }
}
</style>
