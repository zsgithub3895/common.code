package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {

	public static void main(String[] args) {

	}

	public List<Interval> merge(List<Interval> intervals) {
		List<Interval> list = new ArrayList<Interval>();
		if (intervals.size() == 0) {
			return list;
		}
		Collections.sort(intervals, new MyComparator());
		int start = intervals.get(0).start;
		int end = intervals.get(0).end;
		for (int i = 1; i < intervals.size(); i++) {
			Interval inter = intervals.get(i);
			if (inter.start > end) {
				list.add(new Interval(start, end));
				start = inter.start;
				end = inter.end;
			}else{
				end = Math.max(end, inter.end);
			}
		}
		list.add(new Interval(start, end));
		return list;
	}

	class MyComparator implements Comparator<Interval> {
		@Override
		public int compare(Interval o1, Interval o2) {
			return o1.start - o2.start;
		}
	}
}
