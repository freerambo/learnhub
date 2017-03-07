package org.rambo.spring.boot.project.common.utils;

import java.util.List;

import com.google.common.collect.Lists;

public final class ListUtils {

	/**
	 * 
	 * @param ls
	 * @return true : 表示List为空, 反之 false
	 * @description 判断List是否为空
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 13, 2013 3:34:13 PM
	 */
	public final static <E> boolean isEmpty(List<E> ls) {
		return ls == null || ls.isEmpty();
	}

	/**
	 * 
	 * @param ls
	 * @return true : 表示List不为空, 反之 false
	 * @description 判断List是否不为空
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 13, 2013 3:35:02 PM
	 */
	public final static <E> boolean isNotEmpty(List<E> ls) {
		return !isEmpty(ls);
	}

	/**
	 * 
	 * @function: output a list
	 * @param ls
	 * @author: zhuyuanbo 21 Nov, 2014 5:53:12 pm
	 */
	public static void output(List ls) {
		if (isNotEmpty(ls))
			for (Object obj : ls) {
				if (obj instanceof Object[]) {
					Object[] objs = (Object[]) obj;
					for (Object o : objs) {
						System.out.print(o.toString() + "\t");
					}
					System.out.println();
				} else
					System.out.println(obj.toString());
			}
	}

	public static List<Integer> stringArrayToInt(String[] ls) {
		List<Integer> li = null;
		if (null != ls || ls.length != 0) {
			li = Lists.newArrayList();
			for (String obj : ls) {
				Integer i = Integer.valueOf(obj.trim());
				li.add(i);
			}
		} else {
			return null;
		}
		return li;
	}

}
