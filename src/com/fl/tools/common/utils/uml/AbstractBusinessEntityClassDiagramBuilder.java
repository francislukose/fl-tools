package com.fl.tools.common.utils.uml;

import java.util.Collection;
import java.util.HashSet;

import com.fl.tools.infr.domain.BusinessEntity;

abstract public class AbstractBusinessEntityClassDiagramBuilder extends AbstractBusinessEntityDefBuilder {
	public abstract Collection<Def> buildDefs(BusinessEntity be, ClassDef target);

	@Override
	public Collection<Def> build(BusinessEntity be) {
		Collection<Def> theDefs = new HashSet<>();

		ClassDef def = (ClassDef) buildClassDef(be);
		def.setSteriotype("Entity");
		theDefs.add(def);
		theDefs.add(new ClassSkinDef(ClassSkinOption.ClassBackgroundColor, "LightGreen", "Entity"));
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
