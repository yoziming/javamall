<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="名字" prop="name">
        <el-input v-model="dataForm.name" placeholder="名字"></el-input>
      </el-form-item>
      <el-form-item label="圖片地址" prop="pic">
        <single-upload v-model="dataForm.pic"></single-upload>
      </el-form-item>
      <el-form-item label="狀態" prop="status">
        <el-tooltip :content="'是否開啓：' + dataForm.status" placement="top">
          <el-switch v-model="dataForm.status" active-color="#13ce66" inactive-color="#ff4949" :active-value='1' :inactive-value='0'>
          </el-switch>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="運營位置" prop="type">
        <el-select v-model="dataForm.type" placeholder="必填">
          <el-option label="輪播圖" :value="1"></el-option>
          <el-option label="品牌製造商" :value="2"></el-option>
          <el-option label="新品" :value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="連結URL" prop="url">
        <el-input v-model="dataForm.url" placeholder="網址"></el-input>
      </el-form-item>
      <el-form-item label="備註" prop="note">
        <el-input v-model="dataForm.note" placeholder="備註"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="dataForm.sort" :min="1" :max="100" label="排序"></el-input-number>
      </el-form-item>

    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">確定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import SingleUpload from "@/components/upload/singleUpload";
export default {
  components: { SingleUpload },
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: "",
        pic: "",
        startTime: "",
        endTime: "",
        status: "",
        type: "",
        clickCount: "",
        url: "",
        note: "",
        sort: "",
        publisherId: "",
        authId: "",
      },
      dataRule: {
        pic: [{ required: true, message: "圖片地址不能為空", trigger: "blur" }],
        status: [{ required: true, message: "狀態不能為空", trigger: "blur" }],

        sort: [{ required: true, message: "排序不能為空", trigger: "blur" }],
      },
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/homeadv/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.homeAdv.name;
              this.dataForm.pic = data.homeAdv.pic;
              this.dataForm.startTime = data.homeAdv.startTime;
              this.dataForm.endTime = data.homeAdv.endTime;
              this.dataForm.status = data.homeAdv.status;
              this.dataForm.type = data.homeAdv.type;
              this.dataForm.clickCount = data.homeAdv.clickCount;
              this.dataForm.url = data.homeAdv.url;
              this.dataForm.note = data.homeAdv.note;
              this.dataForm.sort = data.homeAdv.sort;
              this.dataForm.publisherId = data.homeAdv.publisherId;
              this.dataForm.authId = data.homeAdv.authId;
            }
          });
        }
      });
    },
    // 表單提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/homeadv/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              name: this.dataForm.name,
              pic: this.dataForm.pic,
              startTime: this.dataForm.startTime,
              endTime: this.dataForm.endTime,
              status: this.dataForm.status,
              type: this.dataForm.type,
              clickCount: this.dataForm.clickCount,
              url: this.dataForm.url,
              note: this.dataForm.note,
              sort: this.dataForm.sort,
              publisherId: this.dataForm.publisherId,
              authId: this.dataForm.authId,
            }),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                },
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
  },
};
</script>
