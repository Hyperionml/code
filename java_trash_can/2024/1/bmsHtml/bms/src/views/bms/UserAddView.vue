<template>
    <div>
        <el-container  style="height: 675px; border: 1px solid #eee">

            <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">图书管理系统</el-header>

            <el-container>
                <el-aside width="200px" style="border: 1px solid #eee">

                    <el-menu :default-openeds="['1', '3']" router="true" default-active="/user">
                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>导航栏</template>
                            <el-menu-item-group>
                                <el-menu-item index="/user">
                                    用户管理
                                </el-menu-item>
                                <el-menu-item index="/book">
                                    <!-- <router-link to="/book">图书管理</router-link> -->
                                    图书管理
                                </el-menu-item>
                            </el-menu-item-group>                           
                        </el-submenu>
                    </el-menu>

                </el-aside>

                <el-main>

                    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" >
                        <el-form-item label="姓名" prop="name">
                            <el-input v-model="ruleForm.name"></el-input>
                        </el-form-item>

                        <el-form-item label="用户名" prop="userName">
                            <el-input v-model="ruleForm.userName"></el-input>
                        </el-form-item>

                        <el-form-item label="密码" prop="password" >
                            <el-input v-model="ruleForm.password" placeholder="密码默认为123456"></el-input>
                        </el-form-item>

                        <el-form-item label="性别" prop="gender">
                            <el-select v-model="ruleForm.gender" placeholder="请选择性别，默认男">
                                <el-option label="男" value="1"></el-option>
                                <el-option label="女" value="2"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="权限" prop="root">
                            <el-select v-model="ruleForm.root" placeholder="默认一般用户">
                                <el-option label="管理员" value="1"></el-option>
                                <el-option label="一般用户" value="2"></el-option>
                            </el-select>
                        </el-form-item>

                        
                        <el-form-item>
                            <el-button type="primary" @click="submitForm()">保存</el-button>
                            <el-button @click="resetForm('ruleForm')">重置</el-button>
                            <el-button type="primary" @click="() => this.$router.push('/user')">返回</el-button>
                        </el-form-item>
                    </el-form>

                </el-main>

            </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
      return {
        optType: '',//当前操作类型是新增还是修改
        ruleForm: {
            id: '',
            name: '',
            userName: '',
            password: '',
            gender: '1',
            root: '2'
        },
        rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 21, message: '长度在 3 到 21 个字符', trigger: 'blur' }
          ],
        }
      };
    },
    created() {
        //根据路由信息区分新增和修改操作
        this.$nextTick(() => {
            this.optType = this.$route.query.id ? 'update' : 'add';
        });
        if(this.optType === 'update'){
            //修改操作，根据id查用户信息用于回显
            axios.get({
                method: "GET",
                url: "http://localhost:8080/user/" + this.optType.query.id
            }).then(res => {
                if(res.data.code === 1){
                    this.ruleForm = res.data.data;
                }
            })
        }
    },
    methods: {
        submitForm() {
            if(this.optType === 'add'){
                alert(this.ruleForm.name);
                axios({
                    method:"post",
                    url:"http://localhost:8080/user/save",
                    data:{
                        "id": "",
                        "name": this.ruleForm.name,
                        "userName": this.ruleForm.userName,
                        "password": this.ruleForm.password,
                        "gender": this.ruleForm.gender,
                        "root": this.ruleForm.root
                    } 
                }).then(res =>{
                    // 请求成功状态为1说明添加成功
                    if(res.data.code===1){
                    // 关闭用户新增表单弹窗
                    this.dialogFormVisible=false,
                    // 添加成功提示
                    this.$message({showClose: true, message: '添加成功！',type: 'success', duration:2000,center:true});
                    // 重新刷新列表数据
                    this.queryUserList();
                    }
                }).catch(error =>{
                    console.log(error)
                })
            }
            

            
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}
</script>

<style>

</style>