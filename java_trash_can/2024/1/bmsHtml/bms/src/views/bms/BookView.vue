<template>
    <div>
        <el-container  style="height: 675px; border: 1px solid #eee">
            <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">图书管理系统</el-header>
            <el-container>
                <el-aside width="200px" style="border: 1px solid #eee">

                    <el-menu :default-openeds="['1', '3']" default-active="/book" class="el-menu-vertical-demo" :router="true">
                    <el-submenu index="1">
                        <template slot="title"><i class="el-icon-message"></i>导航栏</template>
                        <el-menu-item-group>
                            <el-menu-item index="/user">
                                用户管理
                            </el-menu-item>
                            <el-menu-item index="/book" >
                                图书管理
                            </el-menu-item>
                        </el-menu-item-group>
                           
                    </el-submenu>
                    
                    </el-menu>

                </el-aside>
                <el-main>
                    
                    <!-- 表单 -->
                    <el-form :inline="true" :model="searchFrom" class="demo-form-inline">
                        <el-form-item label="书名">
                            <el-input v-model="searchFrom.name" placeholder="书名"></el-input>
                        </el-form-item>

                        <el-form-item label="作者">
                            <el-input v-model="searchFrom.author" placeholder="作者"></el-input>
                        </el-form-item>

                        <el-form-item label="出版社">
                            <el-input v-model="searchFrom.press" placeholder="出版社"></el-input>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">查询</el-button>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="handleAddBook">+添加书籍</el-button>
                        </el-form-item>
                    </el-form>

                    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                            <el-form-item label="书名" prop="name">
                                <el-input v-model="ruleForm.name"></el-input>
                            </el-form-item>

                            <el-form-item label="作者" prop="author">
                                <el-input v-model="ruleForm.author"></el-input>
                            </el-form-item>

                            <el-form-item label="出版社" prop="press">
                                <el-input v-model="ruleForm.press"></el-input>
                            </el-form-item>

                            <el-form-item label="库存数量" prop="number">
                                <el-input v-model="ruleForm.number"></el-input>
                            </el-form-item>

                            <el-form-item label="未借数量" prop="state">
                                <el-input v-model="ruleForm.state"></el-input>
                            </el-form-item>


                            <el-form-item>
                                <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                                <el-button @click="resetForm('ruleForm')">重置</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>


                    <el-table :data="tableData" border>
                        <el-table-column prop="name" label="书名" width="140">
                        </el-table-column>
                        <el-table-column prop="author" label="作者" width="120">
                        </el-table-column>
                        <el-table-column prop="press" label="出版社">
                        </el-table-column>
                        <el-table-column prop="number" label="库存数量">
                        </el-table-column>
                        <el-table-column prop="state" label="未借数量">
                        </el-table-column>

                        <el-table-column label="操作" >
                            <template slot-scope="scope">
                                <el-button type="primary" size="mini" @click="handleUpdateBook(scope.row)">编辑</el-button>
                                <el-button type="danger" size="mini" @click="handleDeleteBook(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>

                    </el-table>
                
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data () {
        return {
            optType: '',
            dialogFormVisible: false,
            tableData: [
                {
                    "name": "云边有个小卖部",
                    "author": "张嘉佳",
                    "press": "湖南文艺出版社",
                    "number": "5",
                    "state": "5"
                },
                {
                    "name": "从你的全世界路过",
                    "author": "张嘉佳",
                    "press": "湖南文艺出版社",
                    "number": "5",
                    "state": "5"
                }
            ],
            searchFrom: {
                name: '',
                author: '',
                press: '',
            },
            ruleForm: {
                id: '',
                name: '',
                author: '',
                press: '',
                number: '',
                state: ''
            },
            rules: {
                name: [
                    { required: true, message: '请输入书名', trigger: 'blur' },
                ],
                author: [
                    { required: true, message: '请输入作者', trigger: 'blur' },
                ],
                press: [
                    { required: true, message: '请输入出版社', trigger: 'blur' },
                ],
                number: [
                    { required: true, message: '请输入库存数量', trigger: 'blur' },
                ],
                state: [
                    { required: true, message: '请输入未借数量', trigger: 'blur' },
                ],
            },
            
        }
    },
    mounted(){
        this.queryBookList();
    },
    methods: {
        onSubmit(){
            this.queryBookList();
        },
        queryBookList(){
            axios.get('http://localhost:8080/book', {
                params:{
                    name: this.searchFrom.name,
                    author: this.searchFrom.author,
                    press: this.searchFrom.press
                }
            }).then(res => {
                this.tableData = res.data.data;
            })
        },
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        handleAddBook() {
            this.optType = 'add';
            //路由跳转到新增组件
            this.dialogFormVisible = true;
            this.ruleForm.id = '',
            this.ruleForm.name = '',
            this.ruleForm.author = '',
            this.ruleForm.press = '',
            this.ruleForm.number = '',
            this.ruleForm.state = ''
        },
        handleUpdateBook(row){
            this.optType = 'update';
            //路由跳转到新增组件
            this.dialogFormVisible = true;
            axios.get(`http://localhost:8080/book/${row.id}`).then(res => {
                this.ruleForm = res.data.data;
            })
        },
        handleDeleteBook(row){
            axios.delete(`http://localhost:8080/book/${row.id}`).then(res => {
                if (res.data.code === 1) {
                    this.queryBookList();
                    this.$message({ showClose: true, message: '删除成功！', type: 'success', duration: 2000, center: true });
                }
            })
        },
        submitForm(formName){
            if(this.optType === 'add'){
                //alert(JSON.stringify(this.ruleForm));
                this.$refs[formName].validate((valid) => {
                    if(valid){
                        axios({
                            method: "post",
                            url: "http://localhost:8080/book/save",
                            data: {
                                "id": "",
                                "name": this.ruleForm.name,
                                "author": this.ruleForm.author,
                                "press": this.ruleForm.press,
                                "number": this.ruleForm.number,
                                "state": this.ruleForm.state
                            }
                        }
                        ).then(res => {
                            if(res.data.code === 1 && res.data.data != 0){
                                // 关闭用户新增表单弹窗
                                this.dialogFormVisible = false,
                                // 添加成功提示
                                this.$message({ showClose: true, message: '添加成功！', type: 'success', duration: 2000, center: true });
                                // 重新刷新列表数据
                                this.queryBookList();
                            }
                        })
                    }
                })
            }

            if(this.optType === 'update'){
                this.$refs[formName].validate((valid) => {
                    if(valid){
                        axios.put('http://localhost:8080/book', this.ruleForm).then(res => {
                            if(res.data.code === 1 && res.data.data != 0){
                                // 关闭用户新增表单弹窗
                                this.dialogFormVisible = false,
                                // 修改成功提示
                                this.$message({ showClose: true, message: '修改成功！', type: 'success', duration: 2000, center: true });
                                // 重新刷新列表数据
                                this.queryBookList();
                            }
                        })
                    }
                })
            }
        }
    }
}
</script>

<style>

</style>