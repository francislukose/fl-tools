package com.fl.tools.common.utils.uml.builder;

import java.util.Collection;
import java.util.HashSet;

import com.fl.tools.common.utils.uml.common.AttrDef;
import com.fl.tools.common.utils.uml.common.ClassDef;
import com.fl.tools.common.utils.uml.common.ClassSkinDef;
import com.fl.tools.common.utils.uml.common.ClassSkinOption;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.v2.ComponentProxy;

abstract public class AbstractComponentClassDiagramBuilder extends AbstractComponentDefBuilder {
	public abstract Collection<Def> buildDefs(ComponentProxy be, ClassDef target);

	@Override
	public Collection<Def> build(ComponentProxy be) {
		Collection<Def> theDefs = new HashSet<>();

		ClassDef def = (ClassDef) buildClassDef(be);
		def.setSteriotype("Entity");
		theDefs.add(def);
		theDefs.add(new ClassSkinDef(ClassSkinOption.ClassBackgroundColor, "LightGreen", "Entity"));
		theDefs.addAll(buildDefs(be, def));

		return theDefs;
	}

	protected Def buildClassDef(ComponentProxy be) {
		if (be != null) {
			ClassDef beDef = new ClassDef(be.getName());
			buildAttrDefs(beDef, be);
			return beDef;
		}

		return Def.NULL_DEF;
	}

	protected void buildAttrDefs(ClassDef beDef, ComponentProxy be, boolean usesAsAttrs, boolean skipPremitiveAttrs) {
		be.getAttributes().forEach((k, v) -> {
			if (!v.isEntity() && !skipPremitiveAttrs) {
				beDef.addAttr(new AttrDef("-", v.getName(), v.getType()));
			} else if (v.isEntity()) {
				if (usesAsAttrs) {
					beDef.addAttr(new AttrDef("-", v.getName(), v.getType()));
				}
			}
		});
	}

	protected void buildAttrDefs(ClassDef beDef, ComponentProxy be) {
		buildAttrDefs(beDef, be, false, false);
	}
}
