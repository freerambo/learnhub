package org.sparkexample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  11 Dec 2017 3:00:23 pm
 */
public class SparkDemo {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     11 Dec 2017 3:00:23 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	    String master = "local[*]";
	
	    /*
	     * Initialises a Spark context.
	     */
	    SparkConf conf = new SparkConf()
	        .setAppName(SparkDemo.class.getName())
	        .setMaster(master);
	    JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> distData = sc.parallelize(data);
		distData.cache();
		Integer product = distData.reduce((a,b) -> a+b);
		System.err.println(product);

//		distData.map((d) -> d * d).foreach((f) -> System.out.println(f));
		
		product = distData.map((d) -> d * d).reduce((sum, d) -> sum + d);
		
		System.err.println(product);

		
	}

}
