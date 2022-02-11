 var swiper_banner = new Swiper(".container" , {
    navigation: {
        nextEl: '.next-button',
        prevEl: '.prev-button',
    },
    effect : "fade", 
    autoplay : true,
    pagination : {
        el : ".pagination",
        clickable :true,   
    },
    loop : true
});
for(var i = 0 ; i < swiper_banner.pagination.bullets.length ; i ++){
    swiper_banner.pagination.bullets[i].onmouseover = function(){
       
        this.click();
    }
}
var container = document.querySelector(".container");
container.onmouseover = function(){
    // swiper_banner.autoplay.stop();
}
container.onmouseout = function(){
    // swiper_banner.autoplay.start();
} 