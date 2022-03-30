var i = 1;

//出荷時、入荷時フォームの追加
//idとnameに変数iを順番に振っていく
function addForm() {

	//ulタグ
	var input_li = document.createElement('li');
	input_li.className = 'formLi';
	input_li.id = 'li' + i;
	var ulParent = document.getElementById('formUl');
	ulParent.appendChild(input_li);

	//商品コード入力欄
	var input_data = document.createElement('input');
	input_data.type = 'text';
	input_data.id = 'text' + i;
	input_data.name = 'itemCode';
	input_data.placeholder = '商品コード';
	input_data.style = 'margin-right: 5px;';
	var parent = document.getElementById('li' + i);
	parent.appendChild(input_data);

	//商品数入力欄
	var input_num = document.createElement('input');
	input_num.type = 'number';
	input_num.id = 'num' + i;
	input_num.name = 'itemCount';
	input_num.placeholder = '個数';
	input_num.style = 'margin-right: 5px;';
	input_num.min = 1;
	input_num.step = 1;
	parent.appendChild(input_num);


	//フォーム消去ボタン
	var button_data = document.createElement('button');
	button_data.id = i;
	button_data.onclick = function () { deleteBtn(this); }
	button_data.innerHTML = '削除';
	parent.appendChild(button_data);


	i++;
}

//フォームタグ（input,button,br）の削除
//削除ボタンのidも変数iなのでほかのフォームタグのidと一致する
function deleteBtn(target) {
	var target_id = target.id;
	var ul_id = document.getElementById('formUl');
	var text_id = document.getElementById('text' + target_id);
	var num_id = document.getElementById('num' + target_id);
	var li_id = document.getElementById('li' + target_id);
	var tgt_id = document.getElementById(target_id);

	li_id.removeChild(text_id);
	li_id.removeChild(num_id);
	li_id.removeChild(tgt_id);
	ul_id.removeChild(li_id);
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

//list.jsでソートするためにクラス名をつける
				 if(item == "price"){
					table_td.className = "price";
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
				valueNames : [  'price',  "date", "inventoryCount", "shipmentPending" ]
		};

		var userList = new List('users', options);

//		userList.sort('price', {
//			order : 'asc'
//		});

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
function itemSelect(){

	var storeCode = document.getElementById("sender").value;

	var request = {
			sender: storeCode
	};

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/ShippingArraivalInputServlet",
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
		var first_ul = document.getElementById("firstUl");

//		<li class="formLi"><input type="text" name="itemCode" placeholder='商品コード'>
//		<input type="number" name="itemCount" placeholder='出荷数' min="1" step="1"></li>

		var input_all = input_area.querySelectorAll("input");

//		上の一行でtrを取得して数が0以上だったら一度全部消す新しく表示するときに続けて表示しないため
		if(input_all.length > 0){

			for (let i = 0; i < input_all.length; i++) {
				input_all[i].remove();

			}
		}

//		DBから持ってきた情報をテーブルとして表示させる
		for (var row in data_json) {

			var first_input = document.createElement("input");
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