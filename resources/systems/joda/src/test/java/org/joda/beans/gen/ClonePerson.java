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
package org.joda.beans.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock address JavaBean, used for testing.
 * 
 * @author Stephen Colebourne
 */
@BeanDefinition
public class ClonePerson
        implements Bean {

    @PropertyDefinition
    private List<String> firstNames;
    @PropertyDefinition
    private String[] middleNames;
    @PropertyDefinition
    private String surname;
    @PropertyDefinition(validate = "notNull")
    private Date dateOfBirth;
    @PropertyDefinition
    private Date dateOfDeath;
    @PropertyDefinition
    private final List<Address> addresses = new ArrayList<Address>();
    @PropertyDefinition
    private Company[] companies;
    @PropertyDefinition
    private int[] amounts;

    /**
     * Creates an instance.
     */
    public ClonePerson() {
    }

    //------------------------- AUTOGENERATED START -------------------------
    ///CLOVER:OFF
    /**
     * The meta-bean for {@code ClonePerson}.
     * @return the meta-bean, not null
     */
    public static ClonePerson.Meta meta() {
        return ClonePerson.Meta.INSTANCE;
    }

    static {
        JodaBeanUtils.registerMetaBean(ClonePerson.Meta.INSTANCE);
    }

    @Override
    public ClonePerson.Meta metaBean() {
        return ClonePerson.Meta.INSTANCE;
    }

    @Override
    public <R> Property<R> property(String propertyName) {
        return metaBean().<R>metaProperty(propertyName).createProperty(this);
    }

    @Override
    public Set<String> propertyNames() {
        return metaBean().metaPropertyMap().keySet();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the firstNames.
     * @return the value of the property
     */
    public List<String> getFirstNames() {
        return firstNames;
    }

    /**
     * Sets the firstNames.
     * @param firstNames  the new value of the property
     */
    public void setFirstNames(List<String> firstNames) {
        this.firstNames = firstNames;
    }

    /**
     * Gets the the {@code firstNames} property.
     * @return the property, not null
     */
    public final Property<List<String>> firstNames() {
        return metaBean().firstNames().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the middleNames.
     * @return the value of the property
     */
    public String[] getMiddleNames() {
        return middleNames;
    }

    /**
     * Sets the middleNames.
     * @param middleNames  the new value of the property
     */
    public void setMiddleNames(String[] middleNames) {
        this.middleNames = middleNames;
    }

    /**
     * Gets the the {@code middleNames} property.
     * @return the property, not null
     */
    public final Property<String[]> middleNames() {
        return metaBean().middleNames().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the surname.
     * @return the value of the property
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     * @param surname  the new value of the property
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the the {@code surname} property.
     * @return the property, not null
     */
    public final Property<String> surname() {
        return metaBean().surname().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the dateOfBirth.
     * @return the value of the property, not null
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the dateOfBirth.
     * @param dateOfBirth  the new value of the property, not null
     */
    public void setDateOfBirth(Date dateOfBirth) {
        JodaBeanUtils.notNull(dateOfBirth, "dateOfBirth");
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the the {@code dateOfBirth} property.
     * @return the property, not null
     */
    public final Property<Date> dateOfBirth() {
        return metaBean().dateOfBirth().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the dateOfDeath.
     * @return the value of the property
     */
    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    /**
     * Sets the dateOfDeath.
     * @param dateOfDeath  the new value of the property
     */
    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    /**
     * Gets the the {@code dateOfDeath} property.
     * @return the property, not null
     */
    public final Property<Date> dateOfDeath() {
        return metaBean().dateOfDeath().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the addresses.
     * @return the value of the property, not null
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * Sets the addresses.
     * @param addresses  the new value of the property, not null
     */
    public void setAddresses(List<Address> addresses) {
        JodaBeanUtils.notNull(addresses, "addresses");
        this.addresses.clear();
        this.addresses.addAll(addresses);
    }

    /**
     * Gets the the {@code addresses} property.
     * @return the property, not null
     */
    public final Property<List<Address>> addresses() {
        return metaBean().addresses().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the companies.
     * @return the value of the property
     */
    public Company[] getCompanies() {
        return companies;
    }

    /**
     * Sets the companies.
     * @param companies  the new value of the property
     */
    public void setCompanies(Company[] companies) {
        this.companies = companies;
    }

    /**
     * Gets the the {@code companies} property.
     * @return the property, not null
     */
    public final Property<Company[]> companies() {
        return metaBean().companies().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the amounts.
     * @return the value of the property
     */
    public int[] getAmounts() {
        return amounts;
    }

    /**
     * Sets the amounts.
     * @param amounts  the new value of the property
     */
    public void setAmounts(int[] amounts) {
        this.amounts = amounts;
    }

    /**
     * Gets the the {@code amounts} property.
     * @return the property, not null
     */
    public final Property<int[]> amounts() {
        return metaBean().amounts().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public ClonePerson clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ClonePerson other = (ClonePerson) obj;
            return JodaBeanUtils.equal(getFirstNames(), other.getFirstNames()) &&
                    JodaBeanUtils.equal(getMiddleNames(), other.getMiddleNames()) &&
                    JodaBeanUtils.equal(getSurname(), other.getSurname()) &&
                    JodaBeanUtils.equal(getDateOfBirth(), other.getDateOfBirth()) &&
                    JodaBeanUtils.equal(getDateOfDeath(), other.getDateOfDeath()) &&
                    JodaBeanUtils.equal(getAddresses(), other.getAddresses()) &&
                    JodaBeanUtils.equal(getCompanies(), other.getCompanies()) &&
                    JodaBeanUtils.equal(getAmounts(), other.getAmounts());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getFirstNames());
        hash = hash * 31 + JodaBeanUtils.hashCode(getMiddleNames());
        hash = hash * 31 + JodaBeanUtils.hashCode(getSurname());
        hash = hash * 31 + JodaBeanUtils.hashCode(getDateOfBirth());
        hash = hash * 31 + JodaBeanUtils.hashCode(getDateOfDeath());
        hash = hash * 31 + JodaBeanUtils.hashCode(getAddresses());
        hash = hash * 31 + JodaBeanUtils.hashCode(getCompanies());
        hash = hash * 31 + JodaBeanUtils.hashCode(getAmounts());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(288);
        buf.append("ClonePerson{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("firstNames").append('=').append(JodaBeanUtils.toString(getFirstNames())).append(',').append(' ');
        buf.append("middleNames").append('=').append(JodaBeanUtils.toString(getMiddleNames())).append(',').append(' ');
        buf.append("surname").append('=').append(JodaBeanUtils.toString(getSurname())).append(',').append(' ');
        buf.append("dateOfBirth").append('=').append(JodaBeanUtils.toString(getDateOfBirth())).append(',').append(' ');
        buf.append("dateOfDeath").append('=').append(JodaBeanUtils.toString(getDateOfDeath())).append(',').append(' ');
        buf.append("addresses").append('=').append(JodaBeanUtils.toString(getAddresses())).append(',').append(' ');
        buf.append("companies").append('=').append(JodaBeanUtils.toString(getCompanies())).append(',').append(' ');
        buf.append("amounts").append('=').append(JodaBeanUtils.toString(getAmounts())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ClonePerson}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code firstNames} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<String>> firstNames = DirectMetaProperty.ofReadWrite(
                this, "firstNames", ClonePerson.class, (Class) List.class);
        /**
         * The meta-property for the {@code middleNames} property.
         */
        private final MetaProperty<String[]> middleNames = DirectMetaProperty.ofReadWrite(
                this, "middleNames", ClonePerson.class, String[].class);
        /**
         * The meta-property for the {@code surname} property.
         */
        private final MetaProperty<String> surname = DirectMetaProperty.ofReadWrite(
                this, "surname", ClonePerson.class, String.class);
        /**
         * The meta-property for the {@code dateOfBirth} property.
         */
        private final MetaProperty<Date> dateOfBirth = DirectMetaProperty.ofReadWrite(
                this, "dateOfBirth", ClonePerson.class, Date.class);
        /**
         * The meta-property for the {@code dateOfDeath} property.
         */
        private final MetaProperty<Date> dateOfDeath = DirectMetaProperty.ofReadWrite(
                this, "dateOfDeath", ClonePerson.class, Date.class);
        /**
         * The meta-property for the {@code addresses} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<Address>> addresses = DirectMetaProperty.ofReadWrite(
                this, "addresses", ClonePerson.class, (Class) List.class);
        /**
         * The meta-property for the {@code companies} property.
         */
        private final MetaProperty<Company[]> companies = DirectMetaProperty.ofReadWrite(
                this, "companies", ClonePerson.class, Company[].class);
        /**
         * The meta-property for the {@code amounts} property.
         */
        private final MetaProperty<int[]> amounts = DirectMetaProperty.ofReadWrite(
                this, "amounts", ClonePerson.class, int[].class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "firstNames",
                "middleNames",
                "surname",
                "dateOfBirth",
                "dateOfDeath",
                "addresses",
                "companies",
                "amounts");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case -177061256:  // firstNames
                    return firstNames;
                case 404996787:  // middleNames
                    return middleNames;
                case -1852993317:  // surname
                    return surname;
                case -386871910:  // dateOfBirth
                    return dateOfBirth;
                case -385160369:  // dateOfDeath
                    return dateOfDeath;
                case 874544034:  // addresses
                    return addresses;
                case -1412832805:  // companies
                    return companies;
                case -879772901:  // amounts
                    return amounts;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends ClonePerson> builder() {
            return new DirectBeanBuilder<ClonePerson>(new ClonePerson());
        }

        @Override
        public Class<? extends ClonePerson> beanType() {
            return ClonePerson.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code firstNames} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<List<String>> firstNames() {
            return firstNames;
        }

        /**
         * The meta-property for the {@code middleNames} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String[]> middleNames() {
            return middleNames;
        }

        /**
         * The meta-property for the {@code surname} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> surname() {
            return surname;
        }

        /**
         * The meta-property for the {@code dateOfBirth} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Date> dateOfBirth() {
            return dateOfBirth;
        }

        /**
         * The meta-property for the {@code dateOfDeath} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Date> dateOfDeath() {
            return dateOfDeath;
        }

        /**
         * The meta-property for the {@code addresses} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<List<Address>> addresses() {
            return addresses;
        }

        /**
         * The meta-property for the {@code companies} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Company[]> companies() {
            return companies;
        }

        /**
         * The meta-property for the {@code amounts} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<int[]> amounts() {
            return amounts;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -177061256:  // firstNames
                    return ((ClonePerson) bean).getFirstNames();
                case 404996787:  // middleNames
                    return ((ClonePerson) bean).getMiddleNames();
                case -1852993317:  // surname
                    return ((ClonePerson) bean).getSurname();
                case -386871910:  // dateOfBirth
                    return ((ClonePerson) bean).getDateOfBirth();
                case -385160369:  // dateOfDeath
                    return ((ClonePerson) bean).getDateOfDeath();
                case 874544034:  // addresses
                    return ((ClonePerson) bean).getAddresses();
                case -1412832805:  // companies
                    return ((ClonePerson) bean).getCompanies();
                case -879772901:  // amounts
                    return ((ClonePerson) bean).getAmounts();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -177061256:  // firstNames
                    ((ClonePerson) bean).setFirstNames((List<String>) newValue);
                    return;
                case 404996787:  // middleNames
                    ((ClonePerson) bean).setMiddleNames((String[]) newValue);
                    return;
                case -1852993317:  // surname
                    ((ClonePerson) bean).setSurname((String) newValue);
                    return;
                case -386871910:  // dateOfBirth
                    ((ClonePerson) bean).setDateOfBirth((Date) newValue);
                    return;
                case -385160369:  // dateOfDeath
                    ((ClonePerson) bean).setDateOfDeath((Date) newValue);
                    return;
                case 874544034:  // addresses
                    ((ClonePerson) bean).setAddresses((List<Address>) newValue);
                    return;
                case -1412832805:  // companies
                    ((ClonePerson) bean).setCompanies((Company[]) newValue);
                    return;
                case -879772901:  // amounts
                    ((ClonePerson) bean).setAmounts((int[]) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

        @Override
        protected void validate(Bean bean) {
            JodaBeanUtils.notNull(((ClonePerson) bean).dateOfBirth, "dateOfBirth");
            JodaBeanUtils.notNull(((ClonePerson) bean).addresses, "addresses");
        }

    }

    ///CLOVER:ON
    //-------------------------- AUTOGENERATED END --------------------------
}
