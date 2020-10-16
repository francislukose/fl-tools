package com.fl.tools.ui;

import javax.faces.context.FacesContext;

public abstract class FacesBean {
	public <T> T getManagedBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();

		T bean = (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);

		return bean;
	}
}
