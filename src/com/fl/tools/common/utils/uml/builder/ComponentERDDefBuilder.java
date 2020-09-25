package com.fl.tools.common.utils.uml.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.ComponentUtils;
import com.fl.tools.common.utils.uml.common.AttrDef;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.common.utils.uml.common.EntityAttrRef;
import com.fl.tools.common.utils.uml.common.EntityDef;
import com.fl.tools.common.utils.uml.common.SimpleDef;
import com.fl.tools.infr.domain.v2.Annotation;
import com.fl.tools.infr.domain.v2.ComponentProxy;

@Component
public class ComponentERDDefBuilder extends AbstractComponentDefBuilder {

	@Override
	public Collection<Def> build(ComponentProxy component) {
		Set<Def> defs = new HashSet<>();

		EntityDef rootDef = buildDefs(component);
		defs.add(rootDef);

		return defs;
	}

	private EntityDef buildDefs(ComponentProxy component) {
		EntityDef def = new EntityDef(component.getTableName());
		Set<Def> cols = new HashSet<>();
		Set<Def> pkCols = new HashSet<>();
		Set<Def> fkCols = new HashSet<>();

		ComponentUtils.getAttributes(componentUiView.getComponents(), component, true).forEach((v) -> {
			boolean isPK = v.isPrimaryKey();
			boolean isFK = v.isForeignKey();

			v.getDatabaseColumns().forEach((e) -> {
				Map<String, Annotation> metatdataMap = new HashMap<>();

				e.getAssociatedAnnotations().forEach((a) -> {
					metatdataMap.put(a.getName(), a);
				});
				if (isPK) {
					pkCols.add(new EntityAttrRef("*", e.getValue(),
							metatdataMap.containsKey(Annotation.ANN_DATABASE_COL_TYPE)
									? metatdataMap.get(Annotation.ANN_DATABASE_COL_TYPE).getValue()
									: "").setSize(metatdataMap.containsKey(Annotation.ANN_DATABASE_COL_SIZE)
											? metatdataMap.get(Annotation.ANN_DATABASE_COL_SIZE).getValue()
											: null).setSteriotype(
													metatdataMap.containsKey(Annotation.ANN_DATABASE_COL_STERIOTYPE)
															? metatdataMap.get(Annotation.ANN_DATABASE_COL_STERIOTYPE)
																	.getValue()
															: null));
				} else if (isFK) {

				} else {
					cols.add(new EntityAttrRef("", e.getValue(),
							metatdataMap.containsKey(Annotation.ANN_DATABASE_COL_TYPE)
									? metatdataMap.get(Annotation.ANN_DATABASE_COL_TYPE).getValue()
									: "").setSize(metatdataMap.containsKey(Annotation.ANN_DATABASE_COL_SIZE)
											? metatdataMap.get(Annotation.ANN_DATABASE_COL_SIZE).getValue()
											: null).setSteriotype(
													metatdataMap.containsKey(Annotation.ANN_DATABASE_COL_STERIOTYPE)
															? metatdataMap.get(Annotation.ANN_DATABASE_COL_STERIOTYPE)
																	.getValue()
															: null));
				}
			});
		});

		if (pkCols.size() > 0) {
			pkCols.forEach((d) -> {
				def.addAttr(d);
			});
			def.addAttr(new SimpleDef("--"));
		}

		cols.forEach((d) -> {
			def.addAttr(d);
		});

		System.out.println(def.toPlantUMLText());

		return def;
	}

}
