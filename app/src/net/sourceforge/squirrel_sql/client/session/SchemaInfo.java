package net.sourceforge.squirrel_sql.client.session;
/*
 * Copyright (C) 2001-2003 Colin Bell
 * colbell@users.sourceforge.net
 *
 * Copyright (C) 2001 Johan Compagner
 * jcompagner@j-com.nl
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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sourceforge.squirrel_sql.fw.sql.SQLConnection;
import net.sourceforge.squirrel_sql.fw.util.log.ILogger;
import net.sourceforge.squirrel_sql.fw.util.log.LoggerController;

public class SchemaInfo
{
	private boolean _loading = false;

	private final List _keywords = new ArrayList();
	private final List _dataTypes = new ArrayList();
	private final List _functions = new ArrayList();
 
	/** Logger for this class. */
	private static final ILogger s_log =
				LoggerController.createLogger(SchemaInfo.class);

	public SchemaInfo()
	{
		super();
	}

	public SchemaInfo(SQLConnection conn)
	{
		super();
		if (conn == null)
		{
			throw new IllegalArgumentException("SQLConnection == null");
		}
		load(conn);
	}

	public void load(SQLConnection conn)
	{
		if (conn == null)
		{
			throw new IllegalArgumentException("SQLConnection == null");
		}

		_loading = true;
		try
		{
			DatabaseMetaData dmd = null;
			try
			{
				dmd = conn.getSQLMetaData().getJDBCMetaData();
			}
			catch (Exception ex)
			{
				s_log.error("Error retrieving metadata", ex);
			}

			try
			{
				loadKeywords(conn, dmd);
			}
			catch (Exception ex)
			{
				s_log.error("Error loading keywords", ex);
			}

			try
			{
				loadDataTypes(conn, dmd);
			}
			catch (Exception ex)
			{
				s_log.error("Error loading data types", ex);
			}

			try
			{
				loadFunctions(conn, dmd);
			}
			catch (Exception ex)
			{
				s_log.error("Error loading functions", ex);
			}
		}
		finally
		{
			_loading = false;
		}
	}

	/**
	 * Retrieve whether the passed string is a keyword.
	 * 
	 * @param	keyword		String to check.
	 * 
	 * @return	<TT>true</TT> if a keyword.
	 */
	public boolean isKeyword(String data)
	{
		if (!_loading && data != null)
		{
			return _keywords.contains(data.toUpperCase());
		}
		return false;
	}

	/**
	 * Retrieve whether the passed string is a data type.
	 * 
	 * @param	keyword		String to check.
	 * 
	 * @return	<TT>true</TT> if a data type.
	 */
	public boolean isDataType(String data)
	{
		if (!_loading && data != null)
		{
			return _dataTypes.contains(data.toUpperCase());
		}
		return false;
	}

	/**
	 * Retrieve whether the passed string is a function.
	 * 
	 * @param	keyword		String to check.
	 * 
	 * @return	<TT>true</TT> if a function.
	 */
	public boolean isFunction(String data)
	{
		if (!_loading && data != null)
		{
			return _functions.contains(data.toUpperCase());
		}
		return false;
	}

	private void loadKeywords(SQLConnection conn, DatabaseMetaData dmd)
	{
		try
		{
			_keywords.add("ABSOLUTE");
			_keywords.add("ACTION");
			_keywords.add("ADD");
			_keywords.add("ALL");
			_keywords.add("ALTER");
			_keywords.add("AND");
			_keywords.add("AS");
			_keywords.add("ASC");
			_keywords.add("ASSERTION");
			_keywords.add("AUTHORIZATION");
			_keywords.add("AVG");
			_keywords.add("BETWEEN");
			_keywords.add("BY");
			_keywords.add("CASCADE");
			_keywords.add("CASCADED");
			_keywords.add("CATALOG");
			_keywords.add("CHARACTER");
			_keywords.add("CHECK");
			_keywords.add("COLLATE");
			_keywords.add("COLLATION");
			_keywords.add("COLUMN");
			_keywords.add("COMMIT");
			_keywords.add("COMMITTED");
			_keywords.add("CONNECT");
			_keywords.add("CONNECTION");
			_keywords.add("CONSTRAINT");
			_keywords.add("COUNT");
			_keywords.add("CORRESPONDING");
			_keywords.add("CREATE");
			_keywords.add("CROSS");
			_keywords.add("CURRENT");
			_keywords.add("CURSOR");
			_keywords.add("DECLARE");
			_keywords.add("DEFAULT");
			_keywords.add("DEFERRABLE");
			_keywords.add("DEFERRED");
			_keywords.add("DELETE");
			_keywords.add("DESC");
			_keywords.add("DIAGNOSTICS");
			_keywords.add("DISCONNECT");
			_keywords.add("DISTINCT");
			_keywords.add("DOMAIN");
			_keywords.add("DROP");
			_keywords.add("ESCAPE");
			_keywords.add("EXCEPT");
			_keywords.add("EXISTS");
			_keywords.add("EXTERNAL");
			_keywords.add("FALSE");
			_keywords.add("FETCH");
			_keywords.add("FIRST");
			_keywords.add("FOREIGN");
			_keywords.add("FROM");
			_keywords.add("FULL");
			_keywords.add("GET");
			_keywords.add("GLOBAL");
			_keywords.add("GRANT");
			_keywords.add("GROUP");
			_keywords.add("HAVING");
			_keywords.add("IDENTITY");
			_keywords.add("IMMEDIATE");
			_keywords.add("IN");
			_keywords.add("INITIALLY");
			_keywords.add("INNER");
			_keywords.add("INSENSITIVE");
			_keywords.add("INSERT");
			_keywords.add("INTERSECT");
			_keywords.add("INTO");
			_keywords.add("IS");
			_keywords.add("ISOLATION");
			_keywords.add("JOIN");
			_keywords.add("KEY");
			_keywords.add("LAST");
			_keywords.add("LEFT");
			_keywords.add("LEVEL");
			_keywords.add("LIKE");
			_keywords.add("LOCAL");
			_keywords.add("MATCH");
			_keywords.add("MAX");
			_keywords.add("MIN");
			_keywords.add("NAMES");
			_keywords.add("NEXT");
			_keywords.add("NO");
			_keywords.add("NOT");
			_keywords.add("NULL");
			_keywords.add("OF");
			_keywords.add("ON");
			_keywords.add("ONLY");
			_keywords.add("OPEN");
			_keywords.add("OPTION");
			_keywords.add("OR");
			_keywords.add("ORDER");
			_keywords.add("OUTER");
			_keywords.add("OVERLAPS");
			_keywords.add("PARTIAL");
			_keywords.add("PRESERVE");
			_keywords.add("PRIMARY");
			_keywords.add("PRIOR");
			_keywords.add("PRIVILIGES");
			_keywords.add("PUBLIC");
			_keywords.add("READ");
			_keywords.add("REFERENCES");
			_keywords.add("RELATIVE");
			_keywords.add("REPEATABLE");
			_keywords.add("RESTRICT");
			_keywords.add("REVOKE");
			_keywords.add("RIGHT");
			_keywords.add("ROLLBACK");
			_keywords.add("ROWS");
			_keywords.add("SCHEMA");
			_keywords.add("SCROLL");
			_keywords.add("SELECT");
			_keywords.add("SERIALIZABLE");
			_keywords.add("SESSION");
			_keywords.add("SET");
			_keywords.add("SIZE");
			_keywords.add("SOME");
			_keywords.add("SUM");
			_keywords.add("TABLE");
			_keywords.add("TEMPORARY");
			_keywords.add("THEN");
			_keywords.add("TIME");
			_keywords.add("TO");
			_keywords.add("TRANSACTION");
			_keywords.add("TRIGGER");
			_keywords.add("TRUE");
			_keywords.add("UNCOMMITTED");
			_keywords.add("UNION");
			_keywords.add("UNIQUE");
			_keywords.add("UNKNOWN");
			_keywords.add("UPDATE");
			_keywords.add("USAGE");
			_keywords.add("USER");
			_keywords.add("USING");
			_keywords.add("VALUES");
			_keywords.add("VIEW");
			_keywords.add("WHERE");
			_keywords.add("WITH");
			_keywords.add("WORK");
			_keywords.add("WRITE");
			_keywords.add("ZONE");

			// Not actually in the std.
			_keywords.add("INDEX");

			// Extra _keywords that this DBMS supports.
			if (dmd != null)
			{
				StringBuffer buf = new StringBuffer(1024);

				try
				{
					buf.append(dmd.getSQLKeywords());
				}
				catch (Throwable ex)
				{
					s_log.error("Error retrieving DBMS _keywords", ex);
				}

				StringTokenizer strtok = new StringTokenizer(buf.toString(), ",");

				while (strtok.hasMoreTokens())
				{
					_keywords.add(strtok.nextToken().trim());
				}

				try
				{
					addSingleKeyword(dmd.getCatalogTerm());
				}
				catch (Throwable ex)
				{
					s_log.error("Error", ex);
				}

				try
				{
					addSingleKeyword(dmd.getSchemaTerm());
				}
				catch (Throwable ex)
				{
					s_log.error("Error", ex);
				}

				try
				{
					addSingleKeyword(dmd.getProcedureTerm());
				}
				catch (Throwable ex)
				{
					s_log.error("Error", ex);
				}
			}
		}
		catch (Throwable ex)
		{
			s_log.error("Error occured creating keyword collection", ex);
		}
	}

	private void loadDataTypes(SQLConnection conn, DatabaseMetaData dmd)
	{
		try
		{
			final ResultSet rs = dmd.getTypeInfo();
			try
			{
				while (rs.next())
				{
					_dataTypes.add(rs.getString(1).trim().toUpperCase());
				}
			}
			finally
			{
				rs.close();
			}
		}
		catch (Throwable ex)
		{
			s_log.error("Error occured creating data types collection", ex);
		}
	}

	private void loadFunctions(SQLConnection conn, DatabaseMetaData dmd)
	{
		StringBuffer buf = new StringBuffer(1024);

		try
		{
			buf.append(dmd.getNumericFunctions());
		}
		catch (Throwable ex)
		{
			s_log.error("Error", ex);
		}

		buf.append(",");

		try
		{
			buf.append(dmd.getStringFunctions());
		}
		catch (Throwable ex)
		{
			s_log.error("Error", ex);
		}

		buf.append(",");

		try
		{
			buf.append(dmd.getTimeDateFunctions());
		}
		catch (Throwable ex)
		{
			s_log.error("Error", ex);
		}

		StringTokenizer strtok = new StringTokenizer(buf.toString(), ",");

		while (strtok.hasMoreTokens())
		{
			final String func = strtok.nextToken().trim();
			if (func.length() > 0)
			{
				_functions.add(func.toUpperCase());
			}
		}
	}

	private void addSingleKeyword(String keyword)
	{
		if (keyword != null)
		{
			keyword = keyword.trim();

			if (keyword.length() > 0)
			{
				_keywords.add(keyword.toUpperCase());
			}
		}
	}


}
