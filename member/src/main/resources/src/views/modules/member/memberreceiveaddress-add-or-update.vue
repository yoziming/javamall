<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member_id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member_id"></el-input>
    </el-form-item>
    <el-form-item label="收貨人姓名" prop="name">
      <el-input v-model="dataForm.name" placeholder="收貨人姓名"></el-input>
    </el-form-item>
    <el-form-item label="電話" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="電話"></el-input>
    </el-form-item>
    <el-form-item label="郵政編碼" prop="postCode">
      <el-input v-model="dataForm.postCode" placeholder="郵政編碼"></el-input>
    </el-form-item>
    <el-form-item label="省份/直轄市" prop="province">
      <el-input v-model="dataForm.province" placeholder="省份/直轄市"></el-input>
    </el-form-item>
    <el-form-item label="城市" prop="city">
      <el-input v-model="dataForm.city" placeholder="城市"></el-input>
    </el-form-item>
    <el-form-item label="區" prop="region">
      <el-input v-model="dataForm.region" placeholder="區"></el-input>
    </el-form-item>
    <el-form-item label="詳細地址(街道)" prop="detailAddress">
      <el-input v-model="dataForm.detailAddress" placeholder="詳細地址(街道)"></el-input>
    </el-form-item>
    <el-form-item label="省市區代碼" prop="areacode">
      <el-input v-model="dataForm.areacode" placeholder="省市區代碼"></el-input>
    </el-form-item>
    <el-form-item label="是否默認" prop="defaultStatus">
      <el-input v-model="dataForm.defaultStatus" placeholder="是否默認"></el-input>
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
          memberId: '',
          name: '',
          phone: '',
          postCode: '',
          province: '',
          city: '',
          region: '',
          detailAddress: '',
          areacode: '',
          defaultStatus: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member_id不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '收貨人姓名不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '電話不能为空', trigger: 'blur' }
          ],
          postCode: [
            { required: true, message: '郵政編碼不能为空', trigger: 'blur' }
          ],
          province: [
            { required: true, message: '省份/直轄市不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '城市不能为空', trigger: 'blur' }
          ],
          region: [
            { required: true, message: '區不能为空', trigger: 'blur' }
          ],
          detailAddress: [
            { required: true, message: '詳細地址(街道)不能为空', trigger: 'blur' }
          ],
          areacode: [
            { required: true, message: '省市區代碼不能为空', trigger: 'blur' }
          ],
          defaultStatus: [
            { required: true, message: '是否默認不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberreceiveaddress/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberReceiveAddress.memberId
                this.dataForm.name = data.memberReceiveAddress.name
                this.dataForm.phone = data.memberReceiveAddress.phone
                this.dataForm.postCode = data.memberReceiveAddress.postCode
                this.dataForm.province = data.memberReceiveAddress.province
                this.dataForm.city = data.memberReceiveAddress.city
                this.dataForm.region = data.memberReceiveAddress.region
                this.dataForm.detailAddress = data.memberReceiveAddress.detailAddress
                this.dataForm.areacode = data.memberReceiveAddress.areacode
                this.dataForm.defaultStatus = data.memberReceiveAddress.defaultStatus
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
              url: this.$http.adornUrl(`/member/memberreceiveaddress/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'name': this.dataForm.name,
                'phone': this.dataForm.phone,
                'postCode': this.dataForm.postCode,
                'province': this.dataForm.province,
                'city': this.dataForm.city,
                'region': this.dataForm.region,
                'detailAddress': this.dataForm.detailAddress,
                'areacode': this.dataForm.areacode,
                'defaultStatus': this.dataForm.defaultStatus
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
