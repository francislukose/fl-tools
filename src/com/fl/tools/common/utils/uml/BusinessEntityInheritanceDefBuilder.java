package com.fl.tools.common.utils.uml;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.BusinessEntity;

@Component
public class BusinessEntityInheritanceDefBuilder extends AbstractBusinessEntityDefBuilder {

	@Override
	public Collection<Def> buildDefs(BusinessEntity be, ClassDef target) {
		Set<Def> defs = new HashSet<>();

		buildAttrDefs(target, be, true, false);
		populateChildren(be, defs, be.getSimpleName());
		populateParent(be, defs, be.getSimpleName());

		return defs;
	}

	void populateChildren(BusinessEntity be, Set<Def> defs, String originalTarget) {
		be.getChildren().forEach((e) -> {
			if (!e.equalsIgnoreCase(originalTarget)) {
				BusinessEntity child = entityMapView.getBusinessEntity(e);
				Def theDef = buildClassDef(child);
				//buildAttrDefs((ClassDef) theDef, child, true, true);
				defs.add(theDef);
				defs.add(new InheritanceRelDef(be.getSimpleName(), e, ViewPosition.DOWN));
			}
		});
	}

	void populateParent(BusinessEntity be, Set<Def> defs, String originalTarget) {
		BusinessEntity parent = entityMapView.getBusinessEntity(be.getDirectParent());
		if (parent != null) {
			defs.add(new InheritanceRelDef(parent.getSimpleName(), be.getSimpleName(), ViewPosition.DOWN));
			Def theDef = buildClassDef(parent);
			// buildAttrDefs((ClassDef) theDef, parent, true, true);
			defs.add(theDef);
			populateChildren(parent, defs, originalTarget);
			populateParent(parent, defs, originalTarget);
		} else {
			defs.add(new InheritanceRelDef(BusinessEntity.BASE_BUSINESS_ENTITY.getSimpleName(), be.getSimpleName(),
					ViewPosition.DOWN));
		}
	}
}
