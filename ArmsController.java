package com.tcs.arms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tcs.annotation.Log;
import com.tcs.common.utils.PageUtils;
import com.tcs.common.utils.R;
import com.tcs.arms.model.AttributeControlInfo;
import com.tcs.arms.model.AuditInfo;
import com.tcs.arms.model.MainInfo;
import com.tcs.arms.model.OperationLog;
import com.tcs.arms.model.OperationLogType;
import com.tcs.arms.model.TableStructureInfo;
import com.tcs.arms.model.TaskSyncFilePath;
import com.tcs.arms.model.TaskSyncLog;
import com.tcs.arms.service.ArmsService;
import com.tcs.arms.service.TaskService;
import com.tcs.common.utils.IPUtils;
import com.tcs.common.utils.PageResult;
import com.tcs.common.utils.Result;


/*
 * 文件名：ArmsController.java
 * 描述：ARMS System
 * 作者：Haijun Huang
 * 创建时间：2023-06-01
*/


@RestController
@RequestMapping("/arms")
public class ArmsController {

	@Autowired
	private ArmsService armsService;

	@Autowired
	private TaskService taskService;

	/*
	 * 方法名：mainInfoList
	 * 描述：获取主数据
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@GetMapping("/mainInfoList")
	// @Log(operModule = "报表管理-主数据", operType = OperationLogType.QueryReport,
	// operDesc = "查询报表")
	public Result<?> queryMainInfoList(@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize) {

		List<MainInfo> listRecord = armsService.QueryMainInfoList();
		PageResult<MainInfo> pageUtil = new PageResult<MainInfo>().instance(listRecord, pageNo, pageSize);
		Result<PageResult<MainInfo>> result = new Result<PageResult<MainInfo>>();
		return result.success().put(pageUtil);
	}

	/*
	 * 方法名：queryMainInfo
	 * 描述：带参数查询主数据
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/queryMainInfo")
	@Log(operModule = "报表管理-主数据", operType = OperationLogType.QueryReport, operDesc = "查询报表")
	public Result<?> queryMainInfo(HttpServletRequest request, @RequestBody Map<String, String> params) {
		int pageNo = 1;
		int pageSize = 10;

		Set<Map.Entry<String, String>> entries = params.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key == "pageNo") {
				pageNo = Integer.parseInt(value);
			}
			if (key == "pageSize") {
				pageSize = Integer.parseInt(value);
			}
		}

		List<MainInfo> listRecord = armsService.SearchMainInfo(params);
		PageResult<MainInfo> pageUtil = new PageResult<MainInfo>().instance(listRecord, pageNo, pageSize);
		Result<PageResult<MainInfo>> result = new Result<PageResult<MainInfo>>();
		return result.success().put(pageUtil);
	}

	/*
	 * 方法名：queryAuditInfoList
	 * 描述：带参数MainId查询Audit数据
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@GetMapping("/auditInfoList")
	public Result<?> queryAuditInfoList(@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize, @RequestParam("mainId") Integer mainId) {

		List<AuditInfo> listRecord = armsService.QueryAuditInfoList(mainId);
		PageResult<AuditInfo> pageUtil = new PageResult<AuditInfo>().instance(listRecord, pageNo, pageSize);
		Result<PageResult<AuditInfo>> result = new Result<PageResult<AuditInfo>>();
		return result.success().put(pageUtil);
	}

	/*
	 * 方法名：taskSyncLog
	 * 描述：获取同步日志
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/taskSyncLog")
	public Result<?> queryTaskSyncLogList(@RequestBody Map<String, String> params) {
		int pageNo = 1;
		int pageSize = 10;

		Set<Map.Entry<String, String>> entries = params.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key == "pageNo") {
				pageNo = Integer.parseInt(value);
			}
			if (key == "pageSize") {
				pageSize = Integer.parseInt(value);
			}
		}

		List<TaskSyncLog> listRecord = armsService.QueryTaskSyncLogList(params);
		PageResult<TaskSyncLog> pageUtil = new PageResult<TaskSyncLog>().instance(listRecord, pageNo, pageSize);
		Result<PageResult<TaskSyncLog>> result = new Result<PageResult<TaskSyncLog>>();
		return result.success().put(pageUtil);
	}

	/*
	 * 方法名：operationLog
	 * 描述：获取系统操作日志
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/operationLog")
	public Result<?> queryOperationLogList(@RequestBody Map<String, String> params) {
		int pageNo = 1;
		int pageSize = 10;

		Set<Map.Entry<String, String>> entries = params.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key == "pageNo") {
				pageNo = Integer.parseInt(value);
			}
			if (key == "pageSize") {
				pageSize = Integer.parseInt(value);
			}
		}

		List<OperationLog> listRecord = armsService.QueryOperationLogList(params);
		PageResult<OperationLog> pageUtil = new PageResult<OperationLog>().instance(listRecord, pageNo, pageSize);
		Result<PageResult<OperationLog>> result = new Result<PageResult<OperationLog>>();
		return result.success().put(pageUtil);
	}

	/*
	 * 方法名：getMainPdfFile
	 * 描述：获取二进制流PDF
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("getMainPdfFile")
	@Log(operModule = "报表管理-主数据", operType = OperationLogType.DownLoad, operDesc = "查看PDF")
	public void GetMainPdfFile(HttpServletRequest request, HttpServletResponse response, Integer mainId) {
		armsService.GetMainPdfFile(request, response, mainId);
	}

	/*
	 * 方法名：tableStructureInfo
	 * 描述：获取表结构字段信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/tableStructureInfo")
	public Result<?> queryTableStructureInfo(@RequestParam("tableName") String tableName) {
		List<TableStructureInfo> listRecord = armsService.QueryTableStructureInfo(tableName);
		Result<List<TableStructureInfo>> result = new Result<List<TableStructureInfo>>();
		return result.success().put(listRecord);
	}


	/*
	 * 方法名：saveAttributeControl
	 * 描述：保存字段查询信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/saveAttributeControl")
	public Result<?> save_AttributeControlInfo() {
		List<AttributeControlInfo> list = new ArrayList<AttributeControlInfo>();
		for (int i = 0; i < 5; i++) {
			String attId = String.valueOf(i + 1);
			AttributeControlInfo model = new AttributeControlInfo();
			model.setAttributeId(attId);
			model.setAttributeName("mainId_" + String.valueOf(i));
			model.setIsActive(true);
			model.setTableId("1195971437");
			model.setTableName("arms_mainInfo");
			list.add(model);
		}

		Result<String> result = new Result<String>();
		AttributeControlInfo model = (AttributeControlInfo) list.get(0);
		if (model != null) {
			String tableId = model.getTableId();
			if (tableId != "") {
				// armsService.DeleteAttributeControlInfo(tableId);
			}
		}

		int reValue = armsService.Save_AttributeControlInfo(list);
		if (reValue == 0)
			return result.error().put("操作失敗");

		return result.success().put("操作成功");
	}

	/*
	 * 方法名：saveAttributeControlInfo
	 * 描述：保存字段查询信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/saveAttributeControlInfo")
	@Log(operModule = "系統管理-系統配置", operType = OperationLogType.ChangeSystemConfig, operDesc = "修改Attribute")
	public Result<?> saveAttributeControlInfo(HttpServletRequest request, @RequestBody AttributeControlInfo params) {
		Result<String> result = new Result<String>();

		int reValue = armsService.GetAttributeControlRecord(params.getAttributeId(), params.getTableId());
		if (reValue > 0)
			return result.error().put("存在相同的Attribute數據，請勿重複添加!");

		reValue = armsService.SaveAttributeControlInfo(params);

		if (reValue == 0)
			return result.error().put("操作失敗");

		return result.success().put("操作成功");
	}

	
	/*
	 * 方法名：editAttributeControlInfo
	 * 描述：修改字段查询信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/editAttributeControlInfo")
	@Log(operModule = "系統管理-系統配置", operType = OperationLogType.ChangeSystemConfig, operDesc = "修改Attribute")
	public Result<?> editAttributeControlInfo(HttpServletRequest request, @RequestBody AttributeControlInfo params) {

		Result<String> result = new Result<String>();
		int reValue = armsService.EditAttributeControlInfo(params);

		if (reValue == 0)
			return result.error().put("操作失敗");

		return result.success().put("操作成功");
	}

	
	/*
	 * 方法名：deleteAttributeControlInfo
	 * 描述：删除字段查询信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@GetMapping("/deleteAttributeControlInfo")
	@Log(operModule = "系統管理-系統配置", operType = OperationLogType.ChangeSystemConfig, operDesc = "删除Attribute")
	public Result<?> deleteAttributeControlInfo(HttpServletRequest request, Integer id) {

		Result<String> result = new Result<String>();

		int reValue = armsService.DeleteAttributeControlInfo(id);

		if (reValue == 0)
			return result.error().put("操作失敗");

		return result.success().put("操作成功");
	}

	/*
	 * 方法名：attributeControlInfo
	 * 描述：获取字段查询信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@GetMapping("/attributeControlInfo")
	// @Log(operModule = "数据管理-系統配置", operType = OperationLogType.QUERY, operDesc =
	// "查看Attribute")
	public Result<?> queryAttributeControlInfo(@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize) {

		List<AttributeControlInfo> listRecord = armsService.QueryAttributeControlInfo();

		PageResult<AttributeControlInfo> pageUtil = new PageResult<AttributeControlInfo>().instance(listRecord, pageNo,
				pageSize);

		Result<PageResult<AttributeControlInfo>> result = new Result<PageResult<AttributeControlInfo>>();

		return result.success().put(pageUtil);
	}

	
	/*
	 * 方法名：attributeControlInfoIsActive
	 * 描述：控制查询字段是否激活
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@GetMapping("/attributeControlInfoIsActive")
	public Result<?> queryAttributeControlInfoIsActive() {

		List<AttributeControlInfo> listRecord = armsService.QueryAttributeControlInfoIsActive();

		Result<List<AttributeControlInfo>> result = new Result<List<AttributeControlInfo>>();

		return result.success().put(listRecord);
	}

	@RequestMapping("/saveTaskSyncFilePath")
	@Log(operModule = "系統管理-系統配置", operType = OperationLogType.ChangeSystemConfig, operDesc = "修改源文件同步路径")
	public Result<?> saveTaskSyncFilePath(HttpServletRequest request, @RequestBody TaskSyncFilePath params) {

		Result<String> result = new Result<String>();

		int reValue = armsService.SaveTaskSyncFilePath(params);

		if (reValue == 0)
			return result.error().put("操作失敗");

		return result.success().put("操作成功");
	}

	/*
	 * 方法名：getTaskSyncFilePathInfo
	 * 描述：获取源文件路径设置信息
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/getTaskSyncFilePathInfo")
	public Result<?> queryTaskSyncFilePathInfo() {

		TaskSyncFilePath model = armsService.QueryTaskSyncFilePathInfo();

		Result<TaskSyncFilePath> result = new Result<TaskSyncFilePath>();

		return result.success().put(model);
	}

	/*
	 * 方法名：manualRunTask
	 * 描述：手动执行文件同步
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/manualRunTask")
	@Log(operModule = "任务管理-Job执行情況", operType = OperationLogType.RerunTask, operDesc = "手动执行Task")
	public Result<?> manualRunTask(HttpServletRequest request, String rerunReason, String rerunTaskFile) {

		boolean flag = taskService.resolveFileWithManual(rerunTaskFile);

		Result<String> result = new Result<String>();
		String msg = "操作成功";
		if (!flag) {
			msg = "操作失败，请检查源文件名称输入是否正确！";
			return result.error().put(msg);
		}
		return result.success().put(msg);
	}

	/*
	 * 方法名：exportMainInfoToExcel
	 * 描述：导出Mian主数据报表
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/exportMainInfoToExcel")
	@Log(operModule = "报表管理-主数据", operType = OperationLogType.ExportReport, operDesc = "导出主报表")
	public void exportMainInfoToExcel(HttpServletResponse response, @RequestBody Map<String, String> params)
			throws Exception {
		armsService.exportMainInfoToExcel(response, params);
	}

	/*
	 * 方法名：exportMainInfoToExcel
	 * 描述：导出Audit数据报表
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/exportAuditInfoToExcel")
	@Log(operModule = "报表管理-子数据Audit", operType = OperationLogType.ExportReport, operDesc = "导出Audit报表")
	public void exportAuditInfoToExcel(HttpServletResponse response, Integer mainId) throws Exception {
		armsService.exportAuditInfoToExcel(response, mainId);
	}

	
	/*
	 * 方法名：exportMainInfoToExcel
	 * 描述：文件同步
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/resolveFile")
	public String resolveFile() {
		taskService.resolveFile();
		return "Success";
	}

	
	/*
	 * 方法名：exportMainInfoToExcel
	 * 描述：根据文件名执行同步
	 * 作者：Haijun Huang
	 * 创建时间：2023-06-01
	*/
	@RequestMapping("/resolveStartedByName")
	public String resolveStartedByName() {
		taskService.resolveStartedByName();
		return "Success";
	}
}