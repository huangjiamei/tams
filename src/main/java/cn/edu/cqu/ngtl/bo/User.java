package cn.edu.cqu.ngtl.bo;


import cn.edu.cqu.ngtl.dataobject.krim.KRIM_PERM_T;

import java.io.Serializable;
import java.util.List;

/**
 * BO层User类
 *
 * @author Bill
 */
public class User implements Serializable {

	private static final long serialVersionUID = -8868493464273634559L;
	
	private String name;	//姓名

	private String code;	//prncpl_ID pk

	private Integer departmentId;	//所在学院Id

	private String department;	//所在学院

	private String type;		//工号  or 学号
	
	private String tag;	//右上角工号（没有用身份证号）或者学号
	
	private List<KRIM_PERM_T> krimPermTs;	//权限列表
	
	@Override
	public String toString(){
		return this.getName()+" "+ this.getDepartment()+" "+this.getCode()+" "+this.getType(); 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<KRIM_PERM_T> getKrimPermTs() {
		return krimPermTs;
	}

	public void setKrimPermTs(List<KRIM_PERM_T> krimPermTs) {
		this.krimPermTs = krimPermTs;
	}
}
