var i = 1;

//出荷時、入荷時フォームの追加
//idとnameに変数iを順番に振っていく
function addForm() {

	// 複製するHTML要素を取得
	var form_ul = document.getElementById("formUl");
	var first_form_li = document.getElementById("firstFormLi");

	// 複製
	var clone_element = first_form_li.cloneNode(true);

	clone_element.id = "li"+ i

	// 複製したHTML要素をページに挿入
	form_ul.appendChild(clone_element);

	//見本にはnameがないのでつける。
	clone_element.querySelector("select").name = "itemCode";
	clone_element.querySelector("input").name = "itemCount";

//	削除ボタンを同じliに追加するために取得。
	var parent = document.getElementById('li' + i);

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
function entryShippingItem(){


//	確定を押すと一度全部の商品入力ボックスを消す。
	var li_all = document.querySelectorAll("li");

	if(li_all.length > 0){

		for (let i = 0; i < li_all.length; i++) {
			li_all[i].remove();

		}
	}

//	追加と確定も一度非表示にする
	var under_area =  document.querySelectorAll(".underArea input");
	under_area[0].style.display = "none";
	under_area[1].style.display = "none";

	var sender = document.getElementById("sender").value;
	var receiver = document.getElementById("receiver").value;

	if(sender == receiver){
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

		under_area[0].style.display = "block";
		under_area[1].style.display = "block";
		under_area[0].style.margin = "5px auto";
		under_area[1].style.margin = "10px auto";

//		見本の入力ボックスを一つ作りそれを複製する形で入力ボックスを増やす。
//		見本は隠しnameもつけない。
		var clone_model = document.getElementById("cloneModel");
		var form_ul = document.getElementById("formUl");

		var form_ul_li = document.createElement("li");
		form_ul_li.className = "formLi";
		form_ul_li.id = "firstFormLi";
		clone_model.appendChild(form_ul_li);

		var form_ul_select = document.createElement("select");
		form_ul_select.style.width = "40px";
		form_ul_select.style.marginRight = "10px";
		form_ul_li.appendChild(form_ul_select);

		for (var row in data_json) {
			var option = document.createElement("option");
			option.value = data_json[row]["itemCode"];
			option.text = data_json[row]["itemCode"];
			form_ul_select.appendChild(option);
		}

		var form_ul_number = document.createElement("input");
		form_ul_number.type = "number";
		form_ul_number.width = "60px";
		form_ul_number.value = 1;
		form_ul_number.min = "1";
		form_ul_number.step = "1";
		form_ul_li.appendChild(form_ul_number);

		// 複製するHTML要素を取得

		var first_form_li = document.getElementById("firstFormLi");

		// 複製
		var clone_element = first_form_li.cloneNode(true);

		// 複製したHTML要素をページに挿入
		form_ul.appendChild(clone_element);

		//見本にはnameがないのでつける。
		clone_element.querySelector("select").name = "itemCode";
		clone_element.querySelector("input").name = "itemCount";


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
	var li_all = document.querySelectorAll("li");

	if(li_all.length > 0){

		for (let i = 0; i < li_all.length; i++) {
			li_all[i].remove();

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

//		見本の入力ボックスを一つ作りそれを複製する形で入力ボックスを増やす。
//		見本は隠しnameもつけない。
		var clone_model = document.getElementById("cloneModel");
		var form_ul = document.getElementById("formUl");

		var form_ul_li = document.createElement("li");
		form_ul_li.className = "formLi";
		form_ul_li.id = "firstFormLi";
		clone_model.appendChild(form_ul_li);

		var form_ul_select = document.createElement("select");
		form_ul_select.style.width = "40px";
		form_ul_select.style.marginRight = "10px";
		form_ul_li.appendChild(form_ul_select);

		for (var row in data_json) {
			var option = document.createElement("option");
			option.value = data_json[row]["itemCode"];
			option.text = data_json[row]["itemCode"];
			form_ul_select.appendChild(option);
		}

		var form_ul_number = document.createElement("input");
		form_ul_number.type = "number";
		form_ul_number.width = "60px";
		form_ul_number.value = 1;
		form_ul_number.min = "1";
		form_ul_number.step = "1";
		form_ul_li.appendChild(form_ul_number);

		// 複製するHTML要素を取得

		var first_form_li = document.getElementById("firstFormLi");

		// 複製
		var clone_element = first_form_li.cloneNode(true);

		// 複製したHTML要素をページに挿入
		form_ul.appendChild(clone_element);

		//見本にはnameがないのでつける。
		clone_element.querySelector("select").name = "itemCode";
		clone_element.querySelector("input").name = "itemCount";


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