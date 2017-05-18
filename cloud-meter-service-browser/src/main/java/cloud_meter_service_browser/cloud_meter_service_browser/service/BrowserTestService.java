package cloud_meter_service_browser.cloud_meter_service_browser.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

import cloud_meter_service_browser.cloud_meter_service_browser.constants.HarSummaryConstants;
import cloud_meter_service_browser.cloud_meter_service_browser.modle.HarSummary;
import cloud_meter_service_browser.cloud_meter_service_browser.modle.PageSummary;
import net.sf.json.JSON;

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
		String dataJson = ConvertStream2Json(fullFileName);

		JSONObject jsonObject = new JSONObject(dataJson);
		List<PageSummary> pageSummaries = new ArrayList<PageSummary>();
		JSONArray pagesArray = jsonObject.getJSONObject("log").getJSONArray("pages");
		Iterator<Object> iterator = pagesArray.iterator();
		while (iterator.hasNext()) {
			JSONObject pageObj = (JSONObject) iterator.next();
			
			pageSummaries.add(generatePageSummary(pageObj, dataJson));
		}
		

		HarSummary harSummary = new HarSummary();
		harSummary.setPageSummaries(pageSummaries);

		return JSONObject.wrap(harSummary).toString();
	}

	
	private PageSummary generatePageSummary(JSONObject pageObj, String dataJson) {
		if(pageObj == null) {
			return null;
		}
		PageSummary pageSummary = new PageSummary();
		//设置加载页面的概要信息
		setPageInfo(pageObj, pageSummary);

		List<Integer> bodySizeList = JsonPath.read(dataJson, "$.log.entries[?(@.pageref == '"+pageSummary.getId()+"')].response.bodySize");
		int totalSize = 0;
		for(Integer size : bodySizeList) {
			totalSize += size;
		}
		pageSummary.setTotalSize(String.valueOf(totalSize));
		
		//计算响应码个数
		List<Integer> respCodeList = JsonPath.read(dataJson, "$.log.entries[?(@.pageref == '"+pageSummary.getId()+"')].response.status");
		Map<String, Integer> respCodeCount = new HashMap<String, Integer>();
		for(Integer respCode : respCodeList) {
			String respCodeStr = String.valueOf(respCode);
			int newCount = 0;
			if(respCodeCount.keySet().contains(respCodeStr)) {
				newCount = respCodeCount.get(respCodeStr);
			}
			newCount ++;
			respCodeCount.put(respCodeStr, newCount);
		}
		pageSummary.setRespCodeCount(respCodeCount);
		//请求数
		pageSummary.setRequestCount(String.valueOf(respCodeList.size()));
		return pageSummary;
	}
	
	/**
	 * 设置log/page的信息
	 * @param pageObj
	 * @param pageSummary
	 */
	private void setPageInfo(JSONObject pageObj, PageSummary pageSummary) {
		if(pageObj == null || pageSummary == null) {
			return;
		}
		String startedDateTime = pageObj.getString(HarSummaryConstants.STARTED_DATE_TIME);
		String id = pageObj.getString(HarSummaryConstants.ID);
		JSONObject pageTimingsObj = (JSONObject)pageObj.get(HarSummaryConstants.PAGE_TIMINGS);
		String onLoadTime = pageTimingsObj.get(HarSummaryConstants.ON_LOAD).toString();
		pageSummary.setId(id);
		pageSummary.setStartedDateTime(startedDateTime);
		pageSummary.setOnLoadTime(onLoadTime);
	}

	public static void main(String[] args) {
		System.out.println(BrowserTestService.getInstance().generateHarSummary(""));
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
