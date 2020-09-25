package com.fl.tools.common.utils.uml.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.uml.common.ClassDef;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.common.utils.uml.common.InheritanceRelDef;
import com.fl.tools.common.utils.uml.common.ViewPosition;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.v2.ComponentProxy;

@Component
public class ComponentInheritanceDefBuilder extends AbstractComponentClassDiagramBuilder {

	@Override
	public Collection<Def> buildDefs(ComponentProxy be, ClassDef target) {
		Set<Def> defs = new HashSet<>();

		buildAttrDefs(target, be, true, false);
		populateChildren(be, defs, be);
		populateParent(be, defs, be);

		return defs;
	}

	void populateChildren(ComponentProxy be, Set<Def> defs, ComponentProxy originalComponent) {
		be.getChildren().forEach((e) -> {
			if (!e.equalsIgnoreCase(originalComponent.getUUID())) {
				ComponentProxy child = componentUiView.getComponents().getComponent(e);
				Def theDef = buildClassDef(child);
				defs.add(theDef);
				defs.add(new InheritanceRelDef(be.getName(), child.getName(), ViewPosition.DOWN));
				populateChildren(child, defs, originalComponent);
			}
		});
	}

	void populateParent(ComponentProxy be, Set<Def> defs, ComponentProxy originalComponent) {
		ComponentProxy parent = componentUiView.getComponents().getComponent(be.getParent());
		if (parent != null) {
			defs.add(new InheritanceRelDef(parent.getName(), be.getName(), ViewPosition.DOWN));
			Def theDef = buildClassDef(parent);
			defs.add(theDef);
			 populateChildren(parent, defs, originalComponent);
			 populateParent(parent, defs, originalComponent);
		}
	}
}
