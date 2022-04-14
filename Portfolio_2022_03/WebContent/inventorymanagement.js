var i = 1;

//出荷時、入荷時フォームの追加
//idとnameに変数iを順番に振っていく
function addForm() {

	// 複製するHTML要素を取得
	var first_tr = document.querySelectorAll("#firstTr tr");

	// 複製
	var clone_item = first_tr[0].cloneNode(true);
	clone_item.id = "tr_item" + i;

	var clone_count = first_tr[1].cloneNode(true);
	clone_count.id = "tr_count" + i;

	// 複製したHTML要素をページに挿入
	document.querySelector("#itemInputArea").appendChild(clone_item);
	document.querySelector("#itemInputArea").appendChild(clone_count);

	//見本にはnameがないのでつける。
	clone_item.querySelector("select").name = "itemCode";
	clone_count.querySelector("input").name = "itemCount";

//	削除ボタンを追加するtdを作成。
	var td_delete = document.createElement("td");
	td_delete.style.width = "10%";
	td_delete.setAttribute("rowspan", "2")
	clone_item.appendChild(td_delete);

//	削除ボタンを同じliに追加するために取得。


	//フォーム消去ボタン
	var button_data = document.createElement('input');
	button_data.type = "button";
	button_data.id = i;
	button_data.setAttribute('onclick', 'deleteBtn(this)');
	button_data.value = '削除';
	td_delete.appendChild(button_data);

	i++;
}

//フォームタグ（input,button,br）の削除
//削除ボタンのidも変数iなのでほかのフォームタグのidと一致する
function deleteBtn(target) {
	var target_id = target.id;
	var delete_tr_item = document.getElementById('tr_item' + target_id);
	var delete_tr_count = document.getElementById('tr_count' + target_id);
	console.log(delete_tr_item);
	console.log(delete_tr_count);

	delete_tr_item.remove();
	delete_tr_count.remove();
}

//内容確認ダイアログ
function confi() {

	var res = confirm('この内容でよろしいですか？');

	return res;
}

//ajax
//非同期伝票表示
//伝票一覧表示
function ajaxGetSlip() {

	var storeCode = document.getElementById("storeCode").value;

	var request = {
			storeCode: storeCode
	};

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/SlipListServlet",
		type: "POST",
		async: true,
		data: request,
		dataType: "json",

	}).done(function (data) {
		// success
		//取得jsonデータ
		var data_stringify = JSON.stringify(data);
		var data_json = JSON.parse(data_stringify);

//		追加するエリアを取得
		var slip_list_area = document.getElementById("slipInsertArea");

		var tr_all = slip_list_area.querySelectorAll("tr");

//		上の一行でtrを取得して数が0以上だったら一度全部消す新しく表示するときに続けて表示しないため
		if(tr_all.length > 0){

			for (let i = 0; i < tr_all.length; i++) {
				tr_all[i].remove();

			}
		}

//		labelつけるためにidを使用そのidを区別するための番号
		var data_count = 0;

//		DBから持ってきた情報をテーブルとして表示させる
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
	})
	.fail(function (data) {
		// error
		console.log("error");
	});
}

