package com.fl.tools.common.utils.uml.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.uml.common.AssociationRelDef;
import com.fl.tools.common.utils.uml.common.AttrDef;
import com.fl.tools.common.utils.uml.common.Cardinality;
import com.fl.tools.common.utils.uml.common.ClassDef;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.common.utils.uml.common.InheritanceRelDef;
import com.fl.tools.common.utils.uml.common.ObjectOwner;
import com.fl.tools.common.utils.uml.common.PosSelector;
import com.fl.tools.common.utils.uml.common.ViewPosition;
import com.fl.tools.infr.domain.BusinessEntityAttribute;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.infr.domain.v2.AttributeProxy;
import com.fl.tools.infr.domain.v2.ComponentProxy;
import com.fl.tools.ui.beans.AttributeUIView;

@Component
public class ComponentBasicDefBuilder extends AbstractComponentClassDiagramBuilder {
	private static final int MAX_USED_CLASSES_IN_DIAGRAM = 200;

	public List<Def> buildUsedClassDefs(ComponentProxy be, ClassDef target) {
		List<Def> defs = new ArrayList<>();
		PosSelector posSelector = PosSelector.newInstance();
		int index = 0;
		for (AttributeProxy v : be.getAttributes().values()) {
			if (v.isEntity()) {
				if (index >= MAX_USED_CLASSES_IN_DIAGRAM) {
					target.addAttr(new AttrDef("-", v.getName(), v.getType()));
				} else {
					index++;
					defs.add(buildClassDef(componentUiView.getComponents().getComponent(v.getTypeUUID())));
					AssociationRelDef associationRelDef = new AssociationRelDef(be.getName(), v.getType(), v.getName())
							.setPos(posSelector.getPos());
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

	private Collection<Def> buildInheritanceDefs(ComponentProxy be) {
		List<Def> defs = new ArrayList<>();

		ComponentProxy parent = componentUiView.getComponents().getComponent(be.getParent());
		if (parent != null) {
			defs.add(new InheritanceRelDef(parent.getName(), be.getName(), ViewPosition.DOWN));
		}
		while (parent != null) {
			ClassDef parentDef = (ClassDef) buildClassDef(parent);
			defs.add(parentDef);
			defs.addAll(buildUsedClassDefs(parent, parentDef));

			ComponentProxy parentParent = componentUiView.getComponents().getComponent(parent.getParent());
			if (parentParent != null) {
				defs.add(new InheritanceRelDef(parentParent.getName(), parent.getName(), ViewPosition.DOWN));
			}

			parent = parentParent;
		}

		return defs;
	}

	@Override
	public List<Def> buildDefs(ComponentProxy be, ClassDef target) {
		List<Def> defs = new ArrayList<>();

		defs.addAll(buildUsedClassDefs(be, target));
		defs.addAll(buildInheritanceDefs(be));

		return defs;
	}

}
