package com.fl.tools.common.utils.uml.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.ComponentUtils;
import com.fl.tools.common.utils.uml.common.AttrDef;
import com.fl.tools.common.utils.uml.common.ClassSkinDef;
import com.fl.tools.common.utils.uml.common.ClassSkinOption;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.common.utils.uml.common.EntityAttrRef;
import com.fl.tools.common.utils.uml.common.EntityDef;
import com.fl.tools.common.utils.uml.common.EntityRelDef;
import com.fl.tools.common.utils.uml.common.EntityRelationship;
import com.fl.tools.common.utils.uml.common.SimpleDef;
import com.fl.tools.common.utils.uml.common.Sortable;
import com.fl.tools.infr.domain.Annotation;
import com.fl.tools.infr.domain.ComponentProxy;

@Component
public class ComponentERDDefBuilder extends AbstractComponentDefBuilder {

	@Override
	public Collection<Def> build(ComponentProxy component) {
		Set<Def> defs = new HashSet<>();

		defs.add(new SimpleDef("skinparam linetype ortho"));
		
		
		EntityDef rootDef = buildEntityDefs(component);
		defs.add(rootDef);

		buildAssociations(rootDef, component, defs);
		
		
		List<Def> sortedDef = new ArrayList<>(defs);
		Collections.sort(sortedDef, new Comparator<Def>() {
			@Override
			public int compare(Def o1, Def o2) {
				if (o1 instanceof Sortable && o2 instanceof Sortable) {
					return ((Sortable) o1).getSortOrder() > ((Sortable) o2).getSortOrder() ? 1 : -1;
				}
				return -1;
			}

		});
		return sortedDef;
	}

	protected void buildAssociations(EntityDef baseDef, ComponentProxy baseComponent, Set<Def> defs) {
		ComponentUtils.getAssociatedComponents(componentUiView.getComponents(), baseComponent).forEach((e) -> {
			defs.add(buildEntityDefs(e.getTypeProxy()));
			if (e.getAttributeProxy().isManyToMany()) {
				defs.add(new EntityRelDef(baseComponent.getTableName(), e.getTypeProxy().getTableName(),
						EntityRelationship.MANY_TO_MANY));
			} else if (e.getAttributeProxy().isOneToMany()) {
				defs.add(new EntityRelDef(baseComponent.getTableName(), e.getTypeProxy().getTableName(),
						EntityRelationship.ONE_TO_MANY));
			} else if (e.getAttributeProxy().isManyToOne()) {
				defs.add(new EntityRelDef(baseComponent.getTableName(), e.getTypeProxy().getTableName(),
						EntityRelationship.MANY_TO_ONE));
			} else if (e.getAttributeProxy().isOneToOne()) {
				defs.add(new EntityRelDef(baseComponent.getTableName(), e.getTypeProxy().getTableName(),
						EntityRelationship.ONE_TO_ONE));
			}

		});
	}

	private EntityDef buildEntityDefs(ComponentProxy component) {
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
															: "PK"));
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

		return def;
	}

}
