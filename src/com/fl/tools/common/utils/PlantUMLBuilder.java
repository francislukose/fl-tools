package com.fl.tools.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityAttribute;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

@Component
public class PlantUMLBuilder implements Builder<String, BusinessEntity> {
	private static final int MAX_USED_CLASSES_IN_DIAGRAM = 12;
	@Autowired(required = true)
	private BusinessEntityMapView entityMapView;
	private BusinessEntityDefBuilder defBuilder = new BusinessEntityDefBuilder();

	public String build(BusinessEntity be) {
		Assert.notNull(be);

		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer();
		List<Def> defs = defBuilder.build(be);

		defs.forEach((e) -> {
			if (e instanceof ClassDef) {
				buffer.append(e.toPlantUMLText());
			} else {
				buffer2.append(e.toPlantUMLText());
			}
		});

		return buffer.append(buffer2).toString();
	}

	class BusinessEntityDefBuilder implements Builder<List<Def>, BusinessEntity> {

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
						ViewPosision.DOWN));

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

	////////////////////////////////////////////////////////////////////
	static enum ViewPosision {

		DOWN("-down-"), UP("-up-");

		private ViewPosision(String n) {
			this.label = n;
		}

		public String label;
	}

	static enum ObjectOwner {
		FROM, TO, NONE;
	}

	static enum Cardinality {
		ONE("[1]"), MANY("[0..*]");

		Cardinality(String n) {
			this.label = n;
		}

		public String label;
	}

	////////////////////////////////////////////////////////////////////
	static class PosSelector {
		static PosSelector newInstance() {
			return new PosSelector();
		}

		private boolean up = true;

		public ViewPosision getPos() {
			if (up) {
				up = false;
				return ViewPosision.UP;
			}
			up = true;
			return ViewPosision.DOWN;
		}
	}

	////////////////////////////////////////////////////////////////////
	static interface Def {
		public String toPlantUMLText();
	}

	abstract static class RelDef implements Def {

		protected String from;
		protected String to;
		protected ViewPosision pos;

		public RelDef() {

		}

		public RelDef(String from, String to, ViewPosision pos) {
			super();
			this.from = from;
			this.to = to;
			this.pos = pos;
		}

	}

	static class InheritanceRelDef extends RelDef {

		public InheritanceRelDef(String from, String to, ViewPosision pos) {
			super(from, to, pos);
		}

		@Override
		public String toPlantUMLText() {
			return "\n" + from + " <|" + pos.label + " " + to + "\n";
		}
	}

	static class AssociationRelDef extends RelDef {
		private String attrName;
		private Cardinality fromCardinality;
		private Cardinality toCardinality;
		private ObjectOwner owner;

		public AssociationRelDef(String from, String to, String attrName) {
			this.from = from;
			this.to = to;
			this.attrName = attrName;
		}

		public AssociationRelDef setFromCardinality(Cardinality c) {
			this.fromCardinality = c;
			return this;
		}

		public AssociationRelDef setToCardinality(Cardinality c) {
			this.toCardinality = c;
			return this;
		}

		public AssociationRelDef setOwner(ObjectOwner o) {
			this.owner = o;
			return this;
		}

		public AssociationRelDef setPos(ViewPosision p) {
			this.pos = p;
			return this;
		}

		@Override
		public String toPlantUMLText() {
			StringBuffer buffer = new StringBuffer();

			buffer.append("\n");
			buffer.append(this.from);
			buffer.append(" ");
			buffer.append("\"");
			buffer.append(this.fromCardinality.label);
			buffer.append("\"");
			buffer.append(" ");
			if (this.owner == ObjectOwner.FROM) {
				buffer.append("*");
			}
			buffer.append(this.pos.label);
			if (this.owner == ObjectOwner.TO) {
				buffer.append("*");
			}
			buffer.append(" ");
			buffer.append("\"");
			buffer.append(this.toCardinality.label);
			buffer.append(" ");
			buffer.append(this.attrName);
			buffer.append("\"");
			buffer.append(" ");
			buffer.append(this.to);

			return buffer.toString();
		}

	}

	static class ClassDef implements Def {
		private String name;
		private List<AttrDef> attrs = new ArrayList<>();

		public ClassDef(String name) {
			this.name = name;
		}

		public ClassDef addAttr(AttrDef attr) {
			attrs.add(attr);
			return this;
		}

		public String toPlantUMLText() {
			StringBuffer buffer = new StringBuffer();

			buffer.append("\n");
			buffer.append("class ");
			buffer.append(name);
			buffer.append(" {");
			buffer.append("\n");

			attrs.forEach((e) -> {
				buffer.append(e.toPlantUMLText());
				buffer.append("\n");
			});

			buffer.append("\n");
			buffer.append("}");

			return buffer.toString();
		}
	}

	static class AttrDef implements Def {
		private String modifier;
		private String name;
		private String type;

		public AttrDef(String modifier, String name, String type) {
			this.modifier = modifier;
			this.name = name;
			this.type = type;
		}

		public String toPlantUMLText() {
			return modifier + name + ":" + type;
		}

	}
}
