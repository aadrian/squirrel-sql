package net.sourceforge.squirrel_sql.plugins.oracle.expander;

/*
 * Copyright (C) 2002-2003 Colin Bell
 * colbell@users.sourceforge.net
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

import net.sourceforge.squirrel_sql.client.session.ISession;
import net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.INodeExpander;
import net.sourceforge.squirrel_sql.client.session.mainpanel.objecttree.ObjectTreeNode;
import net.sourceforge.squirrel_sql.fw.sql.DatabaseObjectInfo;
import net.sourceforge.squirrel_sql.fw.sql.IDatabaseObjectInfo;
import net.sourceforge.squirrel_sql.fw.sql.IObjectTypes;
import net.sourceforge.squirrel_sql.fw.sql.ISQLConnection;
import net.sourceforge.squirrel_sql.fw.sql.SQLUtilities;
import net.sourceforge.squirrel_sql.fw.sql.databasemetadata.SQLDatabaseMetaData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the expanding of the "Session Parent" node. It will give a list of all the sessions.
 * 
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
public class SessionParentExpander implements INodeExpander
{

	/** SQL that retrieves the data. */
	private static String SQL = "select sid||' ('||schemaname||')' from sys.v_$session";
   private IObjectTypes _objectTypes;

   /**
	 * Default ctor.
    * @param objectTypes
    */
	public SessionParentExpander(IObjectTypes objectTypes)
	{
		super();
      _objectTypes = objectTypes;
   }

	/**
	 * Create the child nodes for the passed parent node and return them. Note that this method should
	 * <B>not</B> actually add the child nodes to the parent node as this is taken care of in the caller.
	 * 
	 * @param session
	 *           Current session.
	 * @param node
	 *           Node to be expanded.
	 * @return A list of <TT>ObjectTreeNode</TT> objects representing the child nodes for the passed node.
	 */
	public List<ObjectTreeNode> createChildren(ISession session, ObjectTreeNode parentNode)
		throws SQLException
	{
		final List<ObjectTreeNode> childNodes = new ArrayList<ObjectTreeNode>();
		final ISQLConnection conn = session.getSQLConnection();
		final SQLDatabaseMetaData md = session.getSQLConnection().getSQLMetaData();
		final IDatabaseObjectInfo parentDbinfo = parentNode.getDatabaseObjectInfo();
		final String schemaName = parentDbinfo.getSchemaName();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// There is a reason that we don't want to use DatabaseObjectType.SESSION here.  The "session"
				// nodes that this expander is creating should not be confused with the SQuirreL session.  These
				// session nodes are Oracle sessions that an administrator has the privileges to see.  So we 
				// must not use DatabaseObjectType.SESSION, or else these nodes get expanded with the database
				// DefaultdatabasExpander.
				IDatabaseObjectInfo doi =
					new DatabaseObjectInfo(null, schemaName, rs.getString(1), _objectTypes.getSession(), md);
				childNodes.add(new ObjectTreeNode(session, doi));
			}
		}
		finally
		{
			SQLUtilities.closeResultSet(rs, true);
		}
		return childNodes;
	}
}
