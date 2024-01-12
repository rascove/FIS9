package my.edu.utem.ftmk.fis9.global.model;

import my.edu.utem.ftmk.fis9.global.DefaultSerializable;

/**
 * @author Satrya Fajri Pratama
 */
public abstract class AbstractModel implements DefaultSerializable, Comparable<AbstractModel>
{
	private static final long serialVersionUID = VERSION;
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public int compareTo(AbstractModel model)
	{
		return toString().compareTo(model.toString());
	}
}