//在庫一覧表示
function ajaxGetInventory() {

	var storeCode = document.getElementById("storeCode").value;

	var request = {
			storeCode: storeCode
	};

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/InventoryListServlet",
		type: "POST",
		async: true,
		data: request,
		dataType: "json",

	}).done(function (data) {

		// success
		//取得jsonデータ
		var data_stringify = JSON.stringify(data);
		var data_json = JSON.parse(data_stringify);

		var insert_area = document.getElementById("inventoryInsertArea");

		var tr_all = insert_area.querySelectorAll("tr");

//		表示前にすでに一覧が表示されていたら消す
		if(tr_all.length > 0){

			for (let i = 0; i < tr_all.length; i++) {
				tr_all[i].remove();

			}
		}

		for (var row in data_json) {

			var table_tr = document.createElement("tr");
			insert_area.appendChild(table_tr);

			for (var item in data_json[row]) {

				var table_td = document.createElement("td");

//				list.jsでソートするためにクラス名をつける

				if(item == "itemName"){
					table_td.className = "itemName";
				}else if(item == "price"){
					table_td.className = "price";
				}else if(item == "size"){
					table_td.className = "size";
				}else if(item == "color"){
					table_td.className = "color";
				}else if(item == "categoryName"){
					table_td.className = "category";
				}else if(item == "sexType"){
					table_td.className = "sex";
				}else if(item == "bestBefore"){
					table_td.className = "date";
				}else if(item == "inventoryCount"){
					table_td.className = "inventoryCount";
				}else if(item == "shipmentPending"){
					table_td.className = "shipmentPending";
				}

				table_tr.appendChild(table_td);

				table_td.textContent = data_json[row][item];

			}
		}

//		list.jsで必要な部分
		var options = {
				valueNames : ["itemName",'price',"size","color","category","sex", "date", "inventoryCount", "shipmentPending" ]
		};

		var userList = new List('users', options);
	})
	.fail(function (data) {
		// error
		console.log("error");
	});
}

//select2で必要な部分
$(function () {
	$('.select2').select2({
		language: "ja" //日本語化
	});
})


//shippingInputのajax
//送り元と送り先店舗を入力すると送り先の店舗の在庫にある商品が選択できる
//selectを作る
function entryShippingItem() {


	//	確定を押すと一度全部の商品入力ボックスを消す。
//	コピーするための元のth、tdと追加したth、tdを取得
	var tr_all = document.querySelectorAll("#itemInputArea tr");

	var first_tr_all = document.querySelectorAll("#firstTr th, #firstTr td");

//	上のものすべて消す。
	if(tr_all.length > 0 && first_tr_all.length > 0){
		for (const item of tr_all) {
			item.remove();
		}
		for (const item of first_tr_all) {
			console.log(item);
			item.remove();
		}
	}

	//	追加と確定も一度非表示にする
	var under_area = document.querySelectorAll(".underArea input");
	under_area[0].style.display = "none";
	under_area[1].style.display = "none";

	var sender = document.getElementById("sender").value;
	var receiver = document.getElementById("receiver").value;

	if (sender == receiver) {
		document.getElementById("sameStore").style.display = "block";
		return;
	}

	var request = {
			sender: sender
	};

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/ShippingArraivalInputServlet?flag=shipping",
		type: "POST",
		async: true,
		data: request,
		dataType: "json",

	}).done(function (data) {
		// success
		//取得jsonデータ
		var data_stringify = JSON.stringify(data);
		var data_json = JSON.parse(data_stringify);

		//		追加するエリアを取得
		var input_area = document.getElementById("inputArea");

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

		var first_tr_item = document.createElement("tr");
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
		first_tr_select.style.width ="95%";
		first_tr_select.style.padding ="5px";
		first_tr_select.style.border ="1";
		first_tr_select.style.margin ="0px";
		first_tr_item_td.appendChild(first_tr_select);

		for (var row in data_json) {
			var option = document.createElement("option");
			option.value = data_json[row]["itemCode"];
			option.text = data_json[row]["itemCode"];
			first_tr_select.appendChild(option);
		}

//		商品個数入力
		var first_tr_count = document.createElement("tr");
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
		first_tr_number.style.width ="92%";
		first_tr_number.style.padding ="5px";
		first_tr_number.style.border ="1";
		first_tr_number.style.margin ="0px";
		first_tr_number.type = "number";
		first_tr_number.value = 1;
		first_tr_number.min = "1";
		first_tr_number.step = "1";

		first_tr_count_td.appendChild(first_tr_number);

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
	})
	.fail(function (data) {
		// error
		console.log("error");
	});
}


