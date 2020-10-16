package com.fl.tools.ui.validators;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.common.utils.FileUtil;
import com.fl.tools.infr.domain.Component;
import com.fl.tools.infr.domain.DomainObject;

@FacesValidator("com.fl.tools.ui.validators.ProfileComponentsValidator")
public class ProfileComponentsValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Part thePart = (Part) value;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Set<Component> domainObjects = objectMapper.readValue(
					new ByteArrayInputStream(FileUtil.toBytes(thePart.getInputStream())),
					new TypeReference<Set<Component>>() {
					});
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage("Error reading component file:" + e.getMessage()));
		}

		System.out.println(value);
	}

}
