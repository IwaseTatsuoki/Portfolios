//ajaxGetSlipとajaxGetInventoryのstoreCodeのtrを消す
function clearTr(flag) {
    if (flag == "getSlip") {
        var slip_list_area = document.getElementById("slipInsertArea");

        var tr_all = slip_list_area.querySelectorAll("tr");
    } else if (flag == "inventory") {
        var insert_area = document.getElementById("inventoryInsertArea");

        var tr_all = insert_area.querySelectorAll("tr");
    }

    //上の一行でtrを取得して数が0以上だったら一度全部消す新しく表示するときに続けて表示しないため
    if (tr_all.length > 0) {

        for (let i = 0; i < tr_all.length; i++) {
            tr_all[i].remove();

        }
    }
}

function addAjaxGetSlip(data_json) {
    //追加するエリアを取得
    var slip_list_area = document.getElementById("slipInsertArea");

    //labelつけるためにidを使用そのidを区別するための番号
    var data_count = 0;

    //DBから持ってきた情報をテーブルとして表示させる
    for (var row in data_json) {

        var table_tr = document.createElement("tr");
        slip_list_area.appendChild(table_tr);

        for (var item in data_json[row]) {

            //				伝票コードの時だけチェックボックスを作る
            if (item == "slipCode") {

                var table_td = document.createElement("td");

                var checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.name = "cancelCheck";
                checkbox.id = "checkbox_" + data_count;
                checkbox.value = data_json[row]["slipCode"];

                var label = document.createElement("label");
                label.htmlFor = "checkbox_" + data_count;

                table_tr.appendChild(table_td);

                table_td.appendChild(checkbox);

                table_td.appendChild(label);

                label.textContent = data_json[row][item];

            } else {

                var table_td = document.createElement("td");

                table_tr.appendChild(table_td);

                table_td.innerHTML = data_json[row][item];

            }

            data_count++;
        }
    }
}

//ajaxGetInventoryのフォーム追加
function addAjaxGetInventory(data_json) {

    var insert_area = document.getElementById("inventoryInsertArea");

    for (var row in data_json) {

        var table_tr = document.createElement("tr");
        insert_area.appendChild(table_tr);

        for (var item in data_json[row]) {

            var table_td = document.createElement("td");

            //list.jsでソートするためにクラス名をつける
            switch (item) {
                case "itemName":
                    table_td.className = "itemName";
                    break;
                case "price":
                    table_td.className = "price";
                    break;
                case "size":
                    table_td.className = "size";
                    break;
                case "color":
                    table_td.className = "color";
                    break;
                case "categoryName":
                    table_td.className = "category";
                    break;
                case "sexType":
                    table_td.className = "sex";
                    break;
                case "bestBefore":
                    table_td.className = "date";
                    break;
                case "inventoryCount":
                    table_td.className = "inventoryCount";
                    break;
                case "shipmentPending":
                    table_td.className = "shipmentPending";
                    break;
            }

            table_tr.appendChild(table_td);

            table_td.textContent = data_json[row][item];

        }
    }
}

function clearAll() {
    //	確定を押すと一度全部の商品入力ボックスを消す。
    //	コピーするための元のth、tdと追加したth、tdを取得
    var tr_all = document.querySelectorAll("#itemInputArea tr");

    var first_tr_all = document.querySelectorAll("#firstTr th, #firstTr td");

    //	上のものすべて消す。
    if (tr_all.length > 0 && first_tr_all.length > 0) {
        for (const item of tr_all) {
            item.remove();
        }
        for (const item of first_tr_all) {
            console.log(item);
            item.remove();
        }
    }
}

function entryShippingItemNoneSet(under_area) {
    //	追加と確定も一度非表示にする

    under_area[0].style.display = "none";
    under_area[1].style.display = "none";

    var sender = document.getElementById("sender").value;
    var receiver = document.getElementById("receiver").value;

    if (sender == receiver) {
        document.getElementById("sameStore").style.display = "block";
        return null;
    }
    var request = {
        sender: sender
    };

    return request
}

