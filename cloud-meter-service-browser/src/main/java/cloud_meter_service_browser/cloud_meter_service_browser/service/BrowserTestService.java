package cloud_meter_service_browser.cloud_meter_service_browser.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cloud_meter_service_browser.cloud_meter_service_browser.modle.HarSummary;
import cloud_meter_service_browser.cloud_meter_service_browser.modle.PageSummary;
/**
 * 浏览器测试业务类
 * 
 * @author Administrator
 *
 */
public class BrowserTestService {

	private BrowserTestService() {
	}

	private final static BrowserTestService instance = new BrowserTestService();

	/**
	 * 获取BrowserTestService 实例
	 * 
	 * @return
	 */
	public static BrowserTestService getInstance() {
		return instance;
	}

	public String generateHarSummary(String harJson) {
		String fullFileName = "E:\\home\\git\\cloudmeter\\cloudmeter-agent\\cloud-meter-agent\\src\\main\\resources/static/harfile/task123456.har";
		String data = ConvertStream2Json(fullFileName);
		
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONObject("log").getJSONArray("pages");
		Iterator<Object> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JSONObject page = (JSONObject)iterator.next();
			System.out.println(page.toString());
		}
		List<PageSummary> pageSummaries = new ArrayList<PageSummary>();

		HarSummary harSummary = new HarSummary();
		harSummary.setPageSummaries(pageSummaries);

		return JSONObject.valueToString(harSummary);
	}

	/*
	 * public String generatePageSummary(JSONObject jsonObject) { int onLoadSize
	 * = jsonObject.getString(""); String requestCount =
	 * jsonObject.getString(""); String totalSize = jsonObject.getString("");
	 * String totalTime = jsonObject.getString("");
	 * 
	 * Map<String, Integer> respCodeCount = new HashMap<String, Integer>();
	 * HarSummary summay = new HarSummary(); summay.setOnLoadSize(onLoadSize);
	 * summay.setRequestCount(requestCount);
	 * summay.setRespCodeCount(respCodeCount); summay.setTotalSize(totalSize);
	 * summay.setTotalTime(totalTime);
	 * 
	 * return summayJson.toString(); }
	 */

	public static void main(String[] args) {
		BrowserTestService.getInstance().generateHarSummary("");
	}

	private String ConvertStream2Json(String fullFileName) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			File file = new File(fullFileName);
			InputStream inputStream = new FileInputStream(file);
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr; 
	}
}
