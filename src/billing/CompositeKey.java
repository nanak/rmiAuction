package billing;


/**
 * Class to store the interval (keys)(in a Map) for price steps and to compare them
 * 
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class CompositeKey {
   private Double key1, key2;
   
   public CompositeKey(double startPrice,double endPrice){
	   this.key1=startPrice;
	   this.key2=endPrice;
   }
   
   
   @Override
   public boolean equals(Object obj) {
       if(obj != null && obj instanceof CompositeKey) {
    	   CompositeKey s = (CompositeKey)obj;
           return key1.equals(s.getKey1()) && key2.equals(s.getKey2());
       }
       return false;
   }
   
/**
 * Tests if an pricestep interval overlaps with another pricestep
 * return true if they overlap
 */
   public boolean overlaps(CompositeKey s) {
    	   if (this.key2 == 0 && s.getKey2() == 0) {
				return true;
			}
			if (this.key1 == 0) {
				return !(s.getKey2() <= this.key1);
			}
			if (this.key2 == 0) {
				return (this.key1 < s.getKey2());
			}
			if (s.getKey2() == 0) {
				return !(this.key2 <= s.getKey1());
			}
		return !(this.key2 <= s.getKey1() || this.key1 >= s.getKey2());
   }

@Override
   public int hashCode() {
       return key1.hashCode() + key2.hashCode();
   }

/**
 * tests if keys are the same
 * @param obj
 * @return
 */
public boolean matches(Object obj) {
	 if(obj != null && obj instanceof CompositeKey) {
  	   CompositeKey o = (CompositeKey)obj;
  		return o.getKey1() == this.key1 && this.key2 == o.getKey2();
	 }
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

}