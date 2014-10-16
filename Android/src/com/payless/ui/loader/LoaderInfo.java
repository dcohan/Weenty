package com.payless.ui.loader;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
public class LoaderInfo {

	private static Map<Integer, Integer> mLoadersMap = new HashMap<Integer, Integer>();

	public static void add(int id) {

		if (mLoadersMap.containsKey(id)) {
			mLoadersMap.put(id, mLoadersMap.get(id) + 1);
		} else {
			mLoadersMap.put(id, 1);
		}

	}

	public static void remove(int id) {
		if (mLoadersMap.containsKey(id)) {
			mLoadersMap.put(id, mLoadersMap.get(id) - 1);
		}
	}

	public static int getCount(int id) {
		Integer times = mLoadersMap.get(id);
		if (times == null)
			times = 0;
		return times;
	}

	public static void resetMap() {
		mLoadersMap = new HashMap<Integer, Integer>();
	}
}
