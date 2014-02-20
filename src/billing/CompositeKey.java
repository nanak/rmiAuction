package billing;


/**
 * Class to store the interval (keys)(in a Map) for price steps and to compare them
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class CompositeKey implements Comparable<CompositeKey>{
   private Double key1, key2;
   
   public CompositeKey(double startPrice,double endPrice){
	   this.key1=startPrice;
	   this.key2=endPrice;
   }
   
/**
 * Tests if an pricestep interval overlaps with another pricestep
 * return true if they overlap
 */
   public boolean overlaps(CompositeKey s) {
	   if (this.key2 == 0 && s.getKey2() == 0) {
			return true;
		}
		if (this.key2 == 0) {
			return (this.key1 < s.getKey2());
		}
		if (s.getKey2() == 0) {
			return !(this.key2 <= s.getKey1());
		}
	return !(this.key2 <= s.getKey1() || this.key1 >= s.getKey2());
}

/**
 * tests if value is between interval
 * @param obj
 * @return
 */
public boolean matches(Double value) {
	if(this.key2==0&&this.key1<=value)return true;
	 if(this.key1<=value&&this.key2>value)return true;
	 return false;
}

/**
 * Getter
 * @return
 */
public Double getKey1() {
	return key1;
}

/**
 * Getter
 * @return
 */
public Double getKey2() {
	return key2;
}

/**
 * Methode zum Vergleichen- Sortieren
 */
@Override
public int compareTo(CompositeKey o) {
	if(o == null ||this.key1<o.getKey1()) return -1;
    if(this.key1>o.getKey1())return 1;
	return 0;
}

}