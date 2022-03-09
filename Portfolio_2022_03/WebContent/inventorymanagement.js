var i = 1;

//出荷時、入荷時フォームの追加
//idとnameに変数iを順番に振っていく
function addForm() {

    //ulタグ
    var input_li = document.createElement('li');
    input_li.className = 'form_li'
    input_li.id = 'li_' + i;
    var ulParent = document.getElementById('form_ul');
    ulParent.appendChild(input_li);

    //商品コード入力欄
    var input_data = document.createElement('input');
    input_data.type = 'text';
    input_data.id = 'text_' + i;
    input_data.name = 'itemCode';
    input_data.placeholder = '商品コード';
    input_data.style = 'margin-right: 5px;';
    var parent = document.getElementById('li_' + i);
    parent.appendChild(input_data);

    //商品数入力欄
    var input_num = document.createElement('input');
    input_num.type = 'number';
    input_num.id = 'num_' + i;
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
    var ul_id = document.getElementById('form_ul');
    var text_id = document.getElementById('text_' + target_id);
    var num_id = document.getElementById('num_' + target_id);
    var li_id = document.getElementById('li_' + target_id);
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