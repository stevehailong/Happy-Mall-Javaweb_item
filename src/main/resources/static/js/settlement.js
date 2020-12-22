$(function () {
    //计算总价
    let array = $(".qprice");
    let totalCost = 0;
    for (let i = 0; i < array.length; i++) {
        let val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥" + totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
});

//商品数量++
function addQuantity(obj) {
    let index = $(".car_btn_2").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    var stock = parseInt($(".productStock").eq(index).val());
    var price = parseInt($(".productPrice").eq(index).val());
    var id = parseInt($(".id").eq(index).val());
    if (quantity == stock) {
        alert("库存不足！");
        return false;
    }
    quantity++;
    let cost = quantity * price;
    //将最新的quantity和cost发给后台，动态更新数据库
    $.ajax({
        url: "/cart/update/" + id + "/" + quantity + "/" + cost,
        type: "POST",
        success: function (data) {
            if(data == "success"){
                $(".qprice").eq(index).text('￥' + cost);
                $(".car_ipt").eq(index).val(quantity);
                let array = $(".qprice");
                let totalCost = 0;
                for (let i = 0; i < array.length; i++) {
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥" + totalCost);
            }
        }
    });
}

//商品数量--
function subQuantity(obj) {
    let index = $(".car_btn_1").index(obj);
    let quantity = $(".car_ipt").eq(index).val();
    let id = parseInt($(".id").eq(index).val());
    if (quantity == 1) {
        alert("至少选择一件商品！");
        return false;
    }
    quantity--;
    let price = parseInt($(".productPrice").eq(index).val());
    let cost = quantity * price;
    $.ajax({
        url: "/cart/update/" + id + "/" + quantity + "/" + cost,
        type: "POST",
        success: function (data) {
            if(data == "success"){
                $(".qprice").eq(index).text('￥' + cost);
                $(".car_ipt").eq(index).val(quantity);
                let array = $(".qprice");
                let totalCost = 0;
                for (let i = 0; i < array.length; i++) {
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥" + totalCost);
            }
        }
    });
}

//移出购物车
function removeCart(obj) {
    var index = $(".delete").index(obj);
    var id = parseInt($(".id").eq(index).val());
    if (confirm("是否确定要删除？")) {
        window.location.href = "/cart/deletById/" + id;
    }
}

function settlement2() {
    let totalCost = $("#totalprice").text();
    if(totalCost == "￥0"){
        alert("购物车为空，不能结算！");
        return false;
    }
    window.location.href="/cart/settlement2"
}