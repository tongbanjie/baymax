package com.tongbanjie.baymax.utils;

import java.io.Serializable;

public class Triple<K, V, V2> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8858083025528437907L;

    /**
     * key or object 1.
     */
    private K key;

    /**
     * value or object 2.
     */
    private V value;
    
    private V2 value2;

    /**
     * Constructor.
     */
    public Triple(K k, V v, V2 v2) {
        this.key = k;
        this.value = v;
        this.value2 = v2;
    }

    /**
     * It is xor of K's and V's hashCode. {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hashCode = 0;
        if (key != null) {
            hashCode ^= key.hashCode();
        }
        if (value != null) {
            hashCode ^= value.hashCode();
        }
        if (value2 != null) {
            hashCode ^= value2.hashCode();
        }
        return hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Triple)) {
            return false;
        }
        // can be cast safely.
        @SuppressWarnings("unchecked")
        Triple<K, V, V2> triple = (Triple<K, V, V2>) obj;

        return (triple.key.equals(key) && triple.value.equals(value) && triple.value2.equals(value2));
    }

    /**
     * @return the key.
     */
    public K getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * @return the value.
     */
    public V getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set.
     */
    public void setValue(V value) {
        this.value = value;
    }

	public V2 getValue2() {
		return value2;
	}

	public void setValue2(V2 value2) {
		this.value2 = value2;
	}
}