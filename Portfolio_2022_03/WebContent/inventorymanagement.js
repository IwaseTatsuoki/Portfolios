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

//ajaxGetSlipとajaxGetInventoryのstoreCodeの値を取得
function getValu() {
	var storeCode = document.getElementById("storeCode").value;

	var request = {
		storeCode: storeCode
	};

	return request;
}

//ajax
//非同期伝票表示
//伝票一覧表示
function ajaxGetSlip() {

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/SlipListServlet",
		type: "POST",
		async: true,
		data: getValu(),
		dataType: "json",

	}).done(function (data) {

		clearTr("getSlip");

		// success
		//取得jsonデータ
		var data_stringify = JSON.stringify(data);
		var data_json = JSON.parse(data_stringify);

		addAjaxGetSlip(data_json);

	})
		.fail(function (data) {
			// error
			console.log("error");
		});
}

//在庫一覧表示
function ajaxGetInventory() {

	$.ajax({
		url: "http://localhost:8080/Portfolio_2022_03/InventoryListServlet",
		type: "POST",
		async: true,
		data: getValu(),
		dataType: "json",

	}).done(function (data) {

		clearTr("inventory");

		// success
		//取得jsonデータ
		var data_stringify = JSON.stringify(data);
		var data_json = JSON.parse(data_stringify);

		addAjaxGetInventory(data_json);

		//list.jsで必要な部分
		var options = {
			valueNames: ["itemName", 'price', "size", "color", "category", "sex", "date", "inventoryCount", "shipmentPending"]
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

	clearAll();

	var under_area = document.querySelectorAll(".underArea input");

	var request = entryShippingItemNoneSet(under_area);

	if (request == null) { return };

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

		var first_tr_item = document.createElement("tr");
		var first_tr_count = document.createElement("tr");

		addEntryShippingItem(data_json, under_area, first_tr_item, first_tr_count)

		cloneEntryShippingItem(first_tr_item, first_tr_count);

	}).fail(function (data) {
		// error
		console.log("error");
	});
}


//arraivalInput.jsp
//伝票コードと送り先を決めたら商品入力欄が表示
function entryArraivalItem() {

	clearAll();

	var under_area = document.querySelectorAll(".underArea input");

	var request = entryArraivalItemNoneSet(under_area);

	if (request == null) { return };

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

		var first_tr_item = document.createElement("tr");
		var first_tr_count = document.createElement("tr");

		addEntryArraivalItem(data_json, under_area, first_tr_item, first_tr_count);

		cloneEntryShippingItem(first_tr_item, first_tr_count);

	})
		.fail(function (data) {
			// error
			console.log("error");
		});
}

//一回クリックでfalseになって次クリックでtrue
//表示したらfalseで非表示にする。
var filter_flags = { filterItemName: true, filterSize: true, filterColor: true, filterCategory: true, filterSex: true };

//フィルターの項目を表示させる。
function areaBlock(h5) {

	var block_element = document.getElementsByClassName(h5.id);

	if (filter_flags[h5.id] == true) {

		block_element[0].style.display = "block";

		filter_flags[h5.id] = false;
	} else {

		block_element[0].style.display = "none";

		filter_flags[h5.id] = true;

	}

}

function filter() {

	var options = {
		valueNames: ["itemName", 'price', "size", "color", "category", "sex", "date", "inventoryCount", "shipmentPending"]
	};

	var userList = new List('users', options);

	//	チェックボックスの情報取得
	var filter_checks = document.querySelectorAll("input[type='checkbox']");

	//	userList（在庫テーブル）にフィルターをかける。
	//	tableのtdをひとつずつ回すその中でチェックボックスも回す。
	userList.filter(function (item) {

		for (i = 0; i < filter_checks.length; i++) {

			//			チェックボックスでチェックのついた要素のときだけテーブルの要素のクラス名に対応した値とチェックボックスの値を
			//			比較して同じであればtrueを返しuserListに戻す。
			if (filter_checks[i].checked) {

				//				[filter_checks[i].name]がチェックボックスのnameでテーブルのtdのクラス名と対応している。
				if (item.values()[filter_checks[i].name] == filter_checks[i].value) {

					return true;
				}
			}

		}

		return false;
	});

}