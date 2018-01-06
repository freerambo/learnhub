/*
 * Copyright: Energy Research Institute @ NTU
 * spark-example
 * org.sparkexample -> dsa.java
 * Created on 11 Dec 2017-5:01:30 pm
 */
package org.sparkexample;

import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class JavaWordCount {
  private static final Pattern SPACE = Pattern.compile(" ");

  public static void main(String[] args) throws Exception {


    String inputFile = "src/test/resources/wordcount.txt";

    processFile(inputFile);
   /* JavaRDD<String> lines = spark.read().textFile(inputFile).javaRDD();

    JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

    JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

    JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

    List<Tuple2<String, Integer>> output = counts.collect();
    for (Tuple2<?,?> tuple : output) {
      System.out.println(tuple._1() + ": " + tuple._2());
    }
    spark.stop();*/
  }
  
  static void processFile(String inputFile){
	  

	    SparkSession spark = SparkSession
	      .builder()
	      .master("local[*]")
	      .appName("JavaWordCount")
	      .getOrCreate();
	  
	    JavaRDD<String> lines = spark.read().textFile(inputFile).javaRDD().cache();
	    
	    List<Tuple2<String, Integer>> output = lines.flatMap(text -> Arrays.asList(SPACE.split(text)).iterator())
	    .mapToPair(word -> new Tuple2<>(word, 1))
	    .reduceByKey((a,b) -> a+b).collect();
	    output.forEach(result -> System.out.printf("Word [%s] - count [%d].\n", result._1(), result._2));
	    

  }
}