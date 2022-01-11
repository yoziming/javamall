<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="場次名稱" prop="name">
      <el-input v-model="dataForm.name" placeholder="場次名稱"></el-input>
    </el-form-item>
    <el-form-item label="每日開始時間" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="每日開始時間"></el-input>
    </el-form-item>
    <el-form-item label="每日結束時間" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="每日結束時間"></el-input>
    </el-form-item>
    <el-form-item label="啓用狀態" prop="status">
      <el-input v-model="dataForm.status" placeholder="啓用狀態"></el-input>
    </el-form-item>
    <el-form-item label="創建時間" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="創建時間"></el-input>
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
          name: '',
          startTime: '',
          endTime: '',
          status: '',
          createTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '場次名稱不能为空', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '每日開始時間不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '每日結束時間不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '啓用狀態不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '創建時間不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/seckillsession/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.seckillSession.name
                this.dataForm.startTime = data.seckillSession.startTime
                this.dataForm.endTime = data.seckillSession.endTime
                this.dataForm.status = data.seckillSession.status
                this.dataForm.createTime = data.seckillSession.createTime
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
              url: this.$http.adornUrl(`/coupon/seckillsession/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime
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
