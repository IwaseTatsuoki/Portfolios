package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ItemInfoExecute {

	public static Set<String> getItemNames(Connection con, PreparedStatement ps, ResultSet rs)
			throws Exception {

		Set<String> itemNames = new HashSet<>();

		String sql = "select item_name from item";

		ps = con.prepareStatement(sql);

		rs = ps.executeQuery();


		while (rs.next()) {

			itemNames.add(rs.getString("item_name"));

		}

		return itemNames;

	}

	public static List<String> getSizes(Connection con, PreparedStatement ps, ResultSet rs)
			throws Exception {

		List<String> sizes = new LinkedList<String>();

		String sql = "select size_type from size";

		ps = con.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {

			sizes.add(rs.getString("size_type"));

		}

		return sizes;

	}

	public static List<String> getColors(Connection con, PreparedStatement ps, ResultSet rs)
			throws Exception {

		List<String> colors = new LinkedList<String>();

		String sql = "select color_type from color";

		ps = con.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {

			colors.add(rs.getString("color_type"));

		}

		return colors;

	}

	public static List<String> getCategorys(Connection con, PreparedStatement ps, ResultSet rs)
			throws Exception {

		List<String> categorys = new LinkedList<String>();

		String sql = "select category_type from category";

		ps = con.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {

			categorys.add(rs.getString("category_type"));

		}

		return categorys;

	}

	public static List<String> getSexs(Connection con, PreparedStatement ps, ResultSet rs)
			throws Exception {

		List<String> sexs = new LinkedList<String>();

		String sql = "select sex_type from sex";

		ps = con.prepareStatement(sql);

		rs = ps.executeQuery();

		while (rs.next()) {

			sexs.add(rs.getString("sex_type"));

		}

		return sexs;

	}
}
