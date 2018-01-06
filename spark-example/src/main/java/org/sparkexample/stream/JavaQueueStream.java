/*
 * Copyright: Energy Research Institute @ NTU
 * spark-example
 * org.sparkexample.stream -> dsaasd.java
 * Created on 5 Jan 2018-4:36:27 pm
 */
package org.sparkexample.stream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public final class JavaQueueStream {
  private JavaQueueStream() {
  }

  public static void main(String[] args) throws Exception {

    SparkConf sparkConf = new SparkConf().setAppName("JavaQueueStream").setMaster("local[*]");

    // Create the context
    JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(1000));

    // Create the queue through which RDDs can be pushed to
    // a QueueInputDStream

    // Create and push some RDDs into the queue
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      list.add(i);
    }

    Queue<JavaRDD<Integer>> rddQueue = new LinkedList<>();
    for (int i = 0; i < 30; i++) {
      rddQueue.add(ssc.sparkContext().parallelize(list));
    }

    // Create the QueueInputDStream and use it do some processing
    JavaDStream<Integer> inputStream = ssc.queueStream(rddQueue);
    JavaPairDStream<Integer, Integer> mappedStream = inputStream.mapToPair(
        i -> new Tuple2<>(i % 10, 1));
    JavaPairDStream<Integer, Integer> reducedStream = mappedStream.reduceByKey(
        (i1, i2) -> i1 + i2);
    reducedStream.foreachRDD((r) -> System.out.println(r));
    

    reducedStream.print();
    ssc.start();
    ssc.awaitTermination();
  }
}