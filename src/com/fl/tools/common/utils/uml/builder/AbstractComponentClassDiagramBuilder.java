package com.fl.tools.common.utils.uml.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.fl.tools.common.utils.uml.common.AttrDef;
import com.fl.tools.common.utils.uml.common.AttributeModifier;
import com.fl.tools.common.utils.uml.common.ClassDef;
import com.fl.tools.common.utils.uml.common.ClassModifier;
import com.fl.tools.common.utils.uml.common.ClassSkinDef;
import com.fl.tools.common.utils.uml.common.ClassSkinOption;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.infr.domain.AttributeProxy;
import com.fl.tools.infr.domain.ComponentProxy;

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
			ClassDef beDef = new ClassDef(getClassModifier(be), be.getName());
			buildAttrDefs(beDef, be);
			return beDef;
		}

		return Def.NULL_DEF;
	}

	protected ClassModifier[] getClassModifier(ComponentProxy cp) {
		List<ClassModifier> modifiers = new ArrayList<>();
		if (cp.isAbstract()) {
			modifiers.add(ClassModifier.ABSTRACT);
		}
		modifiers.add(
				cp.isInterface() ? ClassModifier.INTERFACE : cp.isEnum() ? ClassModifier.ENUM : ClassModifier.CLASS);

		ClassModifier[] modArray = new ClassModifier[modifiers.size()];

		return modifiers.toArray(modArray);
	}

	protected AttributeModifier[] getAttributeModifier(AttributeProxy ap) {
		List<AttributeModifier> modifiers = new ArrayList<>();

		modifiers.add(ap.isPrivate() ? AttributeModifier.PRIVATE
				: ap.isProtected() ? AttributeModifier.PROTECTED
						: ap.isPublic() ? AttributeModifier.PUBLIC : AttributeModifier.PACKAGE);
		if (ap.isStatic()) {
			modifiers.add(AttributeModifier.STATIC);
		}

		AttributeModifier[] modArray = new AttributeModifier[modifiers.size()];

		return modifiers.toArray(modArray);
	}

	protected void buildAttrDefs(ClassDef beDef, ComponentProxy be, boolean usesAsAttrs, boolean skipPremitiveAttrs) {
		be.getAttributes().forEach((k, v) -> {
			if (!v.isEntity() && !skipPremitiveAttrs) {
				beDef.addAttr(new AttrDef(getAttributeModifier(v), v.getName(), v.getType()));
			} else if (v.isEntity()) {
				if (usesAsAttrs) {
					beDef.addAttr(new AttrDef(getAttributeModifier(v), v.getName(), v.getType()));
				}
			}
		});
	}

	protected void buildAttrDefs(ClassDef beDef, ComponentProxy be) {
		buildAttrDefs(beDef, be, false, false);
	}
}
