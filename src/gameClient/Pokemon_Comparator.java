package gameClient;

import java.util.Comparator;
/**
 * this class implements a comparator of pokemon
 * @author denis
 *
 */
public class Pokemon_Comparator implements Comparator<CL_Pokemon>{

	@Override
	public int compare(CL_Pokemon o1, CL_Pokemon o2) {
		double ans = o2.getValue()-o1.getValue();
		if(ans>0) 
		{
			return 1;
		}
		else if(ans ==0) 
		{
			return 0;
		}
		
		return -1;
	}
}