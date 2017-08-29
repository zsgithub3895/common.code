package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InsertIntervals {
	public static void main(String[] args) {
		List<Interval> intervals = new ArrayList<Interval>();
		intervals.add(new Interval(1,2));
		intervals.add(new Interval(3,5));
		intervals.add(new Interval(6,7));
		intervals.add(new Interval(8,10));
		intervals.add(new Interval(12,16));
		Interval newInterval = new Interval(4,9);
		List<Interval> obj = insert_two(intervals,newInterval);
		for(Interval i :obj){
			System.out.println(i.start+"|"+i.end);
		}

	}
	public static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> list = new ArrayList<Interval>();
        intervals.add(newInterval);
        if(intervals.size() == 0){
            return list;
        }
        Collections.sort(intervals, new MyComparator());
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        for(int i=1;i<intervals.size();i++){
        	Interval in = intervals.get(i);
        	if(in.start > end){
        		list.add(new Interval(start,end));
        		start = in.start;
        		end = in.end;
        	}else{
        		end = Math.max(end, in.end);
        	}
        }
        list.add(new Interval(start,end));
        return list;
    }
	
	public static List<Interval> insert_two(List<Interval> intervals, Interval newInterval) {
        List<Interval> list = new ArrayList<Interval>();
        if(intervals.size() == 0){
        	list.add(newInterval);
            return list;
        }
        int pos = 0;
        for(int i=0;i<intervals.size();i++){
        	Interval in = intervals.get(i);
        	if(in.end < newInterval.start){
        		list.add(in);
        		pos ++;
        	}else if(in.start > newInterval.end){
        		list.add(in);
        	}else{
        		newInterval.start = Math.min(in.start, newInterval.start);
        		newInterval.end = Math.max(in.end, newInterval.end);
        	}
        }
        list.add(pos,newInterval);
        return list;
    }
}

class MyComparator implements Comparator<Interval> {
	@Override
	public int compare(Interval o1, Interval o2) {
		return o1.start - o2.start;
	}
}
