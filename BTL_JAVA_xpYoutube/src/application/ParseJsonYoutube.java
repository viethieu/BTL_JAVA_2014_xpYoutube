package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.Item;

public class ParseJsonYoutube {
	private String str;

	public ParseJsonYoutube(String str) {
		this.str = str;
	}

	public List<Item> ParseYoutube() {
		URL jsonURL;
		URLConnection jc;
		String str1;
		String[] slipt;
		List<Item> list = new ArrayList<Item>();
		slipt = str.split(" ");
		str1 = "https://gdata.youtube.com/feeds/api/videos?v=2&alt=jsonc&q=";
		for (int i = 0; i < slipt.length; i++)
			str1 = str1 + "%20" + slipt[i];
		System.out.println(str1);
		try {
			jsonURL = new URL(str1);
			jc = jsonURL.openConnection();
			InputStream is = jc.getInputStream();

			// doc du lieu
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			// lay toan bo du lieu tu api vao xau jsonTxt
			String jsonTxt = sb.toString();

			// parsing json
			JSONObject obj = new JSONObject(jsonTxt);
			JSONObject data = obj.getJSONObject("data");
			JSONArray items = data.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {
				Item item = new Item();

				// title
				String title = (items.getJSONObject(i).getString("title"));
				item.setTitle(title);
				System.out.println(title);

				// duration
				int duration = items.getJSONObject(i).getInt("duration");
				String str = duration / 60 + ":" + duration % 60;
				item.setDuartion(str);
				System.out.println(str);

				// date
				String date = items.getJSONObject(i).getString("uploaded");
				date = date.substring(0, 10);
				item.setdate(date);
				System.out.println(date);

				// view count
				int viewCount = items.getJSONObject(i).getInt("viewCount");
				item.setViewCount(viewCount + "");
				System.out.println(viewCount);

				// ảnh
				String image = items.getJSONObject(i).getJSONObject("thumbnail").getString("sqDefault");
				item.seticon(image);
				System.out.println(image);

				// link
				String link = items.getJSONObject(i).getJSONObject("player").getString("default");
				item.setLink(link);
				System.out.println(link);

				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
}
