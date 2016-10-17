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

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "KRIM_PERM_T")
public class KRIM_PERM_T implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PERM_ID")
    protected String id;

    @Column(name = "NMSPC_CD")
    protected String namespaceCode;

    @Column(name = "NM")
    protected String name;

    @Column(name = "DESC_TXT")
    protected String description;

    @Column(name = "PERM_TMPL_ID")
    protected String templateId;

    @Column(name = "ACTV_IND")
    protected String active;

	@Transient
	private Boolean checked;

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getNamespaceCode() {
		return this.namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
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

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof KRIM_PERM_T))
			return false;
		KRIM_PERM_T krimPermT = (KRIM_PERM_T) o;
		return krimPermT.getId().equals(this.getId());
	}
}
