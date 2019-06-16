package client;

import java.util.HashMap;

public class DefaultHashMap<KeyType,ValueType> extends HashMap<KeyType,ValueType> {
	private static final long serialVersionUID = 1L;
	
	ValueType defaultObject;
    public DefaultHashMap(ValueType defaultObject) {
        this.defaultObject = defaultObject;    
    }

    @Override
    public ValueType get(Object key) {
        ValueType returnValue = super.get(key);
        if (returnValue == null) {
            try {
                return defaultObject;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
        	return returnValue;
        }
    }   

}