function addEntryShippingItem(data_json, under_area, first_tr_item, first_tr_count) {
    document.getElementById("sameStore").style.display = "none";

    //		店舗を選んでいないと入力ボックスの追加と出荷確定ボタンが出ないようにする。
    //		店舗を選ぶと出る。
    under_area[0].style.display = "inline";
    under_area[1].style.display = "inline";
    under_area[0].style.margin = "5px 10px";
    under_area[1].style.margin = "10px 10px";

    //		見本の入力ボックスを一つ作りそれを複製する形で入力ボックスを増やす。
    //		見本は隠しnameもつけない。
    //		item入力
    var div_first_tr = document.getElementById("firstTr");

    div_first_tr.appendChild(first_tr_item);

    var first_tr_item_th = document.createElement("th");
    first_tr_item_th.textContent = "商品コード";
    first_tr_item_th.className = "labelTh";
    first_tr_item.appendChild(first_tr_item_th);

    var first_tr_item_td = document.createElement("td");
    first_tr_item_td.className = "labelTd";
    first_tr_item.appendChild(first_tr_item_td);

    //		select追加
    var first_tr_select = document.createElement("select");
    first_tr_select.style.width = "95%";
    first_tr_select.style.padding = "5px";
    first_tr_select.style.border = "1";
    first_tr_select.style.margin = "0px";
    first_tr_item_td.appendChild(first_tr_select);

    for (var row in data_json) {
        var option = document.createElement("option");
        option.value = data_json[row]["itemCode"];
        option.text = data_json[row]["itemCode"];
        first_tr_select.appendChild(option);
    }

    //		商品個数入力
    div_first_tr.appendChild(first_tr_count);

    var first_tr_count_th = document.createElement("th");
    first_tr_count_th.textContent = "商品数";
    first_tr_count_th.className = "labelTh";
    first_tr_count.appendChild(first_tr_count_th);

    var first_tr_count_td = document.createElement("td");
    first_tr_count_td.className = "labelTd";
    first_tr_count.appendChild(first_tr_count_td);

    //		number追加
    var first_tr_number = document.createElement("input");
    first_tr_number.style.width = "92%";
    first_tr_number.style.padding = "5px";
    first_tr_number.style.border = "1";
    first_tr_number.style.margin = "0px";
    first_tr_number.type = "number";
    first_tr_number.value = 1;
    first_tr_number.min = "1";
    first_tr_number.step = "1";

    first_tr_count_td.appendChild(first_tr_number);
}

function cloneEntryShippingItem(first_tr_item, first_tr_count) {
    // 複製するHTML要素を取得
    // 複製
    var clone_item = first_tr_item.cloneNode(true);
    var clone_count = first_tr_count.cloneNode(true);

    // 複製したHTML要素をページに挿入
    document.querySelector("#itemInputArea").appendChild(clone_item);
    document.querySelector("#itemInputArea").appendChild(clone_count);

    //見本にはnameがないのでつける。
    clone_item.querySelector("select").name = "itemCode";
    clone_count.querySelector("input").name = "itemCount";

}

function entryArraivalItemNoneSet(under_area) {
    //	追加と確定も一度非表示にする
    under_area[0].style.display = "none";
    under_area[1].style.display = "none";

    var slipInfo = document.getElementById("slipCode").value.split(',');;
    var slipCode = slipInfo[0];
    console.log(slipCode);
    var slipReceiver = slipInfo[1];

    var receiver = document.getElementById("receiver");

    // selectedで選択されている値の番号が取得されます
    let idx = receiver.selectedIndex;

    // 値を取得
    let txt = receiver.options[idx].text;

    //	伝票コードの送り元店舗と入力された店舗が一致しなければ先に進まない。
    if (slipReceiver != txt) {
        document.getElementById("sameStore").style.display = "block";
        return null;
    }

    var request = {
        slipCode: slipCode
    };
    return request;
}

function addEntryArraivalItem(data_json, under_area, first_tr_item, first_tr_count) {

    document.getElementById("sameStore").style.display = "none";


    //		店舗を選んでいないと入力ボックスの追加と出荷確定ボタンが出ないようにする。
    //		店舗を選ぶと出る。

    under_area[0].style.display = "block";
    under_area[1].style.display = "block";
    under_area[0].style.margin = "5px auto";
    under_area[1].style.margin = "10px auto";


    //		item入力
    var div_first_tr = document.getElementById("firstTr");

    div_first_tr.appendChild(first_tr_item);

    var first_tr_item_th = document.createElement("th");
    first_tr_item_th.textContent = "商品コード";
    first_tr_item_th.className = "labelTh";
    first_tr_item.appendChild(first_tr_item_th);

    var first_tr_item_td = document.createElement("td");
    first_tr_item_td.className = "labelTd";
    first_tr_item.appendChild(first_tr_item_td);

    //		select追加
    var first_tr_select = document.createElement("select");
    first_tr_select.style.width = "95%";
    first_tr_select.style.padding = "5px";
    first_tr_select.style.border = "1";
    first_tr_select.style.margin = "0px";
    first_tr_item_td.appendChild(first_tr_select);

    for (var row in data_json) {
        var option = document.createElement("option");
        option.value = data_json[row]["itemCode"];
        option.text = data_json[row]["itemCode"];
        first_tr_select.appendChild(option);
    }

    //		商品個数入力
    div_first_tr.appendChild(first_tr_count);

    var first_tr_count_th = document.createElement("th");
    first_tr_count_th.textContent = "商品数";
    first_tr_count_th.className = "labelTh";
    first_tr_count.appendChild(first_tr_count_th);

    var first_tr_count_td = document.createElement("td");
    first_tr_count_td.className = "labelTd";
    first_tr_count.appendChild(first_tr_count_td);

    //		number追加
    var first_tr_number = document.createElement("input");
    first_tr_number.style.width = "92%";
    first_tr_number.style.padding = "5px";
    first_tr_number.style.border = "1";
    first_tr_number.style.margin = "0px";
    first_tr_number.type = "number";
    first_tr_number.value = 1;
    first_tr_number.min = "1";
    first_tr_number.step = "1";

    first_tr_count_td.appendChild(first_tr_number);
}