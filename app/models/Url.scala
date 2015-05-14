package models

import java.util.Date

case class Url(id: Long, url: String, shortenUrl: String, created: Date)

object Url  {
	var cache = Map[Long, Url]()
	var internalIndex = 0l

	def all(): List[Url] = cache.values.toList

	def create(url: String) {
		internalIndex = internalIndex + 1l
		var urlEntity = new Url(internalIndex, url, String.valueOf(internalIndex), new Date())
		cache += urlEntity.id -> urlEntity
	}

	def delete(id: Long) {
		cache -= id
	}

	def get(id: Long): Option[Url] = {
		cache.get(id)
	}
}