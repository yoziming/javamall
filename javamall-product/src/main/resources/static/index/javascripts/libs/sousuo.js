var input_ele = document.getElementById("input");
var list_ele = document.getElementById("list");
input_ele.oninput = function () {

    jsonp({
        url: "https://www.baidu.com/sugrec",
        data: {
            prod: "pc",
            wd: input_ele.value,

            _: Date.now()
        },
        jsonp: "cb",
        success: function (res) {
            render(res);
        }
    })

}
function render(res) {
   
    if (!res.g) {
        return list_ele.innerHTML = "";
    }
  
    var html = res.g.map(function (item) {
        return "<li>" + item.q + "</li>";
    }).join("");
  
    list_ele.innerHTML = html;
}