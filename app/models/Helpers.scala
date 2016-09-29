package models

import scala.collection.mutable.ArrayBuffer
import Array._

case class Alignable (name: String, aVal: Int, cVal: Int, tVal: Int, gVal: Int,  content: String)
case class AlignmentResult (id: Long, name: String, dist: Int, timeSeries: String)

object AlignmentHelper {

  def parseSeq(fileContents: String): Tuple2[String, String] = {
    val name = fileContents.split('\n')(0)
    var seq = ""
    fileContents.split('\n').tail.foreach { line =>
      seq += line
    }
    (name, seq)
  }

  def evaluateSeq(seq: String, aVal: Int, cVal: Int, tVal: Int, gVal: Int): Array[Int] = {
    var buffer = ArrayBuffer[Int]()
    val seqSplit = seq.split("").drop(1)
    seqSplit.zipWithIndex.foreach { case(char, index) =>
      char match {
        case "A" => if (index == 0) buffer += aVal else buffer += (buffer(index - 1) + aVal)
        case "C" => if (index == 0) buffer += cVal else buffer += (buffer(index - 1) + cVal)
        case "T" => if (index == 0) buffer += tVal else buffer += (buffer(index - 1) + tVal)
        case "G" => if (index == 0) buffer += gVal else buffer += (buffer(index - 1) + gVal)
      }
    }
    buffer.toArray
  }

  def DTWDistance(seq1: Array[Int], seq2: Array[Int]): Int = {
    var DTWmatrix = Array.fill[Int](seq1.length, seq2.length){Int.MaxValue}
    DTWmatrix(0)(0) = 0
    DTWmatrix.zipWithIndex.foreach { case(rowVal, rowIdx) =>
      rowVal.zipWithIndex.foreach { case(colVal, colIdx) =>
        if (rowIdx != 0 && colIdx != 0) {
          val cost = Math.abs(seq1(rowIdx) - seq2(colIdx))
          var min = Math.min(DTWmatrix(rowIdx - 1)(colIdx), DTWmatrix(rowIdx)(colIdx - 1))
          min = Math.min(min, DTWmatrix(rowIdx - 1)(colIdx - 1))
          DTWmatrix(rowIdx)(colIdx) = cost + min
        }
      }
    }
    DTWmatrix.last.last
  }
}

