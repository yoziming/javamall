//获取localstorage的goods
$(function () {
    var shop_list = (function () {
        if (localStorage.getItem("goods")) {
            return JSON.parse(localStorage.getItem("goods"));
        } else {
            return {};
        }
    })();
    localStorage.setItem("goods", "");
    // 渲染购物车结构 : 
    // 关注localstorage里面的数据
    $.ajax({
        url: "./data.json",
        dataType: "json"
    })
        .done(function (res) {
            render(res.data.filter(function (item) {
                for (var attr in shop_list) {
                    //遍历购物车列表; 
                    if (item.id == attr) {
                        // 如果判定相等那么就意味着当前商品就是购物车里面的商品
                        // - 给商品上面加上数量; 
                        item.count = shop_list[attr];
                        return true;
                    }
                }
                return false;
            }));
        });
    function render(data) {
        var html = data.map(function (item) {
            console.log(item);
            return  `<div class="detail-goods">
        <div class="detail-top">
           <a href="./index.html"><span class="crumb-name ">首页</span><i> &gt; </i></a>
           <span class="crumb-name ">${item.txt}</span><i> &gt; </i>
           <span class="crumb-name ">${item.txt1}</span><i> &gt; </i>
           <span class="crumb-name ">${item.title}</span>
        </div>
        <div class="detail-body detail">
        <div class="detailHd detailHead">
        <div class="container ">
            <!-- 小图部分内容 -->
            <div class="small-box">
                <img src="${item.src}" alt="">
                <!-- 黄色的焦点框 -->
                <!-- yellow-focus 相当于咱们的box框 -->
                <div class="yellow-focus"></div>
            </div>
            <!-- 大图部分 -->
            <div class="big-box">
                <img src="${item.src2}" alt="">
            </div>
            <!-- 选项卡按钮部分 -->
            <div class="tab-list">
                <img src="${item.src1}" 
                data-small="${item.src2}" 
                data-big="${item.src2}" alt="">
                <img src="${item.src3}" 
                data-small="${item.src4}" 
                data-big="${item.src4}" alt="">
                <img src="${item.src5}"
                data-small="${item.src6}"
                data-big="${item.src6}" alt="">
                <img src="${item.src7}"
                data-small="${item.src8}"
                data-big="${item.src8}" alt="">
                <img src="${item.src9}"
                data-small="${item.src0}"
                data-big="${item.src0}" alt="">
                    </div>
                    <div class="you">
                    <a class="youhui" href="http://b.you.163.com?from=yx">
                        <span>${item.text}</span><i></i>
                    </a>
                </div>
                    
                </div>

                <div class="info">
                <div class="intro">
                <div class="name">${item.title}</div>
                <div class="detailTag"></div>
                <div class="desc">${item.txt2}</div>
            </div>
            <div class="price">
                <div class="priceBox">
                    <span class="label first">${item.title}</span>
                    <span class="rmb-price">
                        <span class="rmb">$</span><span id="num">${item.price}</span>
                        <del><span>$</span><span>${item.oldprice}</span></del>
                    </span>
                </div>
               
                        <div class="sale">
                            <span class="label second">促销</span>
                            <div class="saleLine">
                                <a class="linkBox" href="">
                                    <div class="activityType">$15补贴待领取</div>
                                    <span>女王节返场可叠享</span>
                                </a>
                            </div>
                            <div class="saleLine">
                                <a class="linkBox" href="">
                                    <div class="activityType">全场换购</div>
                                    <span>低至4折超值换购</span>
                                </a>
                            </div>
                        </div>
                        <div class="sale">
                            <span class="label second">购物返</span>
                            <div class="saleLine">
                                <a class="linkBox" href="">
                                    <!-- <div class="activityType">$15补贴待领取</div> -->
                                    <span>最高返</span>
                                    <span style="color: red;">20积分？</span>
                                </a>
                            </div>
                        </div>
                        <div class="sale">
                            <span class="label second">邮费</span>
                            <div class="saleLine">
                                <a class="linkBox" href="">
                                    <!-- <div class="activityType">$15补贴待领取</div> -->
                                    <span>满99元免邮</span>
                                </a>
                            </div>
                        </div>
                        <div class="sale">
                            <span class="label second">配送</span>
                            <div class="saleLine">
                                <a class="linkBox" href="">
                                    <!-- <div class="activityType">$15补贴待领取</div> -->
                                    <span>至</span>
                                    <input type="text" placeholder="请选择地址">
                                </a>
                            </div>
                        </div>
                        <div class="server">
                            <span class="label">服务</span>
                            <div class="policyBox">
                                <p class="policy-item">
                                    <span>·&nbsp;</span>
                                    <span>网易自营品牌</span>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                </p>
                                <p class="policy-item">
                                    <span>·&nbsp;</span>
                                    <span>30天无忧退换货</span>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                </p>
                                <p class="policy-item">
                                    <span>·&nbsp;</span>
                                    <span>国内部分地区不可配送</span>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="parameterpicker">
                        <div class="specialProperty prop2">
                            <span class="type type-2">${item.txt3}</span>
                            <div class="cont">
                                <ul class="tabs">
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="${item.txt4}">
                                            <img src="${item.srcs1}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="钢琴白">
                                            <img src="${item.srcs2}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="孔灵蓝">
                                            <img src="${item.srcs3}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="波尔多红">
                                            <img src="${item.srcs4}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="星河银">
                                            <img src="${item.srcs5}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="湾岛蓝">
                                            <img src="${item.srcs6}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="摩尔棕">
                                            <img src="${item.srcs7}">
                                        </a>
                                    </li>
                                    <li class="tab-cont">
                                        <i class=""></i>
                                        <a class="tab tab-pic" title="烟熏灰">
                                            <img src="${item.srcs8}">
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="number">
                        <div class="name1">数量</div>
                        <div class="field">
                            <div class="selnum">
                                <span class="less">-</span>
                                <input class="num-ipt" type="text" name="saleNumber" value="1">
                                <span class="more">+</span>
                            </div>
                        </div>
                    </div>
                    <div class="btns">
                        <a class="buyNow">
                            <span>立即购买</span>
                        </a>
                        <a class="intoCart add-cart" data-id="${item.id}">
                            <span>加入购物车</span>
                        </a>
                        <a class="buyNows">
                            <span>☆收藏</span>
                        </a>
                        <a class="buyNow Nows">
                            <span>下载严选APP</span>
                        </a>
                    </div>
                </div>
            </div>       
        </div>    
    </div>`;
        }).join("");
        $(".detail-box").html(html);
        new Magnifier();
    };
    //点击购物车加入购物车 
    $(".detail-box").on("click", ".add-cart", function () {
        addCart($(this).attr("data-id"));
    });
    var shop_list1 = (function () {
        if (localStorage.getItem("cart")) {
            return JSON.parse(localStorage.getItem("cart"));
        } else {
            return {};
        }
    })();
    function addCart(id) {
        if (id in shop_list1) {
            shop_list1[id]++;
        } else {
            shop_list1[id] = 1;
        }
        localStorage.setItem("cart", JSON.stringify(shop_list1));
    }
    //放大镜功能

    class Magnifier {
        constructor() {
            this.container_ele = $(".container");
            this.small_box_ele = $(".small-box");
            this.small_img_ele = $(".small-box img")
            this.focus_ele = $(".yellow-focus");
            this.big_box_ele = $(".big-box");
            this.big_img_ele = $(".big-box img");
            this.tab_eles = $(".tab-list img");

            // 获取container元素的偏移; 
            this.container_offset = this.container_ele.offset();
            // { left , top }

            // 获取container的宽度和高度; 
            this.container_size = {
                width: this.container_ele.width(),
                height: this.container_ele.height()
            }
            this.focus_size = {
                width: this.focus_ele.width(),
                height: this.focus_ele.height()
            }
            this.big_img_size = {
                width: this.big_img_ele.width(),
                height: this.big_img_ele.height()
            }
            // 获取大图焦点的尺寸; 
            this.big_box_size = {
                width: this.big_box_ele.width(),
                height: this.big_box_ele.height()
            }
            // 直接计算大图最大的移动距离; 
            this.big_max = {
                x: this.big_img_size.width - this.big_box_size.width,
                y: this.big_img_size.height - this.big_box_size.height,
            }
            this.bindEvent();
        }

        bindEvent() {
            //保存实例对象; 
            let mag_instance = this;
            this.small_box_ele.mouseover(function () {
                // 让元素顯示; 
                // 想在事件处理函数之中访问实例对象; 
                // 事件里面的this指向的是dom对象; 
                mag_instance.showEles();
            })
            this.small_box_ele.mouseout(function () {
                // 让元素隐藏; 
                mag_instance.hideEles();
            })
            // 鼠标绑定一个移动事件; 
            this.small_box_ele.mousemove(function (evt) {
                let e = evt || event;
                // x = 鼠标距离可是窗口的x值，- 外包围距离窗口的值 - focus元素一般的宽高; 
                let x = e.clientX - mag_instance.container_offset.left - mag_instance.focus_size.width / 2;
                let y = e.clientY - mag_instance.container_offset.top - mag_instance.focus_size.height / 2;
                // 边界检测 : 找极值 ( 最小值，最大值 );
                // 最小值判断 : 
                if (x <= 0) {
                    x = 0;
                }
                if (y <= 0) {
                    y = 0;
                }
                // 计算 x, 和y的最大位移距离： 
                var max_x = mag_instance.container_size.width - mag_instance.focus_size.width;
                var max_y = mag_instance.container_size.height - mag_instance.focus_size.height;

                if (x > max_x) {
                    x = max_x;
                }
                if (y > max_y) {
                    y = max_y;
                }
                // 计算x和y的移动比例; 
                let prop_x = x / max_x;
                let prop_y = y / max_y;

                mag_instance.focusMove(x, y);
                mag_instance.bigImageMove(prop_x, prop_y);
            })
            // 批量绑定事件; 
            this.tab_eles.mouseover(function () {
                // 我们要获取当前元素上的两个src数据
                let small_img_src = $(this).attr("data-small");
                let big_img_src = $(this).attr("data-big");
                mag_instance.changeImg(small_img_src, big_img_src);
            })
        }
        showEles() {
            // 让两个盒子顯示出来; 
            this.focus_ele.show();
            this.big_box_ele.show();
        }
        hideEles() {
            this.focus_ele.hide();
            this.big_box_ele.hide();
        }
        focusMove(x, y) {
            this.focus_ele.css({
                left: x,
                top: y
            })
        }
        bigImageMove(prop_x, prop_y) {
            // 获取大图移动x,y的算法; 
            // 获得大图x值位移的最大距离;  大图图片宽度 - 大图焦点部分宽度; 
            // 获的大图y值位移的最大距离;  大图图片高度 - 大图焦点部分高度; 
            this.big_img_ele.css({
                left: -this.big_max.x * prop_x,
                top: -this.big_max.y * prop_y
            })
        }
        changeImg(small_src, big_src) {
            this.small_img_ele.attr("src", small_src);
            this.big_img_ele.attr("src", big_src);
        }
    }


});