package cn.edu.cqu.ngtl.optionfinder;

import cn.edu.cqu.ngtl.dao.ut.impl.UTDepartmentDaoJpa;
import cn.edu.cqu.ngtl.dataobject.ut.UTDepartment;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

public class UTDepartmentOptionFinder extends KeyValuesBase {
	private boolean blankOption;

	public List<KeyValue> getKeyValues() {
		List<KeyValue> labels = new ArrayList<KeyValue>();
		List<UTDepartment> utDepartments = new UTDepartmentDaoJpa().getAllUTDepartments();

		List<UTDepartment> sortDepartments = new ArrayList();
		for (UTDepartment s : utDepartments) {
			sortDepartments.add(s);
		}

		if (blankOption) {
			labels.add(new ConcreteKeyValue("", ""));
		}

		//Comparator cmp = new PinYinComparator();
		//Collections.sort(sortDepartments, cmp);

		for (UTDepartment utDepartment : sortDepartments) {
			labels.add(new ConcreteKeyValue(utDepartment.getId().toString(), utDepartment.getName()));
		}
		return labels;
	}

	public boolean isBlankOption() {
		return this.blankOption;
	}

	public void setBlankOption(boolean blankOption) {
		this.blankOption = blankOption;
	}
}
