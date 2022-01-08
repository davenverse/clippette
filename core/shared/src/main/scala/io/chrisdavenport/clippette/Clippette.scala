package io.chrisdavenport.clippette

import cats.effect._

trait Clippette[F[_]]{
  def copy: F[String]

  def paste(data: String): F[Unit]
}
object Clippette extends ClipetteCompanionPlatform {

  // def rawPaste[F[_]: Async](s: String): F[Unit]
  // def rawCopy[F[_]: Async]: F[String]
  
  def impl[F[_]: Async]: Clippette[F] = new Clippette[F] {
    def copy: F[String] = rawCopy[F]
    
    def paste(data: String): F[Unit] = rawPaste[F](data)
    
  }
}