package io.chrisdavenport.clippette

import cats.syntax.all._
import cats.effect._
import scala.util.Try
import scala.scalajs.js

trait ClipetteCompanionPlatform {

  private[this] def cp[F[_]: Async]: F[Clipboardy] = 
    Async[F].fromPromise(Async[F].delay(js.`import`[Clipboardy]("clipboardy")))

    // def rawPaste[F[_]: Async](s: String): F[Unit]
  private[clippette] def rawPaste[F[_]: Async](s: String): F[Unit] = {
    cp[F].flatMap(cp => Async[F].fromPromise(Async[F].delay(cp.default.write(s))))
  }
  
  // def rawCopy[F[_]: Async]: F[String] 
  private[clippette] def rawCopy[F[_]: Async]: F[String] = {
    cp[F].flatMap(cp => Async[F].fromPromise(Async[F].delay(cp.default.read())))
  }
}
// 
@js.native
private[clippette] trait Clipboardy extends js.Any {

  def default: ClipboardyDefault = js.native
}

@js.native
private[clippette] trait ClipboardyDefault extends js.Any {
  def read(): js.Promise[String] = js.native

  def write(s: String): js.Promise[Unit] = js.native
}