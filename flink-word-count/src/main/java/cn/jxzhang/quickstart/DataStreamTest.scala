package cn.jxzhang.quickstart

import org.apache.flink.streaming.api.functions.{KeyedProcessFunction, ProcessFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * DataStreamTest created on 2020-09-22.
 *
 * @author zhangjiaxing
 */
object DataStreamTest {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9999)

    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty }}
      .process(new CustomProcessFunction)
      .map {(_, 1)}
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print()

    env.execute("Window Stream WordCount")
  }

  case class CustomProcessFunction() extends ProcessFunction[String, String] {
    override def processElement(value: String, ctx: ProcessFunction[String, String]#Context, out: Collector[String]): Unit = {
      val upper = value.toUpperCase
      out.collect(upper)
    }
  }
}
