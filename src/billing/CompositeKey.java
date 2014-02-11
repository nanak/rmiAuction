package billing;

import Exceptions.PriceStepIntervalOverlapException;

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

public Double getKey1() {
	return key1;
}

public Double getKey2() {
	return key2;
}

public static void main(String[] args){
	BillingServerSecure s= new BillingServerSecure();
	try {
		s.createPriceStep(0, 10, 5, 10);
		s.createPriceStep(10, 100, 5, 10);
		s.createPriceStep(200, 300, 7, 9);
		s.createPriceStep(300, 0, 5, 6);
	} catch (PriceStepIntervalOverlapException e) {
		System.out.println("ex");
		e.printStackTrace();
	}
}

}