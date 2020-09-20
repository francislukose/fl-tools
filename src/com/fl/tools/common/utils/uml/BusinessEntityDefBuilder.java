package com.fl.tools.common.utils.uml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityAttribute;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

@Component
public class BusinessEntityDefBuilder implements Builder<List<Def>, BusinessEntity> {
	private static final int MAX_USED_CLASSES_IN_DIAGRAM = 12;

	@Autowired(required = true)
	private BusinessEntityMapView entityMapView;

	@Override
	public List<Def> build(BusinessEntity be) {
		List<Def> theDefs = new ArrayList<>();

		ClassDef def = (ClassDef) buildClassDef(be);
		theDefs.add(def);
		theDefs.addAll(buildUsedClassDefs(be, def));
		theDefs.addAll(buildInheritanceDefs(be));

		return theDefs;
	}

	private Def buildClassDef(BusinessEntity be) {
		ClassDef beDef = new ClassDef(be.getSimpleName());
		buildAttrDefs(beDef, be);
		return beDef;
	}

	private void buildAttrDefs(ClassDef beDef, BusinessEntity be) {
		be.getAttributes().forEach((k, v) -> {
			if (!v.isBusinessObjectType()) {
				beDef.addAttr(new AttrDef("-", k, v.getTypeSimpleName()));
			}
		});
	}

	private List<Def> buildInheritanceDefs(BusinessEntity be) {
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

	private List<Def> buildUsedClassDefs(BusinessEntity be, ClassDef target) {
		List<Def> defs = new ArrayList<>();
		PosSelector posSelector = PosSelector.newInstance();
		int index = 0;
		for (BusinessEntityAttribute v : be.getAttributes().values()) {
			if (v.isBusinessObjectType()) {
				if (index >= MAX_USED_CLASSES_IN_DIAGRAM) {
					target.addAttr(new AttrDef("-", v.getAttributeName(), v.getTypeSimpleName()));
				} else {
					index++;
					defs.add(new ClassDef(v.getTypeSimpleName()));
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

}
