<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="會員等級id" prop="levelId">
      <el-input v-model="dataForm.levelId" placeholder="會員等級id"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="密碼" prop="password">
      <el-input v-model="dataForm.password" placeholder="密碼"></el-input>
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
    <el-form-item label="用户來源" prop="sourceType">
      <el-input v-model="dataForm.sourceType" placeholder="用户來源"></el-input>
    </el-form-item>
    <el-form-item label="積分" prop="integration">
      <el-input v-model="dataForm.integration" placeholder="積分"></el-input>
    </el-form-item>
    <el-form-item label="成長值" prop="growth">
      <el-input v-model="dataForm.growth" placeholder="成長值"></el-input>
    </el-form-item>
    <el-form-item label="啓用狀態" prop="status">
      <el-input v-model="dataForm.status" placeholder="啓用狀態"></el-input>
    </el-form-item>
    <el-form-item label="註冊時間" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="註冊時間"></el-input>
    </el-form-item>
    <el-form-item label="社交登錄UID" prop="socialUid">
      <el-input v-model="dataForm.socialUid" placeholder="社交登錄UID"></el-input>
    </el-form-item>
    <el-form-item label="社交登錄TOKEN" prop="accessToken">
      <el-input v-model="dataForm.accessToken" placeholder="社交登錄TOKEN"></el-input>
    </el-form-item>
    <el-form-item label="社交登錄過期時間" prop="expiresIn">
      <el-input v-model="dataForm.expiresIn" placeholder="社交登錄過期時間"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          levelId: '',
          username: '',
          password: '',
          nickname: '',
          mobile: '',
          email: '',
          header: '',
          gender: '',
          birth: '',
          city: '',
          job: '',
          sign: '',
          sourceType: '',
          integration: '',
          growth: '',
          status: '',
          createTime: '',
          socialUid: '',
          accessToken: '',
          expiresIn: ''
        },
        dataRule: {
          levelId: [
            { required: true, message: '會員等級id不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密碼不能为空', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '暱稱不能为空', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '手機號碼不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '郵箱不能为空', trigger: 'blur' }
          ],
          header: [
            { required: true, message: '頭像不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性別不能为空', trigger: 'blur' }
          ],
          birth: [
            { required: true, message: '生日不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '所在城市不能为空', trigger: 'blur' }
          ],
          job: [
            { required: true, message: '職業不能为空', trigger: 'blur' }
          ],
          sign: [
            { required: true, message: '個性簽名不能为空', trigger: 'blur' }
          ],
          sourceType: [
            { required: true, message: '用户來源不能为空', trigger: 'blur' }
          ],
          integration: [
            { required: true, message: '積分不能为空', trigger: 'blur' }
          ],
          growth: [
            { required: true, message: '成長值不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '啓用狀態不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '註冊時間不能为空', trigger: 'blur' }
          ],
          socialUid: [
            { required: true, message: '社交登錄UID不能为空', trigger: 'blur' }
          ],
          accessToken: [
            { required: true, message: '社交登錄TOKEN不能为空', trigger: 'blur' }
          ],
          expiresIn: [
            { required: true, message: '社交登錄過期時間不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/member/member/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.levelId = data.member.levelId
                this.dataForm.username = data.member.username
                this.dataForm.password = data.member.password
                this.dataForm.nickname = data.member.nickname
                this.dataForm.mobile = data.member.mobile
                this.dataForm.email = data.member.email
                this.dataForm.header = data.member.header
                this.dataForm.gender = data.member.gender
                this.dataForm.birth = data.member.birth
                this.dataForm.city = data.member.city
                this.dataForm.job = data.member.job
                this.dataForm.sign = data.member.sign
                this.dataForm.sourceType = data.member.sourceType
                this.dataForm.integration = data.member.integration
                this.dataForm.growth = data.member.growth
                this.dataForm.status = data.member.status
                this.dataForm.createTime = data.member.createTime
                this.dataForm.socialUid = data.member.socialUid
                this.dataForm.accessToken = data.member.accessToken
                this.dataForm.expiresIn = data.member.expiresIn
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/member/member/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'levelId': this.dataForm.levelId,
                'username': this.dataForm.username,
                'password': this.dataForm.password,
                'nickname': this.dataForm.nickname,
                'mobile': this.dataForm.mobile,
                'email': this.dataForm.email,
                'header': this.dataForm.header,
                'gender': this.dataForm.gender,
                'birth': this.dataForm.birth,
                'city': this.dataForm.city,
                'job': this.dataForm.job,
                'sign': this.dataForm.sign,
                'sourceType': this.dataForm.sourceType,
                'integration': this.dataForm.integration,
                'growth': this.dataForm.growth,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime,
                'socialUid': this.dataForm.socialUid,
                'accessToken': this.dataForm.accessToken,
                'expiresIn': this.dataForm.expiresIn
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
