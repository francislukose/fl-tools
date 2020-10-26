package com.fl.tools.ui;

import javax.faces.context.FacesContext;

abstract public class ActionHandler extends FacesBean {

	public void reloadSessionInitializer() {
		ApplicationUIInitializer initializer = getManagedBean("applicationUIInitializer");
		initializer.reload();
	}
	
	public void flushSession() {
		ApplicationUIInitializer initializer = getManagedBean("applicationUIInitializer");
		initializer.flush();
	}
}
