//随机生成一个[min,max）的整数
function numRandom(min, max) {
    var num = parseInt(Math.random() * (max - min + 1) + min);
    return num;
}

//判断数组arr中是否存在num，存在返回true，不存在返回false
function has(arr, num) {
    //循环遍历数组
    for (var i = 0; i < arr.length; i++) {
        //若数组中存在num，返回true，终止循环
        if (arr[i] == num) {
            return true;
            break;
        }
    }
    return false;
}

//获得一个十六进制的随机颜色的字符串
function randomColor() {
    var color = '#';
    for (var i = 1; i <= 6; i++) {
        color += numRandom(0, 15).toString(16);
    }
    return color;
}


//统计字符串中每个字符的个数并将结果存放在countName对象中，返回countName对象
function countN(str) {
    //字符串转为数组
    var arr = str.split('');
    //使用reduce函数将每个字母及出现的次数放入countName中，第一个参数是累计器（提供初始值为一个空对象{}），第二个参数是当前值
    var countName = arr.reduce(function(allNames, name) {
        //判断当前元素是否是累计器中已有的成员，是的话该成员的值+1，不是就把该元素作为成员添加入到累计器中，并设置初始值为1
        if (name in allNames) {
            allNames[name]++; //
        } else {
            allNames[name] = 1;
        }
        return allNames;
    }, {});
    return countName;
}

//随机产生一个n位数字字母验证码
function VerificationCode(n) {
    //初始化
    var str = '';
    //随机数字字母
    for (var i = 0; i < n; i++) {
        //随机产生编码48-122
        var code = numRandomInclude(48, 122);
        //若编码不是0-9，a-z,A-Z，重新随机一个编码，直到是0-9，a-z,A-Z为止
        while ((code > 57 && code < 65) || (code > 90 && code < 97)) {
            code = numRandomInclude(48, 122);
        }
        //拼接字符串
        str += String.fromCharCode(code);
    }
    return str;
}

//获取浏览器滚动距离
function getScroll() {
    if (document.body.scrollTop) {
        return {
            top: document.body.scrollTop,
            left: document.body.scrollLetf
        }
    } else if (document.documentElement.scrollTop) {
        return {
            top: document.documentElement.scrollTop,
            left: document.documentElement.scrollLeft
        }
    } else {
        return {
            top: window.pageYOffset,
            left: window.pageXOffset
        }
    }
}

//随机A-Z大写字母
function letterRandom() {
    var num = numRandom(65, 90);
    var res = String.fromCharCode(num);
    return res;
}

//去除字符串前后的空格
function trim(str) {
    //     //方法一：
    //     //去除字符串前面的空格
    //     str = str.replace(/^\s+/, '');
    //     //去除字符串最后的空格
    //     str = str.replace(/\s+$/, '');
    //     //返回字符串
    //     return str;

    // // 方法二：
    // return str.replace(/^\s+/, '').replace(/\s+$/, '');

    // 方法三
    return str.replace(/^\s+|\s+$/g, '')
}

//通过id获取dom元素
function $id(domId) {
    return document.getElementById(domId);
}

//获取dom属性
function getStyle(dom, attr) {
    if (window.getComputedStyle) {
        return window.getComputedStyle(dom, null)[attr];
    } else {
        return dom.currentStyle[attr];
    }
}

//多属性缓动
function animate(dom, json, fn) {
    //使用定时器之前先清除定时器
    clearInterval(dom.timer);
    //设置定时器
    dom.timer = setInterval(function() {
        //初始化,flag用来判断每个属性缓动是否已经完成
        var flag = true;
        //json有几个键值对，就循环几次，每个属性都缓动
        for (var attr in json) {
            //1.确定当前位置
            if (attr == "opacity") {
                var current = getStyle(dom, attr) * 100;
            } else {
                var current = parseInt(getStyle(dom, attr));
            }
            //2.计算速度
            var speed = (json[attr] - current) / 10;
            speed = speed > 0 ? Math.ceil(speed) : Math.floor(speed);
            //3.计算下一个位置
            var next = current + speed;
            if (attr == "zIndex") {
                next = json[attr];
            }
            //4.有条件的定位
            if (next != json[attr]) {
                flag = false;
            }
            if (attr == "opacity") {
                dom.style.opacity = next / 100;
                dom.style.filter = 'alpha(opacity = ' + next + ')';
            } else if (attr == "zIndex") {
                dom.style.zIndex = next;
            } else {
                dom.style[attr] = next + 'px';
            }
        }
        if (flag == true) {
            clearInterval(dom.timer);
            if (fn) {
                fn()
            }
        }
    }, 200);
}