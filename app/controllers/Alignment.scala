package controllers

import play.api.mvc._
import models._
import scala.io.Source

object Alignment extends Controller {

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("dna").map { dna =>
      val baseValues = request.body.asFormUrlEncoded
      val parsedFile = AlignmentHelper.parseSeq(Source.fromFile(dna.ref.file).mkString)

      val alignable = Alignable(
        parsedFile._1,
        baseValues.apply("aVal").head.toInt,
        baseValues.apply("cVal").head.toInt,
        baseValues.apply("tVal").head.toInt,
        baseValues.apply("gVal").head.toInt,
        parsedFile._2
      )

      val sequences = Sequence.getAll()
      val evalSeq = AlignmentHelper.evaluateSeq(parsedFile._2, alignable.aVal, alignable.cVal, alignable.tVal, alignable.gVal)

      val results = sequences.map { seq =>
        val evalSeq2 = AlignmentHelper.evaluateSeq(seq.content, alignable.aVal, alignable.cVal, alignable.tVal, alignable.gVal)
        val DTWdist = AlignmentHelper.DTWDistance(evalSeq, evalSeq2)
        AlignmentResult(seq.id, seq.name, DTWdist, evalSeq2.mkString(","))
      } sortBy(_.dist) take 10

      Ok(views.html.result(alignable, results, evalSeq.mkString(",")))
    }.getOrElse {
      Redirect(routes.Application.index())
    }
  }

}
