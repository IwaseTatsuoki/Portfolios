package bean;

import java.util.List;
import java.util.Set;

//一度作ったがいらなくなったが念のため残してる。
public class ItemInfoBean {

	private Set<String> itemNames;
	private List<String> sizes;
	private List<String> colors;
	private List<String> categorys;
	private List<String> sexs;

	public ItemInfoBean() {

	}

	public void setItemNames(Set<String> itemName) {
		this.itemNames = itemName;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public void setCategorys(List<String> categorys) {
		this.categorys = categorys;
	}

	public void setSexs(List<String> sexs) {
		this.sexs = sexs;
	}

	public Set<String> getItemNames() {
		return itemNames;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public List<String> getColors() {
		return colors;
	}

	public List<String> getCategorys() {
		return categorys;
	}

	public List<String> getSexs() {
		return sexs;
	}

}
