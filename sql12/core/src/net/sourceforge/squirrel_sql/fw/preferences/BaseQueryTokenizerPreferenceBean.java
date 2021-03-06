/*
 * Copyright (C) 2008 Rob Manning
 * manningr@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sourceforge.squirrel_sql.fw.preferences;

/**
 * Base-class for preference beans that store QueryTokenizer preferences.
 */
public class BaseQueryTokenizerPreferenceBean implements IQueryTokenizerPreferenceBean, Cloneable
{

	/** Client Name. */
	private String clientName;

	/** Client version. */
	private String clientVersion;

	protected String statementSeparator = ";";

	protected String procedureSeparator = "/";

	protected String lineComment = "--";

	protected boolean removeMultiLineComments = false;

	protected boolean removeLineComments = true;

	protected boolean installCustomQueryTokenizer = true;

	/**
	 * @return the clientName
	 */
	@Override
	public String getClientName()
	{
		return clientName;
	}

	/**
	 * @param clientName
	 *           the clientName to set
	 */
	@Override
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	/**
	 * @return the clientVersion
	 */
	@Override
	public String getClientVersion()
	{
		return clientVersion;
	}

	/**
	 * @param clientVersion
	 *           the clientVersion to set
	 */
	@Override
	public void setClientVersion(String clientVersion)
	{
		this.clientVersion = clientVersion;
	}

	/**
	 * @return the statementSeparator
	 */
	@Override
	public String getStatementSeparator()
	{
		return statementSeparator;
	}

	/**
	 * @param statementSeparator
	 *           the statementSeparator to set
	 */
	@Override
	public void setStatementSeparator(String statementSeparator)
	{
		this.statementSeparator = statementSeparator;
	}

	/**
	 * @return the procedureSeparator
	 */
	@Override
	public String getProcedureSeparator()
	{
		return procedureSeparator;
	}

	/**
	 * @param procedureSeparator
	 *           the procedureSeparator to set
	 */
	@Override
	public void setProcedureSeparator(String procedureSeparator)
	{
		this.procedureSeparator = procedureSeparator;
	}

	/**
	 * @return the lineComment
	 */
	@Override
	public String getLineComment()
	{
		return lineComment;
	}

	/**
	 * @param lineComment
	 *           the lineComment to set
	 */
	@Override
	public void setLineComment(String lineComment)
	{
		this.lineComment = lineComment;
	}

	@Override
	public boolean isRemoveMultiLineComments()
	{
		return removeMultiLineComments;
	}

	@Override
	public void setRemoveMultiLineComments(boolean removeMultiLineComments)
	{
		this.removeMultiLineComments = removeMultiLineComments;
	}

	@Override
	public boolean isRemoveLineComments()
	{
		return removeLineComments;
	}

	@Override
	public void setRemoveLineComments(boolean removeLineComments)
	{
		this.removeLineComments = removeLineComments;
	}

	@Override
	public boolean isInstallCustomQueryTokenizer()
	{
		return installCustomQueryTokenizer;
	}

	@Override
	public void setInstallCustomQueryTokenizer(boolean installCustomQueryTokenizer)
	{
		this.installCustomQueryTokenizer = installCustomQueryTokenizer;
		
	}

	/**
	 * Implemented in accordance with page 55 of "Effective Java" by Joshua Bloch.
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected BaseQueryTokenizerPreferenceBean clone()
	{
		try
		{
			return (BaseQueryTokenizerPreferenceBean)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new AssertionError(); // Can't happen
		}
	}

	
}
