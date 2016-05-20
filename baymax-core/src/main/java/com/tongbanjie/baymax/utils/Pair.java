package com.tongbanjie.baymax.utils;

public class Pair<T1, T2> {
	private T1 object1;
	private T2 object2;

	public Pair(T1 object1, T2 object2) {
		this.object1 = object1;
		this.object2 = object2;
	}

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (!(o instanceof Pair)){
            return false;
        }
        if (((Pair) o).getObject1() == null || ((Pair) o).getObject2() == null){
            return false;
        }
        if (object1 == null || object2 == null){
            return false;
        }
        return object1.equals(((Pair) o).getObject1()) && object2.equals(((Pair) o).getObject2());
    }

    @Override
    public int hashCode() {
        return object1.hashCode() + object2.hashCode();
    }

    public T1 getObject1() {
		return object1;
	}

	public void setObject1(T1 object1) {
		this.object1 = object1;
	}

	public T2 getObject2() {
		return object2;
	}

	public void setObject2(T2 object2) {
		this.object2 = object2;
	}

    @Override
    public String toString() {
        return object1 + "|" + object2;
    }
}