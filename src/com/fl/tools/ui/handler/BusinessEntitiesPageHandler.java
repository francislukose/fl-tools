package com.fl.tools.ui.handler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.SelectedBusinessEntityDto;
import com.fl.tools.common.dto.SelectedTypeListDto;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

@Component
@ManagedBean
public class BusinessEntitiesPageHandler {
	@Autowired
	private BusinessEntityMapView businessEntityMapView;
	private SelectedBusinessEntityDto entitySelection;
	private SelectedTypeListDto typeListSelection;

	protected void populateParents() {
		BusinessEntityHierarchy current = entitySelection.getEntity().getHierarchy().getNextHierarchy();
		while (current != null) {
			entitySelection.addParent(businessEntityMapView.getBusinessEntity(current.getSimpleName()));
			current = current.getNextHierarchy();
		}
	}

	public void handleBusinessEntitySelection(AjaxBehaviorEvent evt) {
		String boSelection = (String) ((HtmlCommandLink) evt.getSource()).getValue();
		entitySelection = new SelectedBusinessEntityDto(businessEntityMapView.getBusinessEntity(boSelection));
		populateParents();
	}

	public void handleTypeListSelection(AjaxBehaviorEvent evt) {
		String boSelection = (String) ((HtmlCommandLink) evt.getSource()).getValue();
		typeListSelection = new SelectedTypeListDto(businessEntityMapView.getTypeList(boSelection));
	}

	public SelectedBusinessEntityDto getEntitySelection() {
		if (entitySelection == null && businessEntityMapView != null) {
			entitySelection = new SelectedBusinessEntityDto(businessEntityMapView.getBusinessEntitiesAsList().get(0));
			populateParents();
		}
		return entitySelection;
	}

	public SelectedTypeListDto getTypeListSelection() {
		if (typeListSelection == null && typeListSelection != null) {
			typeListSelection = new SelectedTypeListDto(businessEntityMapView.getTypeListsAsList().get(0));
		}
		return typeListSelection;
	}

	public List<String> getHierarchy() {
		List<String> hierarchy = new ArrayList<>();
		if (entitySelection != null) {
			hierarchy.add(" - " + entitySelection.getEntity().getHierarchy().getFullName());
			BusinessEntityHierarchy current = entitySelection.getEntity().getHierarchy().getNextHierarchy();
			String space = "      ";
			while (current != null) {
				hierarchy.add(space + " - " + current.getFullName());
				space = space + space;
				current = current.getNextHierarchy();
			}
		}
		return hierarchy;
	}

	public String getPlantUMLFullClassText(BusinessEntity be, boolean allAttr) {
		if (be == null) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append("class ");
		buffer.append(be.getSimpleName());
		buffer.append(" { ");
		be.getAttributes().forEach((k, v) -> {
			if (!v.isBusinessObjectType() || allAttr) {
				buffer.append("\n-");
				buffer.append(v.getAttributeName());
				buffer.append(":");
				buffer.append(v.getTypeSimpleName());
			}
		});
		buffer.append("\n} ");

		return buffer.toString();
	}

	public String getPlantUMLBOAttrBasicClassText(BusinessEntity be) {
		StringBuffer buffer = new StringBuffer();
		be.getAttributes().forEach((k, v) -> {
			if (v.isBusinessObjectType()) {
				buffer.append("\n");
				buffer.append("class ");
				buffer.append(v.getTypeSimpleName());
			}
		});
		return buffer.toString();
	}

	public String getPlantUMLInheritanceText(BusinessEntity be) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");

		BusinessEntityHierarchy h = be.getHierarchy();
		while (h != null && !h.getSimpleName().equalsIgnoreCase(BusinessEntity.BASE_BUSINESS_ENTITY.getSimpleName())) {
			buffer.append("\n");
			buffer.append(h.getNextHierarchy().getSimpleName());
			buffer.append(" <|-down- ");
			buffer.append(h.getSimpleName());

			h = h.getNextHierarchy();
		}
		return buffer.toString();
	}

	static class UpOrDown {
		boolean up = true;

		String get() {
			if (up) {
				up = false;
				return "-up-";
			}
			up = true;
			return "-down-";
		}
	}

	public String getPlantUMLBEComposisionText(BusinessEntity be) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		UpOrDown upOrDown = new UpOrDown();

		be.getAttributes().forEach((k, v) -> {
			if (v.isBusinessObjectType()) {
				buffer.append("\n");
				buffer.append(be.getSimpleName());
				buffer.append(" ");

				if (v.isOneToOne()) {
					buffer.append("\"[1]\" ");
				}
				
				if(v.isManyToOne()) {
					buffer.append("\"[0..*]\" ");
				}
				if(v.isManyToMany()) {
					buffer.append("\"[0..*]\" ");
				}
				if(v.isOneToMany()) {
					buffer.append("\"[1]\" *");
				}
				
				buffer.append(upOrDown.get());
				
				if (v.isOneToOne()) {
					buffer.append("* \"[1]");
				}
				if(v.isManyToOne()) {
					buffer.append("* \"[1]");
				}
				if(v.isManyToMany()) {
					buffer.append("\"[0..*]");
				}
				if(v.isOneToMany()) {
					buffer.append("\"[0..*]");
				}
				
				buffer.append(" ");
				buffer.append(v.getAttributeName());
				buffer.append("\"");
				buffer.append(" ");
				buffer.append(v.getTypeSimpleName());
			}
		});

		return buffer.toString();
	}

	public String getPlantUMLText() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getPlantUMLFullClassText(entitySelection.getEntity(),false));
		BusinessEntityHierarchy current = entitySelection.getEntity().getHierarchy().getNextHierarchy();
		while (current != null) {
			buffer.append("\n");
			if (current.getSimpleName().equalsIgnoreCase(BusinessEntity.BASE_BUSINESS_ENTITY.getSimpleName())) {
				buffer.append(getPlantUMLFullClassText(BusinessEntity.BASE_BUSINESS_ENTITY,false));
			} else {
				buffer.append(
						getPlantUMLFullClassText(businessEntityMapView.getBusinessEntity(current.getSimpleName()), true));
			}
			current = current.getNextHierarchy();
		}

		buffer.append(getPlantUMLBOAttrBasicClassText(entitySelection.getEntity()));
		buffer.append(getPlantUMLInheritanceText(entitySelection.getEntity()));
		buffer.append(getPlantUMLBEComposisionText(entitySelection.getEntity()));

		System.out.println(buffer.toString());

		return buffer.toString();
	}
}
