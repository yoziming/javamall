<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="採購商品id" prop="skuId">
        <el-input v-model="dataForm.skuId" placeholder="採購商品id"></el-input>
      </el-form-item>
      <el-form-item label="採購數量" prop="skuNum">
        <el-input v-model="dataForm.skuNum" placeholder="採購數量"></el-input>
      </el-form-item>
      <el-form-item label="倉庫" prop="wareId">
        <el-select v-model="dataForm.wareId" placeholder="請選擇倉庫" clearable>
          <el-option :label="w.name" :value="w.id" v-for="w in wareList" :key="w.id"></el-option>
        </el-select>
      </el-form-item>
      <!-- [0新建，1已分配，2正在採購，3已完成，4採購失敗] -->
      <!-- <el-form-item label="狀態" prop="status">
        <el-select v-model="dataForm.status" placeholder="請選擇狀態" clearable>
          <el-option label="新建" :value="0"></el-option>
          <el-option label="已分配" :value="1"></el-option>
          <el-option label="正在採購" :value="2"></el-option>
          <el-option label="已完成" :value="3"></el-option>
          <el-option label="採購失敗" :value="4"></el-option>
        </el-select>
      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">確定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      wareList: [],
      dataForm: {
        id: 0,
        purchaseId: "",
        skuId: "",
        skuNum: "",
        skuPrice: "",
        wareId: "",
        status: 0,
      },
      dataRule: {
        skuId: [
          { required: true, message: "採購商品id不能為空", trigger: "blur" },
        ],
        skuNum: [
          { required: true, message: "採購數量不能為空", trigger: "blur" },
        ],
        wareId: [
          { required: true, message: "倉庫id不能為空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getWares();
  },
  methods: {
    getWares() {
      this.$http({
        url: this.$http.adornUrl("/ware/wareinfo/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 500,
        }),
      }).then(({ data }) => {
        this.wareList = data.page.list;
      });
    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(
              `/ware/purchasedetail/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.purchaseId = data.purchaseDetail.purchaseId;
              this.dataForm.skuId = data.purchaseDetail.skuId;
              this.dataForm.skuNum = data.purchaseDetail.skuNum;
              this.dataForm.skuPrice = data.purchaseDetail.skuPrice;
              this.dataForm.wareId = data.purchaseDetail.wareId;
              this.dataForm.status = data.purchaseDetail.status;
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
              `/ware/purchasedetail/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              purchaseId: this.dataForm.purchaseId,
              skuId: this.dataForm.skuId,
              skuNum: this.dataForm.skuNum,
              skuPrice: this.dataForm.skuPrice,
              wareId: this.dataForm.wareId,
              status: this.dataForm.status,
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
