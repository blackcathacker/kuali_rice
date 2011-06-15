/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kim.api.group;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectComplete;
import org.kuali.rice.kim.api.group.Group;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@XmlRootElement(name = GroupQueryResults.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = GroupQueryResults.Constants.TYPE_NAME, propOrder = {
		GroupQueryResults.Elements.RESULTS,
		GroupQueryResults.Elements.TOTAL_ROW_COUNT,
		GroupQueryResults.Elements.MORE_RESULTS_AVAILALBE,
		CoreConstants.CommonElements.FUTURE_ELEMENTS })
public class GroupQueryResults implements QueryResults<Group>, ModelObjectComplete {

	@XmlElementWrapper(name = Elements.RESULTS, required = false)
	@XmlElement(name = Elements.RESULT_ELEM, required = false)
	private final List<Group> results;

	@XmlElement(name = Elements.TOTAL_ROW_COUNT, required = false)
	private final Integer totalRowCount;

	@XmlElement(name = Elements.MORE_RESULTS_AVAILALBE, required = true)
	private final boolean moreResultsAvailable;

	@SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

	private GroupQueryResults() {
		this.results = null;
		this.totalRowCount = null;
		this.moreResultsAvailable = false;
	}

	private GroupQueryResults(Builder builder) {
		final List<Group> temp = new ArrayList<Group>();
        for (Group.Builder b : builder.getResults()) {
            if (b != null) {
                temp.add(b.build());
            }
        }

        this.results = Collections.unmodifiableList(temp);
		this.totalRowCount = builder.getTotalRowCount();
		this.moreResultsAvailable = builder.isMoreResultsAvailable();
	}

	@Override
	public List<Group> getResults() {
		return results;
	}
	
	@Override
	public Integer getTotalRowCount() {
		return totalRowCount;
	}

	@Override
	public boolean isMoreResultsAvailable() {
		return moreResultsAvailable;
	}

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public static class Builder implements ModelBuilder, QueryResults<Group.Builder> {

		private List<Group.Builder> results;
		private Integer totalRowCount;
		private boolean moreResultsAvailable;

        public static Builder create() {
            return new Builder();
        }

		private Builder() {
			this.results = new ArrayList<Group.Builder>();
			this.moreResultsAvailable = false;
		}

        @Override
		public GroupQueryResults build() {
			return new GroupQueryResults(this);
		}

        @Override
		public List<Group.Builder> getResults() {
			return Collections.unmodifiableList(this.results);
		}

		public void setResults(List<Group.Builder> results) {
			this.results = new ArrayList<Group.Builder>(results);
		}

        @Override
		public Integer getTotalRowCount() {
			return this.totalRowCount;
		}

		public void setTotalRowCount(Integer totalRowCount) {
			this.totalRowCount = totalRowCount;
		}

        @Override
		public boolean isMoreResultsAvailable() {
			return this.moreResultsAvailable;
		}

		public void setMoreResultsAvailable(boolean moreResultsAvailable) {
			this.moreResultsAvailable = moreResultsAvailable;
		}
		
	}
	
	/**
	 * Defines some internal constants used on this class.
	 */
	public static class Constants {
		public final static String ROOT_ELEMENT_NAME = "GroupQueryResults";
		public final static String TYPE_NAME = "GroupQueryResultsType";
		public final static String[] HASH_CODE_EQUALS_EXCLUDE = { CoreConstants.CommonElements.FUTURE_ELEMENTS };
	}

	/**
	 * A private class which exposes constants which define the XML element
	 * names to use when this object is marshaled to XML.
	 */
	public static class Elements {
		public final static String RESULTS = "results";
		public final static String RESULT_ELEM = "Group";
		public final static String TOTAL_ROW_COUNT = "totalRowCount";
		public final static String MORE_RESULTS_AVAILALBE = "moreResultsAvailable";
	}
	
}
