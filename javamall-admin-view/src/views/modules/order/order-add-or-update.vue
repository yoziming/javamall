<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="member_id" prop="memberId">
        <el-input v-model="dataForm.memberId" placeholder="member_id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="訂單號" prop="orderSn">
        <el-input v-model="dataForm.orderSn" placeholder="訂單號" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="create_time" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="創建時間" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="memberUsername">
        <el-input v-model="dataForm.memberUsername" placeholder="用户名" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="訂單總額" prop="totalAmount">
        <el-input v-model="dataForm.totalAmount" placeholder="訂單總額" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="應付總額" prop="payAmount">
        <el-input v-model="dataForm.payAmount" placeholder="應付總額" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="運費金額" prop="freightAmount">
        <el-input v-model="dataForm.freightAmount" placeholder="運費金額"></el-input>
      </el-form-item>
      <el-form-item label="訂單狀態" prop="status">
        <el-select v-model="dataForm.status" placeholder="訂單狀態">
          <el-option label="待付款" :value="0"></el-option>
          <el-option label="待發貨" :value="1"></el-option>
          <el-option label="已發貨" :value="2"></el-option>
          <el-option label="已完成" :value="3"></el-option>
          <el-option label="已關閉" :value="4"></el-option>
          <el-option label="售後中" :value="5"></el-option>
          <el-option label="售後完成" :value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="物流公司(配送方式)" prop="deliveryCompany">
        <el-input v-model="dataForm.deliveryCompany" placeholder="物流公司(配送方式)"></el-input>
      </el-form-item>
      <el-form-item label="物流單號" prop="deliverySn">
        <el-input v-model="dataForm.deliverySn" placeholder="物流單號"></el-input>
      </el-form-item>
      <el-form-item label="收貨人姓名" prop="receiverName">
        <el-input v-model="dataForm.receiverName" placeholder="收貨人姓名"></el-input>
      </el-form-item>
      <el-form-item label="收貨人電話" prop="receiverPhone">
        <el-input v-model="dataForm.receiverPhone" placeholder="收貨人電話"></el-input>
      </el-form-item>
      <el-form-item label="收貨人郵編" prop="receiverPostCode">
        <el-input v-model="dataForm.receiverPostCode" placeholder="收貨人郵編"></el-input>
      </el-form-item>
      <el-form-item label="省份/直轄市" prop="receiverProvince">
        <el-input v-model="dataForm.receiverProvince" placeholder="省份/直轄市"></el-input>
      </el-form-item>
      <el-form-item label="城市" prop="receiverCity">
        <el-input v-model="dataForm.receiverCity" placeholder="城市"></el-input>
      </el-form-item>
      <el-form-item label="區" prop="receiverRegion">
        <el-input v-model="dataForm.receiverRegion" placeholder="區"></el-input>
      </el-form-item>
      <el-form-item label="詳細地址" prop="receiverDetailAddress">
        <el-input v-model="dataForm.receiverDetailAddress" placeholder="詳細地址"></el-input>
      </el-form-item>
      <el-form-item label="訂單備註" prop="note">
        <el-input v-model="dataForm.note" placeholder="訂單備註"></el-input>
      </el-form-item>
      <el-form-item label="確認收貨狀態[0->未確認；1->已確認]" prop="confirmStatus">
        <el-input v-model="dataForm.confirmStatus" placeholder="確認收貨狀態[0->未確認；1->已確認]" :disabled="true"></el-input>
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
        memberId: "",
        orderSn: "",
        couponId: "",
        createTime: "",
        memberUsername: "",
        totalAmount: "",
        payAmount: "",
        freightAmount: "",
        promotionAmount: "",
        integrationAmount: "",
        couponAmount: "",
        discountAmount: "",
        payType: "",
        sourceType: "",
        status: "",
        deliveryCompany: "",
        deliverySn: "",
        autoConfirmDay: "",
        integration: "",
        growth: "",
        billType: "",
        billHeader: "",
        billContent: "",
        billReceiverPhone: "",
        billReceiverEmail: "",
        receiverName: "",
        receiverPhone: "",
        receiverPostCode: "",
        receiverProvince: "",
        receiverCity: "",
        receiverRegion: "",
        receiverDetailAddress: "",
        note: "",
        confirmStatus: "",
        deleteStatus: "",
        useIntegration: "",
        paymentTime: "",
        deliveryTime: "",
        receiveTime: "",
        commentTime: "",
        modifyTime: "",
      },
      dataRule: {
        memberId: [
          { required: true, message: "member_id不能為空", trigger: "blur" },
        ],
        orderSn: [
          { required: true, message: "訂單號不能為空", trigger: "blur" },
        ],
        couponId: [
          { required: true, message: "使用的優惠券不能為空", trigger: "blur" },
        ],
        createTime: [
          { required: true, message: "create_time不能為空", trigger: "blur" },
        ],
        memberUsername: [
          { required: true, message: "用户名不能為空", trigger: "blur" },
        ],
        totalAmount: [
          { required: true, message: "訂單總額不能為空", trigger: "blur" },
        ],
        payAmount: [
          { required: true, message: "應付總額不能為空", trigger: "blur" },
        ],
        sourceType: [
          {
            required: true,
            message: "訂單來源[0->PC訂單；1->app訂單]不能為空",
            trigger: "blur",
          },
        ],
        status: [
          {
            required: true,
            message:
              "訂單狀態【0->待付款；1->待發貨；2->已發貨；3->已完成；4->已關閉；5->無效訂單】不能為空",
            trigger: "blur",
          },
        ],
        receiverName: [
          { required: true, message: "收貨人姓名不能為空", trigger: "blur" },
        ],
        receiverPhone: [
          { required: true, message: "收貨人電話不能為空", trigger: "blur" },
        ],

        receiverProvince: [
          { required: true, message: "省份/直轄市不能為空", trigger: "blur" },
        ],
        receiverCity: [
          { required: true, message: "城市不能為空", trigger: "blur" },
        ],
        receiverRegion: [
          { required: true, message: "區不能為空", trigger: "blur" },
        ],
        receiverDetailAddress: [
          { required: true, message: "詳細地址不能為空", trigger: "blur" },
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
            url: this.$http.adornUrl(`/order/order/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.memberId = data.order.memberId;
              this.dataForm.orderSn = data.order.orderSn;
              this.dataForm.couponId = data.order.couponId;
              this.dataForm.createTime = data.order.createTime;
              this.dataForm.memberUsername = data.order.memberUsername;
              this.dataForm.totalAmount = data.order.totalAmount;
              this.dataForm.payAmount = data.order.payAmount;
              this.dataForm.freightAmount = data.order.freightAmount;
              this.dataForm.promotionAmount = data.order.promotionAmount;
              this.dataForm.integrationAmount = data.order.integrationAmount;
              this.dataForm.couponAmount = data.order.couponAmount;
              this.dataForm.discountAmount = data.order.discountAmount;
              this.dataForm.payType = data.order.payType;
              this.dataForm.sourceType = data.order.sourceType;
              this.dataForm.status = data.order.status;
              this.dataForm.deliveryCompany = data.order.deliveryCompany;
              this.dataForm.deliverySn = data.order.deliverySn;
              this.dataForm.autoConfirmDay = data.order.autoConfirmDay;
              this.dataForm.integration = data.order.integration;
              this.dataForm.growth = data.order.growth;
              this.dataForm.billType = data.order.billType;
              this.dataForm.billHeader = data.order.billHeader;
              this.dataForm.billContent = data.order.billContent;
              this.dataForm.billReceiverPhone = data.order.billReceiverPhone;
              this.dataForm.billReceiverEmail = data.order.billReceiverEmail;
              this.dataForm.receiverName = data.order.receiverName;
              this.dataForm.receiverPhone = data.order.receiverPhone;
              this.dataForm.receiverPostCode = data.order.receiverPostCode;
              this.dataForm.receiverProvince = data.order.receiverProvince;
              this.dataForm.receiverCity = data.order.receiverCity;
              this.dataForm.receiverRegion = data.order.receiverRegion;
              this.dataForm.receiverDetailAddress =
                data.order.receiverDetailAddress;
              this.dataForm.note = data.order.note;
              this.dataForm.confirmStatus = data.order.confirmStatus;
              this.dataForm.deleteStatus = data.order.deleteStatus;
              this.dataForm.useIntegration = data.order.useIntegration;
              this.dataForm.paymentTime = data.order.paymentTime;
              this.dataForm.deliveryTime = data.order.deliveryTime;
              this.dataForm.receiveTime = data.order.receiveTime;
              this.dataForm.commentTime = data.order.commentTime;
              this.dataForm.modifyTime = data.order.modifyTime;
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
              `/order/order/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              memberId: this.dataForm.memberId,
              orderSn: this.dataForm.orderSn,
              couponId: this.dataForm.couponId,
              createTime: this.dataForm.createTime,
              memberUsername: this.dataForm.memberUsername,
              totalAmount: this.dataForm.totalAmount,
              payAmount: this.dataForm.payAmount,
              freightAmount: this.dataForm.freightAmount,
              promotionAmount: this.dataForm.promotionAmount,
              integrationAmount: this.dataForm.integrationAmount,
              couponAmount: this.dataForm.couponAmount,
              discountAmount: this.dataForm.discountAmount,
              payType: this.dataForm.payType,
              sourceType: this.dataForm.sourceType,
              status: this.dataForm.status,
              deliveryCompany: this.dataForm.deliveryCompany,
              deliverySn: this.dataForm.deliverySn,
              autoConfirmDay: this.dataForm.autoConfirmDay,
              integration: this.dataForm.integration,
              growth: this.dataForm.growth,
              billType: this.dataForm.billType,
              billHeader: this.dataForm.billHeader,
              billContent: this.dataForm.billContent,
              billReceiverPhone: this.dataForm.billReceiverPhone,
              billReceiverEmail: this.dataForm.billReceiverEmail,
              receiverName: this.dataForm.receiverName,
              receiverPhone: this.dataForm.receiverPhone,
              receiverPostCode: this.dataForm.receiverPostCode,
              receiverProvince: this.dataForm.receiverProvince,
              receiverCity: this.dataForm.receiverCity,
              receiverRegion: this.dataForm.receiverRegion,
              receiverDetailAddress: this.dataForm.receiverDetailAddress,
              note: this.dataForm.note,
              confirmStatus: this.dataForm.confirmStatus,
              deleteStatus: this.dataForm.deleteStatus,
              useIntegration: this.dataForm.useIntegration,
              paymentTime: this.dataForm.paymentTime,
              deliveryTime: this.dataForm.deliveryTime,
              receiveTime: this.dataForm.receiveTime,
              commentTime: this.dataForm.commentTime,
              modifyTime: this.dataForm.modifyTime,
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
