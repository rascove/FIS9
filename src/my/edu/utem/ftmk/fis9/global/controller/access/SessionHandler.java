package my.edu.utem.ftmk.fis9.global.controller.access;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * @author Satrya Fajri Pratama
 */
public class SessionHandler extends ViewHandlerWrapper
{
	private ViewHandler handler;
	
	public SessionHandler(ViewHandler handler)
	{
		this.handler = handler;
	}
	
	@Override
	public ViewHandler getWrapped()
	{
		return handler;
	}
	
	@Override
	public UIViewRoot restoreView(FacesContext context, String viewId)
	{
		UIViewRoot root = handler.restoreView(context, viewId);
		
		if (root == null)
			root = createView(context, viewId);
		
		return root;
	}
}