var i = 1;

//出荷時、入荷時フォームの追加
//idとnameに変数iを順番に振っていく
function addForm() {
    //商品コード入力欄
    var input_data = document.createElement('input');
    input_data.type = 'text';
    input_data.id = 'text_' + i;
    input_data.name = 'itemCode_' + i;
    input_data.placeholder = '商品コード';
    var parent = document.getElementById('form_area');
    parent.appendChild(input_data);

    //商品数入力欄
    var input_num = document.createElement('input');
    input_num.type = 'number';
    input_num.id = 'num_' + i;
    input_num.name = 'shippingCount_' + i;
    input_num.placeholder = '出荷数';
    input_num.min = 1;
    input_num.step = 1;
    parent.appendChild(input_num);


    //フォーム消去ボタン
    var button_data = document.createElement('button');
    button_data.id = i;
    button_data.onclick = function () { deleteBtn(this); }
    button_data.innerHTML = '削除';
    parent.appendChild(button_data);

    //改行タグ
    var input_br = document.createElement('br');
    input_br.id = 'br_' + i;
    parent.appendChild(input_br);


    i++;
}

//フォームタグ（input,button,br）の削除
//削除ボタンのidも変数iなのでほかのフォームタグのidと一致する
function deleteBtn(target) {
    var target_id = target.id;
    var parent = document.getElementById('form_area');
    var text_id = document.getElementById('text_' + target_id);
    var num_id = document.getElementById('num_' + target_id);
    var br_id = document.getElementById('br_' + target_id);
    var tgt_id = document.getElementById(target_id);

    parent.removeChild(text_id);
    parent.removeChild(num_id);
    parent.removeChild(br_id);
    parent.removeChild(tgt_id);
}

//内容確認ダイアログ
function confi() {

    var res = confirm('この内容でよろしいですか？');

    return res;
}