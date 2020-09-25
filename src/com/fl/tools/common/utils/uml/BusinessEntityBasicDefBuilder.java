package com.fl.tools.common.utils.uml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityAttribute;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;

@Component("s_BusinessEntityBasicDefBuilder")
public class BusinessEntityBasicDefBuilder extends AbstractBusinessEntityClassDiagramBuilder {
	private static final int MAX_USED_CLASSES_IN_DIAGRAM = 200;

	public List<Def> buildUsedClassDefs(BusinessEntity be, ClassDef target) {
		List<Def> defs = new ArrayList<>();
		PosSelector posSelector = PosSelector.newInstance();
		int index = 0;
		for (BusinessEntityAttribute v : be.getAttributes().values()) {
			if (v.isBusinessObjectType()) {
				if (index >= MAX_USED_CLASSES_IN_DIAGRAM) {
					target.addAttr(new AttrDef("-", v.getAttributeName(), v.getTypeSimpleName()));
				} else {
					index++;
					defs.add(buildClassDef(entityMapView.getBusinessEntity(v.getTypeSimpleName())));
					AssociationRelDef associationRelDef = new AssociationRelDef(be.getSimpleName(),
							v.getTypeSimpleName(), v.getAttributeName()).setPos(posSelector.getPos());
					if (v.isOneToOne()) {
						associationRelDef.setFromCardinality(Cardinality.ONE);
						associationRelDef.setToCardinality(Cardinality.ONE);
						associationRelDef.setOwner(ObjectOwner.TO);
					}

					if (v.isManyToOne()) {
						associationRelDef.setFromCardinality(Cardinality.MANY);
						associationRelDef.setToCardinality(Cardinality.ONE);
						associationRelDef.setOwner(ObjectOwner.TO);
					}
					if (v.isManyToMany()) {
						associationRelDef.setFromCardinality(Cardinality.MANY);
						associationRelDef.setToCardinality(Cardinality.MANY);
						associationRelDef.setOwner(ObjectOwner.NONE);
					}
					if (v.isOneToMany()) {
						associationRelDef.setFromCardinality(Cardinality.ONE);
						associationRelDef.setToCardinality(Cardinality.MANY);
						associationRelDef.setOwner(ObjectOwner.FROM);
					}
					defs.add(associationRelDef);
				}
			}
		}

		return defs;
	}

	private Collection<Def> buildInheritanceDefs(BusinessEntity be) {
		List<Def> defs = new ArrayList<>();

		BusinessEntityHierarchy beh = be.getHierarchy();
		while (beh != null && beh.getNextHierarchy() != null) {
			BusinessEntity parent = entityMapView.getBusinessEntity(beh.getNextHierarchy().getSimpleName());
			if (parent != null) {
				ClassDef parentDef = (ClassDef) buildClassDef(parent);
				defs.add(parentDef);
				defs.addAll(buildUsedClassDefs(parent, parentDef));
			} else {
				defs.add(new ClassDef(beh.getNextHierarchy().getSimpleName()));
			}
			defs.add(new InheritanceRelDef(beh.getNextHierarchy().getSimpleName(), beh.getSimpleName(),
					ViewPosition.DOWN));

			beh = beh.getNextHierarchy();
		}

		return defs;
	}

	@Override
	public List<Def> buildDefs(BusinessEntity be, ClassDef target) {
		List<Def> defs = new ArrayList<>();

		defs.addAll(buildUsedClassDefs(be, target));
		defs.addAll(buildInheritanceDefs(be));

		return defs;
	}

}
