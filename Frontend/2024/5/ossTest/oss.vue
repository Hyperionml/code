<template>
  <div>  
    <h2>文件上传</h2>  
    <form @submit.prevent="handleFileUpload">  
      <input type="file" @change="onFileChange" ref="fileInput">  
      <button type="submit">上传文件</button>  
    </form>  
    <div v-if="uploadedFileName">  
      文件已上传: {{ uploadedFileName }}  
    </div>  
  </div>  
</template>

<script>
export default {
    data() {  
    return {  
      selectedFile: null,  
      uploadedFileName: ''  
    };  
  },  
  methods: {  
    onFileChange(e) {  
      // 获取选择的文件  
      this.selectedFile = e.target.files[0];  
    },  
    handleFileUpload() {  
      // 在这里，你可以使用选定的文件（this.selectedFile）进行上传操作  
      // 这里仅作为示例，我们仅将文件名存储在 uploadedFileName 中  
      if (this.selectedFile) {  
        this.uploadedFileName = this.selectedFile.name;  
        // 实际情况下，你会在这里调用 API 来上传文件  
        this.uploadFileToServer(this.selectedFile);  
      }  
    },  
    // 假设你有一个上传文件的函数（这里省略了实现细节）  
    uploadFileToServer(file) {  
      // 使用 axios、fetch 或其他库上传文件

    }  
  }  
}
</script>

<style>

</style>