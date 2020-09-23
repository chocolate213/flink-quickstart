package cn.jxzhang.quickstart

import java.util

import org.apache.commons.cli.{Options, PosixParser}

/**
 * Main created on 2020-09-22.
 *
 * @author zhangjiaxing
 */
object Main {

  def main(args: Array[String]): Unit = {
    val map = loadConfig(args)
    map.foreach(entry => {
      entry._2 match {
        case array: Array[AnyVal] =>
          print(entry._1)
          print(": ")
          println(array.mkString(","))
        case _ =>
          println(_:AnyVal)
      }
    })

    val person: Person = Person("zhangSan")
  }

  private def loadConfig(args: Array[String]): Map[String, AnyRef] = {
    val options = new Options()
    val optionList = args.filter(_.startsWith("--")).map(arg => arg.substring(2)).toList
    optionList.foreach(option => {
      options.addOption(null, option, true, "")
    })
    val commandLine = new PosixParser().parse(options, args)
    optionList.map(option => {
      val value = if (commandLine.hasOption(option)) {
        if ("source".equals(option) || "sink".equals(option)) {
          commandLine.getOptionValues(option)
        } else {
          commandLine.getOptionValue(option)
        }
      } else {
        null
      }
      option -> value
    }).toMap
  }
}
