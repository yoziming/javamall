<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="優惠卷類型" prop="couponType">
        <el-select v-model="dataForm.couponType" placeholder="請選擇">
          <el-option label="全場贈券" :value="0"></el-option>
          <el-option label="會員贈券" :value="1"></el-option>
          <el-option label="購物贈券" :value="2"></el-option>
          <el-option label="註冊贈券" :value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="優惠券圖片" prop="couponImg">
        <single-upload v-model="dataForm.couponImg"></single-upload>
      </el-form-item>
      <el-form-item label="優惠卷名字" prop="couponName">
        <el-input v-model="dataForm.couponName" placeholder="優惠卷名字"></el-input>
      </el-form-item>
      <el-form-item label="數量" prop="num">
        <el-input-number :min="0" v-model="dataForm.num"></el-input-number>
      </el-form-item>
      <el-form-item label="金額" prop="amount">
        <el-input-number :min="0" v-model="dataForm.amount" :precision="2"></el-input-number>
      </el-form-item>
      <el-form-item label="每人限領張數" prop="perLimit">
        <el-input-number :min="0" v-model="dataForm.perLimit"></el-input-number>
      </el-form-item>
      <el-form-item label="使用門檻（最小積分）" prop="minPoint">
        <el-input-number :min="0" v-model="dataForm.minPoint"></el-input-number>
      </el-form-item>
      <el-form-item label="有效時間" prop="useTimeRange">
        <el-date-picker v-model="dataForm.useTimeRange" type="daterange" range-separator="至" start-placeholder="開始時間" end-placeholder="結束時間"></el-date-picker>
      </el-form-item>
      <el-form-item label="使用類型" prop="useType">
        <el-select v-model="dataForm.useType" placeholder="請選擇">
          <el-option :value="0" label="全場通用"></el-option>
          <el-option :value="1" label="指定分類"></el-option>
          <el-option :value="2" label="指定商品"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="備註" prop="note">
        <el-input v-model="dataForm.note" placeholder="備註"></el-input>
      </el-form-item>
      <el-form-item label="發行數量" prop="publishCount">
        <el-input-number v-model="dataForm.publishCount" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="領取日期" prop="enableStartTime">
        <el-date-picker v-model="dataForm.timeRange" type="daterange" range-separator="至" start-placeholder="開始日期" end-placeholder="結束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="優惠碼" prop="code">
        <el-input v-model="dataForm.code" placeholder="優惠碼"></el-input>
      </el-form-item>
      <el-form-item label="領取所需等級" prop="memberLevel">
        <el-select v-model="dataForm.memberLevel" placeholder="請選擇">
          <el-option :value="0" label="不限制"></el-option>
          <el-option v-for="item in memberLevels" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
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
      memberLevels: [],
      dataForm: {
        id: 0,
        couponType: "",
        couponImg: "",
        couponName: "",
        num: "",
        amount: "",
        perLimit: "",
        minPoint: "",
        startTime: "",
        endTime: "",
        useType: "",
        note: "",
        publishCount: "",
        useCount: "",
        receiveCount: "",
        enableStartTime: "",
        enableEndTime: "",
        code: "",
        memberLevel: "",
        publish: 0,
        timeRange: [],
        useTimeRange: [],
      },
      dataRule: {
        couponType: [
          {
            required: true,
            message: "優惠卷類型不能為空",
            trigger: "blur",
          },
        ],
        couponImg: [
          { required: true, message: "優惠券圖片不能為空", trigger: "blur" },
        ],
        couponName: [
          { required: true, message: "優惠卷名字不能為空", trigger: "blur" },
        ],
        num: [{ required: true, message: "數量不能為空", trigger: "blur" }],
        amount: [{ required: true, message: "金額不能為空", trigger: "blur" }],
        perLimit: [
          { required: true, message: "每人限領張數不能為空", trigger: "blur" },
        ],
        minPoint: [
          { required: true, message: "使用門檻不能為空", trigger: "blur" },
        ],
        useType: [
          {
            required: true,
            message: "使用類型不能為空",
            trigger: "blur",
          },
        ],
        note: [{ required: true, message: "備註不能為空", trigger: "blur" }],
        publishCount: [
          { required: true, message: "發行數量不能為空", trigger: "blur" },
        ],
        enableStartTime: [
          {
            required: true,
            message: "可以領取的開始日期不能為空",
            trigger: "blur",
          },
        ],
        enableEndTime: [
          {
            required: true,
            message: "可以領取的結束日期不能為空",
            trigger: "blur",
          },
        ],
        code: [{ required: true, message: "優惠碼不能為空", trigger: "blur" }],
        memberLevel: [
          {
            required: true,
            message: "可以領取的會員等級不能為空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getMemberLevels();
  },
  methods: {
    getMemberLevels() {
      //獲取所有的會員等級
      this.$http({
        url: this.$http.adornUrl("/member/memberlevel/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 500,
        }),
      }).then(({ data }) => {
        this.memberLevels = data.page.list;
      });
    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/coupon/coupon/info/${this.dataForm.id}`),
            method: "get",
            params: this.$http.adornParams(),
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.couponType = data.coupon.couponType;
              this.dataForm.couponImg = data.coupon.couponImg;
              this.dataForm.couponName = data.coupon.couponName;
              this.dataForm.num = data.coupon.num;
              this.dataForm.amount = data.coupon.amount;
              this.dataForm.perLimit = data.coupon.perLimit;
              this.dataForm.minPoint = data.coupon.minPoint;
              this.dataForm.startTime = data.coupon.startTime;
              this.dataForm.endTime = data.coupon.endTime;
              this.dataForm.useType = data.coupon.useType;
              this.dataForm.note = data.coupon.note;
              this.dataForm.publishCount = data.coupon.publishCount;
              this.dataForm.useCount = data.coupon.useCount;
              this.dataForm.receiveCount = data.coupon.receiveCount;
              this.dataForm.enableStartTime = data.coupon.enableStartTime;
              this.dataForm.enableEndTime = data.coupon.enableEndTime;
              this.dataForm.code = data.coupon.code;
              this.dataForm.memberLevel = data.coupon.memberLevel;
              this.dataForm.publish = data.coupon.publish;
              this.dataForm.timeRange = [
                this.dataForm.startTime,
                this.dataForm.endTime,
              ];
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
              `/coupon/coupon/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              couponType: this.dataForm.couponType,
              couponImg: this.dataForm.couponImg,
              couponName: this.dataForm.couponName,
              num: this.dataForm.num,
              amount: this.dataForm.amount,
              perLimit: this.dataForm.perLimit,
              minPoint: this.dataForm.minPoint,
              startTime: this.dataForm.useTimeRange[0],
              endTime: this.dataForm.useTimeRange[1],
              useType: this.dataForm.useType,
              note: this.dataForm.note,
              publishCount: this.dataForm.publishCount,
              useCount: this.dataForm.useCount,
              receiveCount: this.dataForm.receiveCount,
              enableStartTime: this.dataForm.timeRange[0],
              enableEndTime: this.dataForm.timeRange[1],
              code: this.dataForm.code,
              memberLevel: this.dataForm.memberLevel,
              publish: this.dataForm.publish,
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
