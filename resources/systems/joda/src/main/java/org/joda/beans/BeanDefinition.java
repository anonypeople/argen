/*
 *  Copyright 2001-2014 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation defining a bean for code generation.
 * <p>
 * This annotation must be used on classes that should be treated as beans.
 * 
 * @author Stephen Colebourne
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeanDefinition {

    /**
     * The style of bean generation.
     * <p>
     * By default, this follows 'smart' rules.
     * Set to 'minimal' to generate a minimal amount of code.
     * Set to 'full' to generate the full code.
     */
    String style() default "smart";

    /**
     * The scope of the builder class, currently just for immutable beans.
     * <p>
     * By default, this follows 'smart' rules.
     * Set to 'private' to generate a private builder.
     * Set to 'public' to generate a public builder.
     */
    String builderScope() default "smart";

    /**
     * Information about the bean hierarchy.
     * <p>
     * This is needed to add information that cannot be derived.
     * Set to 'immutable' for a subclass of an immutable bean.
     */
    String hierarchy() default "";

    /**
     * Whether to generate code to cache the hash code.
     * <p>
     * Setting this to true will cause the hash code to be cached using the racy single check idiom.
     * The setting only applies to immutable beans.
     */
    boolean cacheHashCode() default false;

}
