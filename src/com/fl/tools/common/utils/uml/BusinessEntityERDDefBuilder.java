package com.fl.tools.common.utils.uml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.BusinessEntityAttributeDto;
import com.fl.tools.common.dto.SelectedBusinessEntityDto;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityAttribute;

@Component("s_BusinessEntityERDDefBuilder")
public class BusinessEntityERDDefBuilder extends AbstractBusinessEntityDefBuilder {
	protected EntityDef buildEntityDef(BusinessEntity be) {
		EntityDef theDef = new EntityDef(be.getSimpleName());
		buildAttrDefs(theDef, be);
		return theDef;
	}

	protected void buildAttrDefs(EntityDef beDef, BusinessEntity be) {
		new SelectedBusinessEntityDto(be).getAllAttributes().forEach((v) -> {
			if (!v.getAttribute().isBusinessObjectType()) {
				beDef.addAttr(
						new AttrDef("", v.getAttribute().getAttributeName(), v.getAttribute().getTypeSimpleName()));
			}
		});
	}

	public List<Def> buildUsedClassDefs(BusinessEntity be, EntityDef target) {
		List<Def> defs = new ArrayList<>();
		PosSelector posSelector = PosSelector.newInstance();
		for (BusinessEntityAttributeDto v : new SelectedBusinessEntityDto(be).getAllAttributes()) {
			if (v.getAttribute().isBusinessObjectType()) {
				defs.add(buildEntityDef(entityMapView.getBusinessEntity(v.getAttribute().getTypeSimpleName())));
				AssociationRelDef associationRelDef = new AssociationRelDef(be.getSimpleName(),
						v.getAttribute().getTypeSimpleName(), v.getAttribute().getAttributeName())
								.setPos(posSelector.getPos());
				if (v.getAttribute().isOneToOne()) {
					associationRelDef.setFromCardinality(Cardinality.ONE);
					associationRelDef.setToCardinality(Cardinality.ONE);
					associationRelDef.setOwner(ObjectOwner.TO);
				}

				if (v.getAttribute().isManyToOne()) {
					associationRelDef.setFromCardinality(Cardinality.MANY);
					associationRelDef.setToCardinality(Cardinality.ONE);
					associationRelDef.setOwner(ObjectOwner.TO);
				}
				if (v.getAttribute().isManyToMany()) {
					associationRelDef.setFromCardinality(Cardinality.MANY);
					associationRelDef.setToCardinality(Cardinality.MANY);
					associationRelDef.setOwner(ObjectOwner.NONE);
				}
				if (v.getAttribute().isOneToMany()) {
					associationRelDef.setFromCardinality(Cardinality.ONE);
					associationRelDef.setToCardinality(Cardinality.MANY);
					associationRelDef.setOwner(ObjectOwner.FROM);
				}
				defs.add(associationRelDef);
			}
		}

		return defs;
	}

	@Override
	public Collection<Def> build(BusinessEntity be) {
		Collection<Def> theDefs = new HashSet<>();

		theDefs.add(new SimpleDef("hide circle"));
		theDefs.add(new SimpleDef("skinparam linetype ortho"));

		EntityDef rootDef = buildEntityDef(be);
		theDefs.add(rootDef);
		theDefs.addAll(buildUsedClassDefs(be, rootDef));

		return theDefs;
	}

}
