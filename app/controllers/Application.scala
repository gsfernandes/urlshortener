package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models.Url

object Application extends Controller {
  
  	def index = Action {
    	Redirect(routes.Application.urls)
  	}
  
	def urls = Action { implicit request =>
		Ok(views.html.index(Url.all().map { element => new Url(element.id, element.url, "http://" + request.host + "/" + element.shortenUrl, element.created) }, urlForm))
	}
	  
	def newUrl = Action { implicit request =>
		urlForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index(Url.all(), errors)),
			url => {
				Url.create(url)
				Redirect(routes.Application.urls)
			}
		)
	}
	  
	def deleteUrl(id: Long) = Action { 
		Url.delete(id)
		Redirect(routes.Application.urls)
	}

	val urlForm = Form(
	  "url" -> nonEmptyText
	)

	def dispatch(id: Long) = Action {
		val url: Option[Url] = Url.get(id)
  		url match {
    		case Some(value) => Redirect(value.url)
    		case None => NotFound("URL not found")
  		}
	}

}