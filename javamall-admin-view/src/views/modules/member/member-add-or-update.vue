<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="暱稱" prop="nickname">
        <el-input v-model="dataForm.nickname" placeholder="暱稱"></el-input>
      </el-form-item>
      <el-form-item label="手機號碼" prop="mobile">
        <el-input v-model="dataForm.mobile" placeholder="手機號碼"></el-input>
      </el-form-item>
      <el-form-item label="郵箱" prop="email">
        <el-input v-model="dataForm.email" placeholder="郵箱"></el-input>
      </el-form-item>
      <el-form-item label="頭像" prop="header">
        <el-input v-model="dataForm.header" placeholder="頭像"></el-input>
      </el-form-item>
      <el-form-item label="性別" prop="gender">
        <el-input v-model="dataForm.gender" placeholder="性別"></el-input>
      </el-form-item>
      <el-form-item label="生日" prop="birth">
        <el-input v-model="dataForm.birth" placeholder="生日"></el-input>
      </el-form-item>
      <el-form-item label="所在城市" prop="city">
        <el-input v-model="dataForm.city" placeholder="所在城市"></el-input>
      </el-form-item>
      <el-form-item label="職業" prop="job">
        <el-input v-model="dataForm.job" placeholder="職業"></el-input>
      </el-form-item>
      <el-form-item label="個性簽名" prop="sign">
        <el-input v-model="dataForm.sign" placeholder="個性簽名"></el-input>
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
        levelId: "",
        username: "",
        password: "",
        nickname: "",
        mobile: "",
        email: "",
        header: "",
        gender: "",
        birth: "",
        city: "",
        job: "",
        sign: "",
        sourceType: "",
        integration: "",
        growth: "",
        status: "",
        createTime: "",
      },
      dataRule: {
        username: [
          { required: true, message: "用户名不能為空", trigger: "blur" },
        ],
        mobile: [
          { required: true, message: "手機號碼不能為空", trigger: "blur" },
        ],
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
            url: this.$http.adornUrl(`/member/member/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.levelId = data.member.levelId;
              this.dataForm.username = data.member.username;
              this.dataForm.password = data.member.password;
              this.dataForm.nickname = data.member.nickname;
              this.dataForm.mobile = data.member.mobile;
              this.dataForm.email = data.member.email;
              this.dataForm.header = data.member.header;
              this.dataForm.gender = data.member.gender;
              this.dataForm.birth = data.member.birth;
              this.dataForm.city = data.member.city;
              this.dataForm.job = data.member.job;
              this.dataForm.sign = data.member.sign;
              this.dataForm.sourceType = data.member.sourceType;
              this.dataForm.integration = data.member.integration;
              this.dataForm.growth = data.member.growth;
              this.dataForm.status = data.member.status;
              this.dataForm.createTime = data.member.createTime;
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
              `/member/member/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              levelId: this.dataForm.levelId,
              username: this.dataForm.username,
              password: this.dataForm.password,
              nickname: this.dataForm.nickname,
              mobile: this.dataForm.mobile,
              email: this.dataForm.email,
              header: this.dataForm.header,
              gender: this.dataForm.gender,
              birth: this.dataForm.birth,
              city: this.dataForm.city,
              job: this.dataForm.job,
              sign: this.dataForm.sign,
              sourceType: this.dataForm.sourceType,
              integration: this.dataForm.integration,
              growth: this.dataForm.growth,
              status: this.dataForm.status,
              createTime: this.dataForm.createTime,
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
