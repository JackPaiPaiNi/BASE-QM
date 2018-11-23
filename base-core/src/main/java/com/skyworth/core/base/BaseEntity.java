package com.skyworth.core.base;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyworth.core.user.entity.User;
import com.skyworth.core.utils.Identities;
import com.skyworth.core.utils.UserUtils;

public class BaseEntity {
	
	public enum Oper{
		search,add,edit,del
	}
	// 共有属性
	protected Oper oper;
	protected String id;
	protected String zdrid;
	protected String zdr;
	protected Date zdsj;
	protected Integer zt;
	protected String ztmc;
	protected String sjc;
	// 重复提交
	protected String token;
	
	// 审核使用
	protected String processId;
	protected String taskId;
	protected Integer approveType;     //审批类型0提交1通过 2驳回 3终审
	protected String spyj;            //审批意见
	
	// 导入使用
	protected String inportMsg;
	
	// 数据权限控制
	protected String bmid;
	protected String bm;
	
	//流程选择分公司会计
	protected String fgskj;	//分公司会计
	
	public BaseEntity() {
		
	}
	
	public BaseEntity(String id) {
		this();
		this.id = id;
	}
	
	public void preQuery(){
		if (StringUtils.isBlank(this.bmid)) {
			this.bmid = UserUtils.getViewOrgs();
		}
	}
	
	public void preInsert(){
		if (StringUtils.isBlank(this.id)) {
			this.id = Identities.uuid();
		}
		if (StringUtils.isBlank(this.zdrid)) {
			User user = UserUtils.getUser();
			this.zdrid = user.getGh();
			this.zdr = user.getXm();
		}
		this.zdsj = new Date();
	}
	
	public void preUpdate(){
		if (StringUtils.isBlank(this.zdrid)) {
			User user = UserUtils.getUser();
			this.zdrid = user.getGh();
			this.zdr = user.getXm();
		}
		this.zdsj = new Date();
	}
	
	public void preSubmitOrApprove(){
		User user = UserUtils.getUser();
		this.zdrid = user.getGh();
		this.zdr = user.getXm();
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public Oper getOper() {
		return oper;
	}

	public void setOper(Oper oper) {
		this.oper = oper;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public String getZdr() {
		return zdr;
	}

	public void setZdr(String zdr) {
		this.zdr = zdr;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
	}

	public String getSpyj() {
		return spyj;
	}

	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}

	public Integer getApproveType() {
		return approveType;
	}

	public void setApproveType(Integer approveType) {
		this.approveType = approveType;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSjc() {
		return sjc;
	}

	public void setSjc(String sjc) {
		this.sjc = sjc;
	}
	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	public String getInportMsg() {
		return inportMsg;
	}

	public void setInportMsg(String inportMsg) {
		this.inportMsg = inportMsg;
	}
	
	public String getZtmc() {
		return ztmc;
	}

	public void setZtmc(String ztmc) {
		this.ztmc = ztmc;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getFgskj() {
		return fgskj;
	}

	public void setFgskj(String fgskj) {
		this.fgskj = fgskj;
	}
	
}
