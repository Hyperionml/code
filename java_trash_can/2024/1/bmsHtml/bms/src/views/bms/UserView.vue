<template>
    <div>
        <el-container style="height: 675px; border: 1px solid #eee">

            <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">图书管理系统</el-header>

            <el-container>
                <el-aside width="200px" style="border: 1px solid #eee">

                    <el-menu :default-openeds="['1', '3']" default-active="/user" class="el-menu-vertical-demo"
                        :router="true">
                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>导航栏</template>
                            <el-menu-item-group>
                                <el-menu-item index="/user">
                                    用户管理
                                </el-menu-item>
                                <el-menu-item index="/book">
                                    图书管理
                                </el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </el-menu>

                </el-aside>

                <el-main>

                    <!-- 表单 -->
                    <el-form :inline="true" :model="searchFrom" class="demo-form-inline">
                        <el-form-item label="姓名">
                            <el-input v-model="searchFrom.name" placeholder="姓名"></el-input>
                        </el-form-item>

                        <el-form-item label="性别">
                            <el-select v-model="searchFrom.gender" placeholder="性别">
                                <el-option label="男" value="1"></el-option>
                                <el-option label="女" value="2"></el-option>
                                <el-option label="不选择性别" value=""></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">查询</el-button>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="handleAddUser">+添加用户</el-button>
                        </el-form-item>

                    </el-form>

                    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                            <el-form-item label="姓名" prop="name">
                                <el-input v-model="ruleForm.name"></el-input>
                            </el-form-item>

                            <el-form-item label="用户名" prop="userName">
                                <el-input v-model="ruleForm.userName"></el-input>
                            </el-form-item>

                            <el-form-item label="密码" prop="password">
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
                            </el-form-item>
                        </el-form>
                    </el-dialog>

                    <el-table :data="tableData" border>
                        <el-table-column prop="name" label="姓名" width="140">
                        </el-table-column>
                        <el-table-column prop="userName" label="用户名" width="120">
                        </el-table-column>
                        <el-table-column prop="password" label="密码">
                        </el-table-column>
                        <el-table-column prop="gender" label="性别">
                            <template slot-scope="scope">
                                {{ scope.row.gender === 1 ? '男' : '女' }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="root" label="权限">
                            <template slot-scope="scope">
                                {{ scope.row.root === 1 ? '管理员' : '一般用户' }}
                            </template>
                        </el-table-column>

                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button type="primary" size="mini" @click="handleUpdateUser(scope.row)">编辑</el-button>
                                <el-button type="danger" size="mini" @click="handleDeleteUser(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>

                    </el-table>

                    <br>

                    <el-pagination @current-change="handleCurrentChange" :current-page="page" :page-size="pageSize"
                        background layout="prev, pager, next" :total="50">
                    </el-pagination>

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
            optType: '',
            page: 1, //页码
            pageSize: 5,
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
            },
            dialogFormVisible: false,
            tableData: [
                {
                    "id": 1,
                    "name": "百里莫凌",
                    "userName": "hyperionml",
                    "password": "123456",
                    "gender": "男",
                    "root": "管理员"
                },
                {
                    "id": 2,
                    "name": "胡安康",
                    "userName": "huankang",
                    "password": "123456",
                    "gender": "男",
                    "root": "一般用户"
                }
            ],
            searchFrom: {
                name: "",
                gender: ""
            }
        }
    },

    mounted() {
        axios.get('http://localhost:8080/user', {
            params: {
                name: this.searchFrom.name,
                gender: this.searchFrom.gender,
                page: this.page,
                pageSize: this.pageSize
            }
        }).then((res) => {
            this.tableData = res.data.data;
            /* this.tableData.forEach((e) => {
                if(e.gender === 1){
                    e.gender = '男';
                }else{
                    e.gender = '女';
                }

                if(e.root === 1){
                    e.root = '管理员';
                }else{
                    e.root = '一般用户';
                }
            }) */
        })
    },

    methods: {
        onSubmit: function () {
            this.queryUserList();
        },
        //跳转到新增用户界面
        handleAddUser() {
            this.optType = 'add';
            //路由跳转到新增组件
            this.dialogFormVisible = true;
            this.ruleForm.id = '',
            this.ruleForm.name = '',
            this.ruleForm.userName = '',
            this.ruleForm.password = '',
            this.ruleForm.gender = '1',
            this.ruleForm.root = '2'
        },
        handleCurrentChange(page) {
            //页码发生变化时触发
            this.page = page;
            this.queryUserList();

        },
        handleUpdateUser(row) {
            if (row.id === 1) {
                this.$message.error("该账号为超级管理员，无法修改！");
                return;
            }
            //跳转并传参
            this.dialogFormVisible = true;
            this.optType = 'update';
            //alert(JSON.stringify(row))
            axios.get(`http://localhost:8080/user/${row.id}`).then(res => {
                if (res.data.code === 1) {
                    this.ruleForm = res.data.data;

                    if (res.data.data.gender === 1) {
                        this.ruleForm.gender = '男';
                    } else if (res.data.gender === 2) {
                        this.ruleForm.gender = '女';
                    }

                    if (res.data.data.root === 1) {
                        this.ruleForm.root = '管理员';
                    } else {
                        this.ruleForm.root = '一般用户';
                    }

                }
            });

        },
        submitForm() {
            if (this.optType === 'add') {
                if(this.ruleForm.password == null || this.ruleForm.password == ''){
                    this.ruleForm.password = '123456';
                }
                //alert(this.ruleForm.name);
                axios({
                    method: "post",
                    url: "http://localhost:8080/user/save",
                    data: {
                        "id": "",
                        "name": this.ruleForm.name,
                        "userName": this.ruleForm.userName,
                        "password": this.ruleForm.password,
                        "gender": this.ruleForm.gender,
                        "root": this.ruleForm.root
                    }
                }).then(res => {
                    // 请求成功状态为1说明添加成功
                    if (res.data.code === 1) {
                        // 关闭用户新增表单弹窗
                        this.dialogFormVisible = false,
                        // 添加成功提示
                        this.$message({ showClose: true, message: '添加成功！', type: 'success', duration: 2000, center: true });
                        // 重新刷新列表数据
                        this.queryUserList();
                    }
                }).catch(error => {
                    console.log(error)
                })
            }

            if (this.optType === 'update') {
                var date = this.ruleForm;
                if (date.gender === '男') {
                    date.gender = 1;
                } else {
                    date.gender = 2;
                }

                /* if(date.root === '管理员'){
                    date.root = 1;
                }else{
                    date.root = 2;
                } */
                date.root = date.root === '管理员' ? 1 : 2;

                axios.put('http://localhost:8080/user', this.ruleForm).then(res => {
                    if (res.data.code === 1) {
                        this.dialogFormVisible = false;
                        this.$message({ showClose: true, message: '修改成功！', type: 'success', duration: 2000, center: true });
                        this.queryUserList();
                    }
                })
            }
        },
        queryUserList() {
            axios.get('http://localhost:8080/user',{
                params: {
                name: this.searchFrom.name,
                gender: this.searchFrom.gender,
                page: this.page,
                pageSize: this.pageSize
            }
            }).then((res) => {
                this.tableData = res.data.data;
            })
        },
        handleDeleteUser(row) {
            //alert(row.id);
            axios.delete(`http://localhost:8080/user/${row.id}`).then(res => {
                if (res.data.code === 1) {
                    this.queryUserList();
                    this.$message({ showClose: true, message: '删除成功！', type: 'success', duration: 2000, center: true });
                }
            })

        }

    }
}
</script>

<style></style>