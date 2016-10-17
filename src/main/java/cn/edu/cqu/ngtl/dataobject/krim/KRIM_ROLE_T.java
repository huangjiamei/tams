/**
 * Copyright 2005-2015 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.cqu.ngtl.dataobject.krim;

import org.kuali.rice.krad.bo.DataObjectBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KRIM_ROLE_T")
public class KRIM_ROLE_T extends DataObjectBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="krimRoleSeq")
    @SequenceGenerator(name="krimRoleSeq",sequenceName="KRIM_ROLE_ID_S",allocationSize=1)
    @Column(name = "ROLE_ID")
    private String id;

    @Column(name = "ROLE_NM")
    private String name;

    @Column(name = "DESC_TXT")
    private String description;

    @Column(name = "ACTV_IND")
    private String active;

    @Column(name = "KIM_TYP_ID")
    private String kimTypeId;

    @Column(name = "NMSPC_CD")
    private String namespaceCode;

	@Transient
	private Boolean checked;
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActive() {
		return this.active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getKimTypeId() {
		return this.kimTypeId;
	}

	public void setKimTypeId(String kimTypeId) {
		this.kimTypeId = kimTypeId;
	}

	public String getNamespaceCode() {
		return this.namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof KRIM_ROLE_T))
			return false;
		KRIM_ROLE_T krimRoleT = (KRIM_ROLE_T) o;
		return krimRoleT.getId().equals(this.getId());
	}
}
