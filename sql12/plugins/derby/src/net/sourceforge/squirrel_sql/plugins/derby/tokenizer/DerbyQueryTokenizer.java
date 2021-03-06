package net.sourceforge.squirrel_sql.plugins.derby.tokenizer;
/*
 * Copyright (C) 2007 Rob Manning
 * manningr@users.sourceforge.net
 * 
 * Based on initial work from Johan Compagner.
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
import net.sourceforge.squirrel_sql.fw.sql.IQueryTokenizer;
import net.sourceforge.squirrel_sql.fw.sql.ITokenizerFactory;
import net.sourceforge.squirrel_sql.fw.sql.QueryTokenizer;

/**
 * This class is loaded by the Derby Plugin and registered with all Derby 
 * Sessions as the query tokenizer if the plugin is loaded.  It handles some
 * of the syntax allowed in ij scripts that would be hard to parse in a 
 * generic way for any database.  Specifically, it handles "run 'script'" 
 * commands which 
 *  
 * @author manningr
 */
public class DerbyQueryTokenizer extends QueryTokenizer implements IQueryTokenizer
{
    private static final String DERBY_SCRIPT_INCLUDE_PREFIX = "run ";

   public DerbyQueryTokenizer(String sep,
                              String linecomment,
                              boolean removeMultiLineComment,
                              boolean removeLineComment)
	{
        super(sep, linecomment, removeMultiLineComment, removeLineComment);
	}

    public void setScriptToTokenize(String script) {
        super.setScriptToTokenize(script);
        
        expandFileIncludes(DERBY_SCRIPT_INCLUDE_PREFIX);
        
        _queryIterator = _queries.iterator();
    }
    
    /**
     * Sets the ITokenizerFactory which is used to create additional instances
     * of the IQueryTokenizer - this is used for handling file includes
     * recursively.  
     */    
	protected void setFactory() {
	    _tokenizerFactory = new ITokenizerFactory() {
	        public IQueryTokenizer getTokenizer() {
	            return new DerbyQueryTokenizer(
                                DerbyQueryTokenizer.this._querySep,
                                DerbyQueryTokenizer.this._lineCommentBegin,
                                DerbyQueryTokenizer.this._removeMultiLineComment,
                                DerbyQueryTokenizer.this._removeLineComment);
            }
        };
    }
            
}
