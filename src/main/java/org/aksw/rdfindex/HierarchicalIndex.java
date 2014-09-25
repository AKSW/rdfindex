package org.aksw.rdfindex;

import lombok.AllArgsConstructor;
import lombok.ToString;

/** A combination of two indexes. If the primary one returns less than {@code limit}, the secondary one is consulted as well.
 * Items of the secondary index incur a penalty.  
 * @author Konrad Höffner */
@AllArgsConstructor
@ToString
public class HierarchicalIndex extends Index
{
	private static final float	SECONDARY_PENALTY	= 0.8f;
	
	private Index primaryIndex;
	private Index secondaryIndex;
	
	@Override
	public IndexResultSet getResourcesWithScores(String queryString, int limit)
	{
		IndexResultSet rs = primaryIndex.getResourcesWithScores(queryString, limit);
		if(rs.size() < limit)
		{
			IndexResultSet rss = secondaryIndex.getResourcesWithScores(queryString, limit);
			rss.multiplyScore(SECONDARY_PENALTY);
			rs.addAll(rss);
		}
		rs.retainBest(limit);
		return rs;
	}

}