package controllers

import play.api.mvc._
import models.Sequence
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def list = Action {
    val sequences = Sequence.getAll()
    Ok(views.html.list(sequences))
  }

  def detail(id: Long) = Action {
    val seq = Sequence.get(id)
    Ok(views.html.detail(seq))
  }

  def delete(id: Long) = Action {
    val res = Sequence.delete(id)
    Redirect(routes.Application.list())
  }

  def edit(id: Long) = Action {
    val seq = Sequence.get(id)
    Ok(views.html.edit(seq))
  }

  val sequenceForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "content" -> nonEmptyText
    )((name, content) => Sequence(0, name, content))((s: Sequence) => Some(s.name, s.content))
  )

  def update(id: Long) = Action { implicit request =>
    sequenceForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index()),
      seq => {
        Sequence.update(id, seq)
        Redirect(routes.Application.detail(id))
      }
    )
  }
}