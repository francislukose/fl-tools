package com.fl.tools.common.utils.uml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

public abstract class AbstractBusinessEntityDefBuilder implements Builder<List<Def>, BusinessEntity> {
	@Autowired(required = true)
	protected BusinessEntityMapView entityMapView;

	public abstract List<Def> buildDefs(BusinessEntity be, ClassDef target);

	@Override
	public List<Def> build(BusinessEntity be) {
		List<Def> theDefs = new ArrayList<>();

		ClassDef def = (ClassDef) buildClassDef(be);
		theDefs.add(def);
		theDefs.addAll(buildDefs(be, def));

		return theDefs;
	}

	protected Def buildClassDef(BusinessEntity be) {
		ClassDef beDef = new ClassDef(be.getSimpleName());
		buildAttrDefs(beDef, be);
		return beDef;
	}

	protected void buildAttrDefs(ClassDef beDef, BusinessEntity be, boolean usesAsAttrs, boolean skipPremitiveAttrs) {
		be.getAttributes().forEach((k, v) -> {
			if (!v.isBusinessObjectType() && !skipPremitiveAttrs) {
				beDef.addAttr(new AttrDef("-", k, v.getTypeSimpleName()));
			} else if (v.isBusinessObjectType()) {
				if (usesAsAttrs) {
					beDef.addAttr(new AttrDef("-", k, v.getTypeSimpleName()));
				}
			}
		});
	}

	protected void buildAttrDefs(ClassDef beDef, BusinessEntity be) {
		buildAttrDefs(beDef, be, false, false);
	}

}