//arraivalInput.jsp
//伝票コードと送り先を決めたら商品入力欄が表示
function entryArraivalItem(){


	//	確定を押すと一度全部の商品入力ボックスを消す。
//	コピーするための元のth、tdと追加したth、tdを取得
	var tr_all = document.querySelectorAll("#itemInputArea tr");

	var first_tr_all = document.querySelectorAll("#firstTr th, #firstTr td");

//	上のものすべて消す。
	if(tr_all.length > 0 && first_tr_all.length > 0){
		for (const item of tr_all) {
			item.remove();
		}
		for (const item of first_tr_all) {
			console.log(item);
			item.remove();
		}
	}

//	追加と確定も一度非表示にする
	var under_area =  document.querySelectorAll(".underArea input");
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
	let txt  = receiver.options[idx].text;

//	伝票コードの送り元店舗と入力された店舗が一致しなければ先に進まない。
	if(slipReceiver != txt){
		document.getElementById("sameStore").style.display = "block";
		return;
	}

	var request = {
			slipCode: slipCode
	};

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/ShippingArraivalInputServlet?flag=arraival",
		type: "POST",
		async: true,
		data: request,
		dataType: "json",

	}).done(function (data) {
		// success
		//取得jsonデータ
		var data_stringify = JSON.stringify(data);
		var data_json = JSON.parse(data_stringify);

//		追加するエリアを取得
		var input_area = document.getElementById("inputArea");

		document.getElementById("sameStore").style.display = "none";


//		店舗を選んでいないと入力ボックスの追加と出荷確定ボタンが出ないようにする。
//		店舗を選ぶと出る。

		under_area[0].style.display = "block";
		under_area[1].style.display = "block";
		under_area[0].style.margin = "5px auto";
		under_area[1].style.margin = "10px auto";


//		item入力
		var div_first_tr = document.getElementById("firstTr");

		var first_tr_item = document.createElement("tr");
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
		first_tr_select.style.width ="95%";
		first_tr_select.style.padding ="5px";
		first_tr_select.style.border ="1";
		first_tr_select.style.margin ="0px";
		first_tr_item_td.appendChild(first_tr_select);

		for (var row in data_json) {
			var option = document.createElement("option");
			option.value = data_json[row]["itemCode"];
			option.text = data_json[row]["itemCode"];
			first_tr_select.appendChild(option);
		}

//		商品個数入力
		var first_tr_count = document.createElement("tr");
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
		first_tr_number.style.width ="92%";
		first_tr_number.style.padding ="5px";
		first_tr_number.style.border ="1";
		first_tr_number.style.margin ="0px";
		first_tr_number.type = "number";
		first_tr_number.value = 1;
		first_tr_number.min = "1";
		first_tr_number.step = "1";

		first_tr_count_td.appendChild(first_tr_number);

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

	})
	.fail(function (data) {
		// error
		console.log("error");
	});
}

//一回クリックでfalseになって次クリックでtrue
//表示したらfalseで非表示にする。
var filter_flags = {filterItemName:true, filterSize:true, filterColor:true, filterCategory:true, filterSex:true};

//フィルターの項目を表示させる。
function areaBlock(h5) {

	var block_element = document.getElementsByClassName(h5.id);

	if(filter_flags[h5.id] == true){

		block_element[0].style.display="block";

		filter_flags[h5.id] = false;
	}else{

		block_element[0].style.display="none";

		filter_flags[h5.id] = true;

	}

}

function filter() {

	var options = {
			valueNames : ["itemName",'price',"size","color","category","sex", "date", "inventoryCount", "shipmentPending" ]
	};

	var userList = new List('users', options);

//	チェックボックスの情報取得
	var filter_checks = document.querySelectorAll("input[type='checkbox']");

//	userList（在庫テーブル）にフィルターをかける。
//	tableのtdをひとつずつ回すその中でチェックボックスも回す。
	userList.filter(function(item) {

		for(i = 0; i < filter_checks.length; i++){

//			チェックボックスでチェックのついた要素のときだけテーブルの要素のクラス名に対応した値とチェックボックスの値を
//			比較して同じであればtrueを返しuserListに戻す。
			if(filter_checks[i].checked){

//				[filter_checks[i].name]がチェックボックスのnameでテーブルのtdのクラス名と対応している。
				if (item.values()[filter_checks[i].name] == filter_checks[i].value) {

					return true;
				}
			}

		}

		return false;
	});

}