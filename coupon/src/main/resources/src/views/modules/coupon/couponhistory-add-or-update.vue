<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="優惠券id" prop="couponId">
      <el-input v-model="dataForm.couponId" placeholder="優惠券id"></el-input>
    </el-form-item>
    <el-form-item label="會員id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="會員id"></el-input>
    </el-form-item>
    <el-form-item label="會員名字" prop="memberNickName">
      <el-input v-model="dataForm.memberNickName" placeholder="會員名字"></el-input>
    </el-form-item>
    <el-form-item label="獲取方式[0->後台贈送；1->主動領取]" prop="getType">
      <el-input v-model="dataForm.getType" placeholder="獲取方式[0->後台贈送；1->主動領取]"></el-input>
    </el-form-item>
    <el-form-item label="創建時間" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="創建時間"></el-input>
    </el-form-item>
    <el-form-item label="使用狀態[0->未使用；1->已使用；2->已過期]" prop="useType">
      <el-input v-model="dataForm.useType" placeholder="使用狀態[0->未使用；1->已使用；2->已過期]"></el-input>
    </el-form-item>
    <el-form-item label="使用時間" prop="useTime">
      <el-input v-model="dataForm.useTime" placeholder="使用時間"></el-input>
    </el-form-item>
    <el-form-item label="訂單id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="訂單id"></el-input>
    </el-form-item>
    <el-form-item label="訂單號" prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="訂單號"></el-input>
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
          couponId: '',
          memberId: '',
          memberNickName: '',
          getType: '',
          createTime: '',
          useType: '',
          useTime: '',
          orderId: '',
          orderSn: ''
        },
        dataRule: {
          couponId: [
            { required: true, message: '優惠券id不能为空', trigger: 'blur' }
          ],
          memberId: [
            { required: true, message: '會員id不能为空', trigger: 'blur' }
          ],
          memberNickName: [
            { required: true, message: '會員名字不能为空', trigger: 'blur' }
          ],
          getType: [
            { required: true, message: '獲取方式[0->後台贈送；1->主動領取]不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '創建時間不能为空', trigger: 'blur' }
          ],
          useType: [
            { required: true, message: '使用狀態[0->未使用；1->已使用；2->已過期]不能为空', trigger: 'blur' }
          ],
          useTime: [
            { required: true, message: '使用時間不能为空', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: '訂單id不能为空', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: '訂單號不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/couponhistory/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.couponId = data.couponHistory.couponId
                this.dataForm.memberId = data.couponHistory.memberId
                this.dataForm.memberNickName = data.couponHistory.memberNickName
                this.dataForm.getType = data.couponHistory.getType
                this.dataForm.createTime = data.couponHistory.createTime
                this.dataForm.useType = data.couponHistory.useType
                this.dataForm.useTime = data.couponHistory.useTime
                this.dataForm.orderId = data.couponHistory.orderId
                this.dataForm.orderSn = data.couponHistory.orderSn
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
              url: this.$http.adornUrl(`/coupon/couponhistory/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'couponId': this.dataForm.couponId,
                'memberId': this.dataForm.memberId,
                'memberNickName': this.dataForm.memberNickName,
                'getType': this.dataForm.getType,
                'createTime': this.dataForm.createTime,
                'useType': this.dataForm.useType,
                'useTime': this.dataForm.useTime,
                'orderId': this.dataForm.orderId,
                'orderSn': this.dataForm.orderSn
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
