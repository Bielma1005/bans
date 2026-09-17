package bansy21.Utilerias;

public class Numero 
{
	public String format(String number,int decimals) 
	{
		int i,d;
		 // set default values
		if(number=="") 
			number="0";
		if(decimals==0) 
			decimals=2;

		// round number to specified number of decimals
		double temp1=Math.round((new Double(number)).doubleValue()*Math.pow(10,(new Double(decimals)).doubleValue()));
		number=""+temp1*Math.pow(10,-(new Double(decimals)).doubleValue());
		
		// find index of decimal point
		d=number.indexOf(".");
		// truncate number to desired length
		if (number.length()>=(d+decimals+1))
		{
			number=number.substring(0,d+decimals+1);
		}
		// if number is shorter than desired length
		// pad number with trailing zeros
		while(number.length()<=d+decimals)
			number=number+"0";
		return number;
	}	
}
