package com.iitk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class CreateProductPrices {

	/**
	 * @param args
	 */
	private static String productNameArray[]={"Sony DAV-DZ340K/CE12 5.1 Home Theatre System"

};
	private static String productUrl[]={"http://www.flipkart.com/sony-dav-dz340k-ce12/p/itmd29nzzgvm4kff?pid=HTRD29NMXTFFN42U"
};
	private static double productPriceArray[]={16499
		
};
	public static void main(String[] args) {
		int productId=1;
		double actualProductPrice=500;
		double addedPrice=0;
		double createdPrice=0.0;
		String query="insert into product_price(id,ts,price) values (?,?,?)";
		String productDetailsQuery="insert into product(name,url) values(?,?)";
		String getProductIdQuery="select id from product where url=?";
		try {
			String url = "jdbc:mysql://172.27.22.59:3306/swldb?user=swl&password=lws";
			Connection conn = DriverManager.getConnection(url,"swl","lws");
			PreparedStatement pstPdt=conn.prepareStatement(productDetailsQuery);
			PreparedStatement pst=conn.prepareStatement(query);
			PreparedStatement pstGetPid=conn.prepareStatement(getProductIdQuery);
			for(int k=0;k<productNameArray.length;k++)
			{
            pstPdt.setString(1, productNameArray[k]);
            pstPdt.setString(2, productUrl[k]);
            pstPdt.executeUpdate();
            
            pstGetPid.setString(1, productUrl[k]);
            ResultSet rs=pstGetPid.executeQuery();
            
            if(rs.next())
            {
            	productId=rs.getInt(1);
            }
            actualProductPrice=productPriceArray[k];
            for(int i=0;i<200;i++)
            {
            	
            	addedPrice=actualProductPrice*0.1*Math.random();
            	createdPrice=actualProductPrice-actualProductPrice*0.05+addedPrice;
            	pst.setInt(1, productId);
            	long temmp=System.currentTimeMillis()-(long)(200-i)*86400*1000;
            	Timestamp createDate=new Timestamp(temmp);
            	pst.setTimestamp(2, createDate);
            	pst.setLong(3, Math.round(createdPrice));
            	pst.addBatch();
            }
            pst.executeBatch();
			}
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

	}

}
