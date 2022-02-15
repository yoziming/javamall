<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="等級名稱" prop="name">
        <el-input v-model="dataForm.name" placeholder="等級名稱"></el-input>
      </el-form-item>
      <el-form-item label="所需成長值" prop="growthPoint">
        <el-input-number v-model="dataForm.growthPoint" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="默認等級" prop="defaultStatus">
        <el-checkbox v-model="dataForm.defaultStatus" :true-label="1" :false-label="0"></el-checkbox>
      </el-form-item>
      <el-form-item label="免運費標準" prop="freeFreightPoint">
        <el-input-number :min="0" v-model="dataForm.freeFreightPoint"></el-input-number>
      </el-form-item>
      <el-form-item label="每次評價獲取的成長值" prop="commentGrowthPoint">
        <el-input-number :min="0" v-model="dataForm.commentGrowthPoint"></el-input-number>
      </el-form-item>
      <el-form-item label="是否有免郵特權" prop="priviledgeFreeFreight">
        <el-checkbox v-model="dataForm.priviledgeFreeFreight" :true-label="1" :false-label="0"></el-checkbox>
      </el-form-item>
      <el-form-item label="是否有會員價格特權" prop="priviledgeMemberPrice">
        <el-checkbox v-model="dataForm.priviledgeMemberPrice" :true-label="1" :false-label="0"></el-checkbox>
      </el-form-item>
      <el-form-item label="是否有生日特權" prop="priviledgeBirthday">
        <el-checkbox v-model="dataForm.priviledgeBirthday" :true-label="1" :false-label="0"></el-checkbox>
      </el-form-item>
      <el-form-item label="備註" prop="note">
        <el-input v-model="dataForm.note" placeholder="備註"></el-input>
      </el-form-item>
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
      dataForm: {
        id: 0,
        name: "",
        growthPoint: 0,
        defaultStatus: 0,
        freeFreightPoint: 0,
        commentGrowthPoint: 0,
        priviledgeFreeFreight: 0,
        priviledgeMemberPrice: 0,
        priviledgeBirthday: 0,
        note: "",
      },
      dataRule: {
        name: [
          { required: true, message: "等級名稱不能為空", trigger: "blur" },
        ],
        growthPoint: [
          {
            required: true,
            message: "等級需要的成長值不能為空",
            trigger: "blur",
          },
        ],
        defaultStatus: [
          {
            required: true,
            message: "是否為默認等級[0->不是；1->是]不能為空",
            trigger: "blur",
          },
        ],
        freeFreightPoint: [
          { required: true, message: "免運費標準不能為空", trigger: "blur" },
        ],
        commentGrowthPoint: [
          {
            required: true,
            message: "每次評價獲取的成長值不能為空",
            trigger: "blur",
          },
        ],
        priviledgeFreeFreight: [
          {
            required: true,
            message: "是否有免郵特權不能為空",
            trigger: "blur",
          },
        ],
        priviledgeMemberPrice: [
          {
            required: true,
            message: "是否有會員價格特權不能為空",
            trigger: "blur",
          },
        ],
        priviledgeBirthday: [
          {
            required: true,
            message: "是否有生日特權不能為空",
            trigger: "blur",
          },
        ],
        note: [{ required: true, message: "備註不能為空", trigger: "blur" }],
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
              `/member/memberlevel/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.memberLevel.name;
              this.dataForm.growthPoint = data.memberLevel.growthPoint || 0;
              this.dataForm.defaultStatus = data.memberLevel.defaultStatus || 0;
              this.dataForm.freeFreightPoint =
                data.memberLevel.freeFreightPoint || 0;
              this.dataForm.commentGrowthPoint =
                data.memberLevel.commentGrowthPoint || 0;
              this.dataForm.priviledgeFreeFreight =
                data.memberLevel.priviledgeFreeFreight || 0;
              this.dataForm.priviledgeMemberPrice =
                data.memberLevel.priviledgeMemberPrice || 0;
              this.dataForm.priviledgeBirthday =
                data.memberLevel.priviledgeBirthday || 0;
              this.dataForm.note = data.memberLevel.note;
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
              `/member/memberlevel/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              name: this.dataForm.name,
              growthPoint: this.dataForm.growthPoint,
              defaultStatus: this.dataForm.defaultStatus,
              freeFreightPoint: this.dataForm.freeFreightPoint,
              commentGrowthPoint: this.dataForm.commentGrowthPoint,
              priviledgeFreeFreight: this.dataForm.priviledgeFreeFreight,
              priviledgeMemberPrice: this.dataForm.priviledgeMemberPrice,
              priviledgeBirthday: this.dataForm.priviledgeBirthday,
              note: this.dataForm.note,
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
