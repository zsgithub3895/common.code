package com.zs.quick.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionSort {

	public static void main(String[] args) {
		List<PlayResponseLog> plays =new ArrayList<PlayResponseLog>();
		for(int i=5;i>0;i--){
			PlayResponseLog pp = new PlayResponseLog();
			pp.setKPIUTCSec("20171110141"+i);
			plays.add(pp);
		}
		Collections.sort(plays, new Comparator<PlayResponseLog>() {
            public int compare(PlayResponseLog arg0, PlayResponseLog arg1) {  
            	long kpi0 = Long.valueOf(arg0.getKPIUTCSec());  
            	long kpi1 = Long.valueOf(arg1.getKPIUTCSec());  
                if (kpi1 < kpi0) {
                    return 1;  
                } else if (kpi1 == kpi0) {  
                    return 0;  
                } else {  
                    return -1;  
                }  
            }  
        });
		
		for(int i=0;i<5;i++){
			String ss = plays.get(i).getKPIUTCSec();
			System.out.println(ss);
		}
		
		

	}

}
