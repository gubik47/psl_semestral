package models

import play.api.db._
import play.api.Play.current
import anorm._

case class Sequence(val id: Long, var name: String, var content: String)

object Sequence {
  def getAll(): List[Sequence] = DB.withConnection { implicit c =>
    val query = SQL("SELECT * FROM dna")

    query().map(row =>
      Sequence(
        row[Long]("id"),
        row[String]("name"),
        row[String]("content")
      )
    ).toList
  }

  def get(id: Long): Sequence = DB.withConnection { implicit c =>
    val query = SQL("SELECT * FROM dna WHERE id = {id}")
      .on("id" -> id)

    query().map(row =>
      Sequence(
        row[Long]("id"),
        row[String]("name"),
        row[String]("content")
      )
    ).head
  }

  def delete(id: Long) : Int = DB.withConnection { implicit c =>
    val numRowsDeleted = SQL("DELETE FROM dna WHERE id = {id}")
      .on("id" -> id)
      .executeUpdate()
    numRowsDeleted
  }

  def update(id: Long, seq: Sequence) = DB.withConnection { implicit c =>
    val query = SQL("UPDATE dna SET name={name}, content={content} WHERE id={id}")
      .on("id" -> id,
          "name" -> seq.name,
          "content" -> seq.content
      ).executeUpdate()
  }
}
