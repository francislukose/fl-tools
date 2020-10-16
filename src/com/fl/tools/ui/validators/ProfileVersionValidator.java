package com.fl.tools.ui.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.fl.tools.infr.domain.ProfileVersionWrapper;
import com.fl.tools.infr.domain.ProfileWrapper;
import com.fl.tools.ui.FacesBean;
import com.fl.tools.ui.beans.ProfileFormBean;
import com.fl.tools.ui.beans.ProfileUIView;

@FacesValidator("com.fl.tools.ui.validators.ProfileVersionValidator")
public class ProfileVersionValidator extends FacesBean implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value.toString().contains(" ")) {
			throw new ValidatorException(new FacesMessage("Space character is not allowed in profile version name."));
		}

		ProfileUIView profileUIView = getManagedBean("profileUIView");
		HtmlInputText profileName = (HtmlInputText) component.getAttributes().get("profileName");
		if (profileName != null && profileName.getValue() != null) {
			for (ProfileWrapper p : profileUIView.getProfiles().getProfiles()) {
				if (p.getName().replaceAll(" ", "").toLowerCase()
						.equalsIgnoreCase(profileName.getValue().toString().replaceAll(" ", "").toLowerCase())) {
					for (ProfileVersionWrapper pv : p.getProfiles()) {
						if (pv.getVersionNumber().toLowerCase().equalsIgnoreCase(value.toString().toLowerCase())) {
							throw new ValidatorException(
									new FacesMessage("Duplicate profile version name. Please choose another name."));
						}
					}
				}
			}
		}
	}

}
