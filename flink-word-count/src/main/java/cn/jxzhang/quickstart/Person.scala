package cn.jxzhang.quickstart

import java.util.Date

/**
 * Person created on 2020-09-22.
 *
 * @author zhangjiaxing
 */
class Person(name: String, age: Int, birth: java.util.Date) {

}

object Person {

  def apply(name: String, age: Int, birth: Date): Person = new Person(name, age, birth)

  def apply(name: String): Person = new Person(name, null, null)
}


