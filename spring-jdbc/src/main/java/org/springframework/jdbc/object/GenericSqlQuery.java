/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.jdbc.object;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

/**
 * A concrete variant of {@link SqlQuery} which can be configured
 * with a {@link RowMapper}.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @since 3.0
 * @see #setRowMapperClass
 */
public class GenericSqlQuery<T> extends SqlQuery<T> {

	@SuppressWarnings("rawtypes")
	private Class<? extends RowMapper> rowMapperClass;


	/**
	 * Set a {@link RowMapper} class for this query, creating a fresh
	 * {@link RowMapper} instance per execution.
	 */
	@SuppressWarnings("rawtypes")
	public void setRowMapperClass(Class<? extends RowMapper> rowMapperClass) {
		this.rowMapperClass = rowMapperClass;
	}

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(this.rowMapperClass, "'rowMapperClass' is required");
	}


	@Override
	@SuppressWarnings("unchecked")
	protected RowMapper<T> newRowMapper(Object[] parameters, Map context) {
		return BeanUtils.instantiateClass(this.rowMapperClass);
	}

}
