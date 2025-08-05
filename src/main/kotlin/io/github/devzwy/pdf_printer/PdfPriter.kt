package io.github.devzwy.pdf_printer

import org.apache.pdfbox.Loader
import org.apache.pdfbox.printing.PDFPrintable
import org.apache.pdfbox.printing.Scaling
import java.awt.print.PrinterJob
import java.io.File
import javax.print.PrintServiceLookup

object PdfPriter {

    fun doPrintByDefault(pdfFilePath: String, showPageBorder: Boolean = false, dpi: Float = 0F, showPrintDialog: Boolean = false) {
        val file = File(pdfFilePath)
        Loader.loadPDF(file).use { document ->
            PrinterJob.getPrinterJob().apply {
                jobName = "print pdf file ${file.name}"
                PrintServiceLookup.lookupDefaultPrintService()?.let {
                    printService = it
                }
                java.awt.print.Book().let { book ->
                    val pageable = PDFPrintable(document, Scaling.SHRINK_TO_FIT, showPageBorder, dpi)
                    book.append(pageable, defaultPage())
                    setPageable(book)

                    if (showPrintDialog) {
                        printDialog()
                    } else {
                        print()
                    }
                }
            }
        }
    }
}