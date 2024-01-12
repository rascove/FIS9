package my.edu.utem.ftmk.fis9.global.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class FieldWorksheet<X extends FieldWorkRecord> extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long fieldWorksheetID;
	private long fieldWorkID;
	private Date fieldWorkDate;
	private Date inspectionDate;
	private String recorderID;
	private String inspectorID;
	private String recorderName;
	private String inspectorName;
	private ArrayList<X> fieldWorkRecords; 

	protected final long getFieldWorksheetID()
	{
		return fieldWorksheetID;
	}

	protected final void setFieldWorksheetID(long fieldWorksheetID)
	{
		this.fieldWorksheetID = fieldWorksheetID;
	}

	protected final long getFieldWorkID()
	{
		return fieldWorkID;
	}

	protected final void setFieldWorkID(long fieldWorkID)
	{
		this.fieldWorkID = fieldWorkID;
	}

	protected final Date getFieldWorkDate()
	{
		return fieldWorkDate;
	}

	protected final void setFieldWorkDate(Date fieldWorkDate)
	{
		this.fieldWorkDate = fieldWorkDate;
	}

	public final Date getInspectionDate()
	{
		return inspectionDate;
	}

	public final void setInspectionDate(Date inspectionDate)
	{
		this.inspectionDate = inspectionDate;
	}

	public final String getRecorderID()
	{
		return recorderID;
	}

	public final void setRecorderID(String recorderID)
	{
		this.recorderID = recorderID;
	}

	public final String getInspectorID()
	{
		return inspectorID;
	}

	public final void setInspectorID(String inspectorID)
	{
		this.inspectorID = inspectorID;
	}

	public final String getRecorderName()
	{
		return recorderName;
	}

	public final void setRecorderName(String recorderName)
	{
		this.recorderName = recorderName;
	}

	public final String getInspectorName()
	{
		return inspectorName;
	}

	public final void setInspectorName(String inspectorName)
	{
		this.inspectorName = inspectorName;
	}

	protected final ArrayList<X> getFieldWorkRecords()
	{
		return fieldWorkRecords;
	}

	protected final void setFieldWorkRecords(ArrayList<X> fieldWorkRecords)
	{
		this.fieldWorkRecords = fieldWorkRecords;
	}
}