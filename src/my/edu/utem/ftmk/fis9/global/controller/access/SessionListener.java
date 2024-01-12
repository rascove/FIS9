package my.edu.utem.ftmk.fis9.global.controller.access;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import com.ocpsoft.pretty.PrettyContext;

import my.edu.utem.ftmk.fis9.global.DefaultSerializable;
import my.edu.utem.ftmk.fis9.global.controller.mbean.UserManagedBean;
import my.edu.utem.ftmk.fis9.global.util.StringProtector;

/**
 * @author Satrya Fajri Pratama
 */
public class SessionListener implements PhaseListener, DefaultSerializable
{
	private static final long serialVersionUID = VERSION;
	private boolean valid;
	private long next;

	@Override
	public void afterPhase(PhaseEvent event)
	{
		FacesContext context = event.getFacesContext();
		Application application = context.getApplication();
		ExternalContext external = context.getExternalContext();
		NavigationHandler handler = application.getNavigationHandler();
		UserManagedBean userMBean = application.evaluateExpressionGet(context, "#{userMBean}", UserManagedBean.class);
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		String uri = request.getRequestURI();
		long current = System.currentTimeMillis();

		if (current >= next)
		{
			try
			{
				String key = StringProtector.decrypt(external.getInitParameter("key"), 1);
				String[] temp = key.split(":");
				next = current + 3_600_000;
				
				if (temp.length == 3)
				{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date start = sdf.parse(temp[0]), end = sdf.parse(temp[1]), now = new Date();
	
					if (now.after(start) && now.before(end))
					{
						Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
	
						while (nis.hasMoreElements())
						{
							NetworkInterface ni = nis.nextElement();
	
							if (temp[2].equals(Arrays.toString(ni.getHardwareAddress())))
								valid = true;
						}
					}
				}
			}
			catch (Exception e)
			{
			}
		}

		if (valid)
		{
			ArrayList<String> authorized = userMBean.getAuthorized();

			if (authorized != null)
			{
				String url = PrettyContext.getCurrentInstance().getRequestURL().toURL();

				if (!authorized.contains(url))
					handler.handleNavigation(context, null, url.equals("/login") ? "pretty:secured" : "pretty:400");
			}
			else
			{
				if (!uri.endsWith("login.xhtml") && !"partial/ajax".equals(request.getHeader("Faces-Request")))
					handler.handleNavigation(context, null, "pretty:login");
			}
		}
		else
		{
			if (!uri.endsWith("invalid.xhtml"))
			{
				userMBean.setUser(null);
				handler.handleNavigation(context, null, "pretty:invalid");
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event)
	{
	}

	@Override
	public PhaseId getPhaseId()
	{
		return PhaseId.RESTORE_VIEW;
	}
}