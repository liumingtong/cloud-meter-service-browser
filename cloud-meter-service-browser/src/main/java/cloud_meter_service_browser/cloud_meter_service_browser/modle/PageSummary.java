package cloud_meter_service_browser.cloud_meter_service_browser.modle;

import java.util.Map;

public class PageSummary {
		
	String id;
	String startedDateTime;
	// 网页加载总时长
	String totalTime;
	// 请求个数
	String requestCount;
	// 响应码个数
	Map<String, Integer> respCodeCount;
	// 网页加载总大小
	String totalSize;
	// 负载onload大小
	String onLoadSize;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartedDateTime() {
		return startedDateTime;
	}
	public void setStartedDateTime(String startedDateTime) {
		this.startedDateTime = startedDateTime;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(String requestCount) {
		this.requestCount = requestCount;
	}
	public Map<String, Integer> getRespCodeCount() {
		return respCodeCount;
	}
	public void setRespCodeCount(Map<String, Integer> respCodeCount) {
		this.respCodeCount = respCodeCount;
	}
	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}
	public String getOnLoadSize() {
		return onLoadSize;
	}
	public void setOnLoadSize(String onLoadSize) {
		this.onLoadSize = onLoadSize;
	}

	
}
