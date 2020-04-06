package com.rsmaxwell.infection.config;

public class Pair implements Comparable {

	public String one;
	public String two;

	public Pair(String one, String two) {
		this.one = one;
		this.two = two;
	}

	@Override
	public String toString() {
		return "{ \"one\": " + one + ", \"two\": " + two + " }";
	}

	@Override
	public int compareTo(Object o) {
		Pair other = (Pair) o;

		String a = one + "." + two;
		String b = other.one + "." + other.two;

		return a.compareTo(b);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((one == null) ? 0 : one.hashCode());
		result = prime * result + ((two == null) ? 0 : two.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (one == null) {
			if (other.one != null)
				return false;
		} else if (!one.equals(other.one))
			return false;
		if (two == null) {
			if (other.two != null)
				return false;
		} else if (!two.equals(other.two))
			return false;
		return true;
	}

}
