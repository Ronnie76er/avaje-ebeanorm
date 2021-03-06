package com.avaje.ebeaninternal.server.persist.dmlbind;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;

/**
 * Add base properties to the BindableList for a bean type.
 * <p>
 * This excludes unique embedded and associated properties.
 * </p>
 */
public class FactoryBaseProperties {

	private final FactoryProperty factoryProperty;
	
	
	public FactoryBaseProperties(boolean bindEncryptDataFirst) {
		factoryProperty = new FactoryProperty(bindEncryptDataFirst);
	}

	/**
	 * Add Bindable for the base properties to the list.
	 */
	public void create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {

	    add(desc.propertiesBaseScalar(), list, desc, mode, withLobs);	    

		BeanPropertyCompound[] compoundProps = desc.propertiesBaseCompound();
		for (int i = 0; i < compoundProps.length; i++) {
		    BeanProperty[] props = compoundProps[i].getScalarProperties();

		    ArrayList<Bindable> newList = new ArrayList<Bindable>(props.length);
		    add(props, newList, desc, mode, withLobs);
		    
		    BindableCompound compoundBindable = new BindableCompound(compoundProps[i], newList);

		    list.add(compoundBindable);
		}
	}

    private void add(BeanProperty[] props, List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {

        for (int i = 0; i < props.length; i++) {

//          if(DmlMode.WHERE.equals(mode) && !withLobs && !props[i].isDbUpdatable()) {
//            // skip non-updatable column from where clause
//          } else {
            Bindable item = factoryProperty.create(props[i], mode, withLobs);
            if (item != null) {
                list.add(item);
            } else {
                // null where readOnly (Secondary tables) or Lob exclusion
            }
//          }
        }

    }

}
