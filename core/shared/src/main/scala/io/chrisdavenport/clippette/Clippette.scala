package io.chrisdavenport.clippette

import cats.effect._

trait Clippette[F[_]]{
  def copy: F[String]

  def paste(data: String): F[Unit]
}
object Clippette extends ClipetteCompanionPlatform {

  def apply[F[_]](implicit ev1: Clippette[F]): Clippette[F] = ev1
  // def rawPaste[F[_]: Async](s: String): F[Unit]
  // def rawCopy[F[_]: Async]: F[String]
  
  implicit def impl[F[_]: Async]: Clippette[F] = new Clippette[F] {
    def copy: F[String] = rawCopy[F]
    
    def paste(data: String): F[Unit] = rawPaste[F](data)
    
  }
}