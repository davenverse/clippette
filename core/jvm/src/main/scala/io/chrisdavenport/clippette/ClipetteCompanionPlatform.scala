package io.chrisdavenport.clippette

import cats.effect._
import java.awt.datatransfer.StringSelection
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor


trait ClipetteCompanionPlatform {

  // def pasteRaw[F[_]: Async](s: String): F[Unit]
  private[clippette] def rawPaste[F[_]: Async](s: String): F[Unit] = Sync[F].delay{
    val selection = new StringSelection(s)
    val clipboard: Clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    clipboard.setContents(selection, selection)
  }
  // def copyRaw[F[_]: Async]: F[String]
  private[clippette] def rawCopy[F[_]: Async]: F[String] = Sync[F].delay{
    val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    val d = clipboard.getData(DataFlavor.stringFlavor)
    d.asInstanceOf[String]
  }
}