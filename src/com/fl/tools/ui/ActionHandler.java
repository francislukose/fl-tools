package com.fl.tools.ui;

import javax.faces.context.FacesContext;

abstract public class ActionHandler {
	public <T> T getManagedBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();

		T bean = (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);

		return bean;
	}

	public void reloadSessionInitializer() {
		ApplicationUIInitializer initializer = getManagedBean("applicationUIInitializer");
		initializer.reload();
	}
}
