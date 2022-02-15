<template> 
  <div>
    <el-upload :action="useOss?ossUploadUrl:minioUploadUrl" :data="useOss?dataObj:null" list-type="picture-card" :file-list="fileList" :before-upload="beforeUpload" :on-remove="handleRemove" :on-success="handleUploadSuccess" :on-preview="handlePreview" :limit="maxCount" :on-exceed="handleExceed">
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>
<script>
import { policy } from "./policy";

export default {
  name: "multiUpload",
  props: {
    //圖片屬性數組
    value: Array,
    //最大上傳圖片數量
    maxCount: {
      type: Number,
      default: 5,
    },
  },
  data() {
    return {
      dataObj: {
        policy: "",
        signature: "",
        key: "",
        ossaccessKeyId: "",
        dir: "",
        host: "",
      },
      dialogVisible: false,
      dialogImageUrl: null,
      useOss: false, //使用oss->true;使用MinIO->false
      ossUploadUrl: "http://macro-oss.oss-cn-shenzhen.aliyuncs.com",
      minioUploadUrl: "http://localhost:88/api/third-party/minio/upload",
    };
  },
  computed: {
    fileList() {
      let fileList = [];
      for (let i = 0; i < this.value.length; i++) {
        fileList.push({ url: this.value[i] });
      }
      return fileList;
    },
  },
  methods: {
    emitInput(fileList) {
      let value = [];
      for (let i = 0; i < fileList.length; i++) {
        value.push(fileList[i].url);
      }
      this.$emit("input", value);
    },
    handleRemove(file, fileList) {
      this.emitInput(fileList);
    },
    handlePreview(file) {
      this.dialogVisible = true;
      this.dialogImageUrl = file.url;
    },
    beforeUpload(file) {
      let _self = this;
      if (!this.useOss) {
        //不使用oss不需要獲取策略
        return true;
      }
      return new Promise((resolve, reject) => {
        policy()
          .then((response) => {
            _self.dataObj.policy = response.data.policy;
            _self.dataObj.signature = response.data.signature;
            _self.dataObj.ossaccessKeyId = response.data.accessKeyId;
            _self.dataObj.key = response.data.dir + "/${filename}";
            _self.dataObj.dir = response.data.dir;
            _self.dataObj.host = response.data.host;
            resolve(true);
          })
          .catch((err) => {
            console.log(err);
            reject(false);
          });
      });
    },
    handleUploadSuccess(res, file) {
      let url = this.dataObj.host + "/" + this.dataObj.dir + "/" + file.name;
      if (!this.useOss) {
        //不使用oss直接獲取圖片路徑
        url = res.data.url;
      }
      this.fileList.push({ name: file.name, url: url });
      this.emitInput(this.fileList);
    },
    handleExceed(files, fileList) {
      this.$message({
        message: "最多隻能上傳" + this.maxCount + "張圖片",
        type: "warning",
        duration: 1000,
      });
    },
  },
};
</script>
<style>
</style>


