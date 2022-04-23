package model;

import java.util.List;

import bean.ItemBean;

public class ArraivalMatch {

	public static boolean checkMatch(List<ItemBean> slipItemBeanList, List<ItemBean> arraivalItemBeanList) {

		int slipIBElementCount = slipItemBeanList.size();

		//伝票と入荷の商品の種類が一致しているか要素数比較
		if(slipItemBeanList.size() == arraivalItemBeanList.size()) {

			//伝票のbeanと入荷のbeanの中身を比較
			//伝票のリストを回す
			for (ItemBean slipItemBean : slipItemBeanList) {

				int i = 0;

				//入荷のリストを回す
				arraival : for (ItemBean arraivalItemBean : arraivalItemBeanList) {

					//beanのオーバーライドしたequalsメソッドで中身を比較
					//伝票の中のbeanと入荷の中のbeanが一つでもマッチしたらループを抜けて伝票の中の次のbeanと比べる
					if(slipItemBean.equals(arraivalItemBean)) {

						break arraival;

					}

					i++;
				}

				if(i == slipIBElementCount) {
					return false;
				}
			}

		}else {

			return false;

		}

		return true;

	}
}
