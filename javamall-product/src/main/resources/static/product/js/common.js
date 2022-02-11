$(document).ready(function() {
    // 页面滚动顶部导航栏固定事件
    window.onscroll = scroll;
    // 页面滚动顶部导航栏固定事件
    function scroll() {
        if ($(window).scrollTop() > $('#header').outerHeight(true)) {
            $('#fixhead').css({
                display: 'block'
            });
        } else {
            $('#fixhead').css({
                display: 'none'
            });
        }
    };
    // 切换到购物车页面
    $('.cart').click(function() {
        window.open('../pages/cart.html', 'blank');
    })

})