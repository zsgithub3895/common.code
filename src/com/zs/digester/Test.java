package com.zs.digester;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		/*List<Long> privilegeIds = new ArrayList<Long>();
		privilegeIds.add((long) 1);
		privilegeIds.add((long) 2);
		privilegeIds.add((long) 3);
		privilegeIds.add((long) 4);
		privilegeIds.add((long) 5);
		Long[] ll = privilegeIds.toArray(new Long[0]);
		for(Long l:ll){
			System.out.println(l);
		}*/
		
		String s="ALL@";
		s = s.substring(s.indexOf('@'));
		if(s.equals("@")){
			System.out.println("111");
		}
		
	}

}
