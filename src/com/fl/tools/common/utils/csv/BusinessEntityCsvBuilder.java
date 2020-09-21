package com.fl.tools.common.utils.csv;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.SelectedBusinessEntityDto;
import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityAttribute;

@Component
public class BusinessEntityCsvBuilder implements Builder<List<String>, BusinessEntity> {

	@Override
	public List<String> build(BusinessEntity input) {
		List<String> csv = new ArrayList<>();
		new SelectedBusinessEntityDto(input).getAllAttributes().forEach((v) -> {
			csv.add(getAttrCsv(v.getAttribute(), input));
		});
		return csv;
	}

	protected String getAttrCsv(BusinessEntityAttribute attr, BusinessEntity be) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(be.getSimpleName());
		buffer.append(",");
		buffer.append(be.getTableName());
		buffer.append(",");
		buffer.append(attr.getAttributeName());
		buffer.append(",");
		buffer.append(attr.getTypeSimpleName());
		buffer.append(",");
		buffer.append(attr.getRelationship());

		return buffer.toString();
	}

}
