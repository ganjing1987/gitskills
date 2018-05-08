package com.example.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class LeaveDynamicFormTest extends AbstractTest{

	@Test
	@Deployment(resources = "leaveDynamic.bpmn") //利用注解简化部署
	public void allApproved() throws Exception{
		String currentUserId = "henryyan";
		identitySerice.setAuthenticatedUserId(currentUserId); //设置当前用户
		//启动流程
		ProcessDefinition processDefinition = repositoryService
			.createProcessDefinitionQuery().processDefinitionKey("leave").singleResult();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> variables = new HashMap<String,String>();
		Calendar ca = Calendar.getInstance();          //利用日历对象设置
		String startDate = sdf.format(ca.getTime());   //请假的开始、结束日期
		ca.add(Calendar.DAY_OF_MONTH, 2);
		String endDate = sdf.format(ca.getTime());
		variables.put("startDate", startDate);
		variables.put("endDate", endDate);
		variables.put("reason", "公休");
		
		ProcessInstance processInstace = formService.submitStartFormData(processDefinition.getId(), variables);
		assertNotNull(processInstace);
		//部门领导审批通过
		Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		variables = new HashMap<String,String>();
		variables.put("deptLeaderApproved", "true");
		formService.submitStartFormData(deptLeaderTask.getId(), variables);
		//人事审批通过
		Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
		variables = new HashMap<String,String>();
		variables.put("hrApproved", "true");              //设置审批通过标志
		formService.submitStartFormData(hrTask.getId(), variables);
		//销毁(根据申请人的用户ID读取)
		Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
		variables = new HashMap<String,String>();
		variables.put("reportBackDate", ca.getTime().toString());
		formService.submitStartFormData(reportBackTask.getId(),variables);
		//验证流程是否已经结束
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				 .finished().singleResult();
		assertNotNull(historicProcessInstance);
		//读取历史变量
		Map<String,Object> historyVariables = packageVariables(processInstace);
		//验证执行结果
		assertEquals("ok", historyVariables.get("result"));
	}
	
	//读取历史变量
	private Map<String,Object> packageVariables(ProcessInstance processInstance) {
		Map<String,Object> historyVariables = new HashMap<String,Object>();
		List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(processInstance.getId()).list();
		for(HistoricDetail historicDetail:list){
			if(historicDetail instanceof HistoricDetail){ // 表单中的字段
				HistoricFormProperty field = (HistoricFormProperty)historicDetail;
				historyVariables.put(field.getPropertyId(), field.getPropertyValue());
				System.out.println("form field:taskId="+field.getTaskId()+","+field.getPropertyId()+"="+field.getPropertyValue());
			}else if(historicDetail instanceof HistoricVariableUpdate){
			    HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
                historyVariables.put(variable.getVariableName(), variable.getValue());
                System.out.println("variable: " + variable.getVariableName() + " = " + variable.getValue());
			}
			
		}
		return  historyVariables;
	}
}
