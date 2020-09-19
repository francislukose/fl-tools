package com.fl.tools.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

@Component
public class PlantUMLBuilder implements Builder<String, BusinessEntity> {
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

	static class BusinessEntityDefBuilder implements Builder<List<Def>, BusinessEntity> {

		@Override
		public List<Def> build(BusinessEntity be) {
			List<Def> theDefs = new ArrayList<>();

			theDefs.add(buildClassDef(be));
			theDefs.addAll(buildUsedClassDefs(be));
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
				defs.add(new ClassDef(beh.getNextHierarchy().getSimpleName()));
				defs.add(new InheritanceRelDef(beh.getNextHierarchy().getSimpleName(), beh.getSimpleName(),
						ViewPosision.DOWN));

				beh = beh.getNextHierarchy();
			}

			return defs;
		}

		private List<Def> buildUsedClassDefs(BusinessEntity be) {
			List<Def> defs = new ArrayList<>();

			be.getAttributes().forEach((k, v) -> {
				if (v.isBusinessObjectType()) {
					defs.add(new ClassDef(v.getTypeSimpleName()));

				}
			});

			return defs;
		}

	}

	static interface Def {
		public String toPlantUMLText();
	}

	static enum RelOnOff {
		ON, OFF;
	}

	static enum ViewPosision {

		DOWN("-down-"), UP("-up-");

		private ViewPosision(String n) {
			this.name = n;
		}

		private String name;
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
			return "\n" + from + " <|" + pos.name + " " + to + "\n";
		}

	}

	static class AssociationRelDef extends RelDef {

		@Override
		public String toPlantUMLText() {
			return null;
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
