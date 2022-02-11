
function xhr( options ){
    // 如果你不希望有默认值，那么这里的内容你可以不写; 
    var default_options = {
         type : "GET",
         data : {},
         url : null,
         callback : function(){}
    }
    // 对象合并 : ES6 的API : Object.assign ; 
    for(var attr in options){
         default_options[attr] = options[attr];
    }
    default_options.type = default_options.type.toUpperCase();
    // 把data里面的对象数据变成字符串数据; 
     // {
     //     username : "xxx",
     //     password : "123456"
     // }
     var data = "";
     for(var attr in default_options.data){
         data += "&" + attr + "=" + default_options.data[attr]
     }
     // 干掉开头的& ; 
     data = data.slice(1);
     // 如果是POST的发送方式那么我们就把这个字符串放到send里; 
     // 如果是get的形式发送数据那么我们就把这个数据放在url上; 
     if( default_options.type === "GET"){
         default_options.url += "?" + data;
     }

     var xhr = new XMLHttpRequest();
     xhr.open( default_options.type , default_options.url );
     if( default_options.type === "POST"){
         xhr.setRequestHeader("Content-Type" , "application/x-www-form-urlencoded");
     }
     if( default_options.type === "POST" ){
         xhr.send( data );
     }else{
         xhr.send( null );
     }
     xhr.onload = function(){
         if( /^2\d{2}/.test( xhr.status ) ){
             default_options.callback( xhr.responseText );
         }
     }
}

function setCookie(name, value, options) {
    
    options = options || {};
    if (typeof options.expires === "number") {
        var d = new Date();
        d.setDate(d.getDate() + options.expires);
    }
    document.cookie = [
        name + "=" + value,
        typeof options.path === "string" ? ";path=" + options.path : "",
        typeof options.domain === "string" ? ";domain=" + options.domain : "",
        typeof options.expires === "number" ? ";expires=" + d : ""
    ].join("");
}
function removeCookie( name , path ){
    setCookie(name, "" , {
        expires : - 1,
        path : path 
    })
}
function getCookie( name ){
    var cookie_array = document.cookie.split("; ");
    for(var i = 0 ; i < cookie_array.length ; i ++){
        var cookie_item = cookie_array[i].split("=")
        if(  cookie_item[0] === name ){
            return cookie_item[1];
        }
    }
    return "";
}