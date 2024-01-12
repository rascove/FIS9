package my.edu.utem.ftmk.fis9.global.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Satrya Fajri Pratama
 */
public class DateConverter implements Converter
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Date date = null;
		
		try
		{
			date = sdf.parse(value);
		}
		catch (Exception e)
		{
		}
		
		return date;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object)
	{
		Date date = (Date) object;
		String value = "-";
		
		if (date != null)
			value = sdf.format(date);
		
		return value;
	}
	
	public static String getHijriDate(GregorianCalendar calendar)
	{
		HijrahDate date = HijrahDate.from(LocalDate.of(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH) + 1, calendar.get(GregorianCalendar.DATE)));
		String[] names = {"Muharam", "Safar", "Rabiulawal", "Rabiulakhir", "Jamadilawal", "Jamadilakhir", "Rejab", "Syaaban", "Ramadan", "Syawal", "Zulkaedah", "Zulhijjah"};
		
		return date.get(ChronoField.DAY_OF_MONTH) + " " + names[date.get(ChronoField.MONTH_OF_YEAR) - 1] + " " + date.get(ChronoField.YEAR_OF_ERA);
	}
}