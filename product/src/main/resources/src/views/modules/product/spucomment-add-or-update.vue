<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="sku_id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
    </el-form-item>
    <el-form-item label="spu_id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu_id"></el-input>
    </el-form-item>
    <el-form-item label="商品名字" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="商品名字"></el-input>
    </el-form-item>
    <el-form-item label="會員暱稱" prop="memberNickName">
      <el-input v-model="dataForm.memberNickName" placeholder="會員暱稱"></el-input>
    </el-form-item>
    <el-form-item label="星級" prop="star">
      <el-input v-model="dataForm.star" placeholder="星級"></el-input>
    </el-form-item>
    <el-form-item label="會員ip" prop="memberIp">
      <el-input v-model="dataForm.memberIp" placeholder="會員ip"></el-input>
    </el-form-item>
    <el-form-item label="創建時間" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="創建時間"></el-input>
    </el-form-item>
    <el-form-item label="顯示狀態[0-不顯示，1-顯示]" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="顯示狀態[0-不顯示，1-顯示]"></el-input>
    </el-form-item>
    <el-form-item label="購買時屬性組合" prop="spuAttributes">
      <el-input v-model="dataForm.spuAttributes" placeholder="購買時屬性組合"></el-input>
    </el-form-item>
    <el-form-item label="點贊數" prop="likesCount">
      <el-input v-model="dataForm.likesCount" placeholder="點贊數"></el-input>
    </el-form-item>
    <el-form-item label="回覆數" prop="replyCount">
      <el-input v-model="dataForm.replyCount" placeholder="回覆數"></el-input>
    </el-form-item>
    <el-form-item label="評論圖片/視頻[json數據；[{type:文件類型,url:資源路徑}]]" prop="resources">
      <el-input v-model="dataForm.resources" placeholder="評論圖片/視頻[json數據；[{type:文件類型,url:資源路徑}]]"></el-input>
    </el-form-item>
    <el-form-item label="內容" prop="content">
      <el-input v-model="dataForm.content" placeholder="內容"></el-input>
    </el-form-item>
    <el-form-item label="用户頭像" prop="memberIcon">
      <el-input v-model="dataForm.memberIcon" placeholder="用户頭像"></el-input>
    </el-form-item>
    <el-form-item label="評論類型[0 - 對商品的直接評論，1 - 對評論的回覆]" prop="commentType">
      <el-input v-model="dataForm.commentType" placeholder="評論類型[0 - 對商品的直接評論，1 - 對評論的回覆]"></el-input>
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
          skuId: '',
          spuId: '',
          spuName: '',
          memberNickName: '',
          star: '',
          memberIp: '',
          createTime: '',
          showStatus: '',
          spuAttributes: '',
          likesCount: '',
          replyCount: '',
          resources: '',
          content: '',
          memberIcon: '',
          commentType: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'sku_id不能为空', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu_id不能为空', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: '商品名字不能为空', trigger: 'blur' }
          ],
          memberNickName: [
            { required: true, message: '會員暱稱不能为空', trigger: 'blur' }
          ],
          star: [
            { required: true, message: '星級不能为空', trigger: 'blur' }
          ],
          memberIp: [
            { required: true, message: '會員ip不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '創建時間不能为空', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: '顯示狀態[0-不顯示，1-顯示]不能为空', trigger: 'blur' }
          ],
          spuAttributes: [
            { required: true, message: '購買時屬性組合不能为空', trigger: 'blur' }
          ],
          likesCount: [
            { required: true, message: '點贊數不能为空', trigger: 'blur' }
          ],
          replyCount: [
            { required: true, message: '回覆數不能为空', trigger: 'blur' }
          ],
          resources: [
            { required: true, message: '評論圖片/視頻[json數據；[{type:文件類型,url:資源路徑}]]不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '內容不能为空', trigger: 'blur' }
          ],
          memberIcon: [
            { required: true, message: '用户頭像不能为空', trigger: 'blur' }
          ],
          commentType: [
            { required: true, message: '評論類型[0 - 對商品的直接評論，1 - 對評論的回覆]不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/product/spucomment/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.spuComment.skuId
                this.dataForm.spuId = data.spuComment.spuId
                this.dataForm.spuName = data.spuComment.spuName
                this.dataForm.memberNickName = data.spuComment.memberNickName
                this.dataForm.star = data.spuComment.star
                this.dataForm.memberIp = data.spuComment.memberIp
                this.dataForm.createTime = data.spuComment.createTime
                this.dataForm.showStatus = data.spuComment.showStatus
                this.dataForm.spuAttributes = data.spuComment.spuAttributes
                this.dataForm.likesCount = data.spuComment.likesCount
                this.dataForm.replyCount = data.spuComment.replyCount
                this.dataForm.resources = data.spuComment.resources
                this.dataForm.content = data.spuComment.content
                this.dataForm.memberIcon = data.spuComment.memberIcon
                this.dataForm.commentType = data.spuComment.commentType
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
              url: this.$http.adornUrl(`/product/spucomment/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName,
                'memberNickName': this.dataForm.memberNickName,
                'star': this.dataForm.star,
                'memberIp': this.dataForm.memberIp,
                'createTime': this.dataForm.createTime,
                'showStatus': this.dataForm.showStatus,
                'spuAttributes': this.dataForm.spuAttributes,
                'likesCount': this.dataForm.likesCount,
                'replyCount': this.dataForm.replyCount,
                'resources': this.dataForm.resources,
                'content': this.dataForm.content,
                'memberIcon': this.dataForm.memberIcon,
                'commentType': this.dataForm.commentType
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
